package src.de.tud.gdi1.risk.controller;
import java.util.ArrayList;
import java.util.Arrays;


import src.de.tud.gdi1.risk.model.Card;
import src.de.tud.gdi1.risk.model.Continent;
import src.de.tud.gdi1.risk.model.GameMap;
import src.de.tud.gdi1.risk.model.Options;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.GameplayState;


public class GameController {
	
	private GameMap map;
	private int state = 0;
	private int currentPlayer;
	private int startTroops;
	private GameplayState view;
	private Options options;
	private Country[] countries;	
	
	private static final int REINFORCEMENT_PHASE = 0;
	private static final int ATTACKING_PHASE = 1;
	private static final int FORTIFYING_PHASE = 2;
	private static final int STARTING_PHASE = 3;
	
	private boolean forcesAdded = false;
	private boolean countryConquered = false;
	private int[] attackDices, defenseDices;
	
	public GameController(GameplayState view){
		this.view = view;
		options = Options.getInstance();
		Player[] players = new Player[options.getPlayerCount()];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(options.getColor(i), "Player " + i);
		this.currentPlayer = 0;
		this.state = 3;
		map = new GameMap(players);
		startTroops = (2 * map.getCountries().size())/players.length;	
	}
	
	public void init()
	{
		view.updateUserInterface(state, map, currentPlayer);		
	}

	public GameMap getMap(){
		return map;
	}
	
	public void update(){
		
		switch(state)
		{
		case REINFORCEMENT_PHASE:
			if(!forcesAdded)
				addForces(currentPlayer);
			/*
			if(view.isReinforceButtonPushed())
			{
				view.getSelectedCountry().addForce(view.getReinforcement());
				map.getPlayer(currentPlayer).substractReinforcement(view.getReinforcement());
				if(map.getPlayer(currentPlayer).getReinforcement() == 0)
				{
					view.disableReinforcement();
					view.enableNextPhase();
				}else 
				{
					view.setReinforce(false);
					view.resetUI();
				}
			}
			else if(view.nextPhaseButtonPressed())
			{
				state = ATTACKING_PHASE;
				view.resetUI();
				view.setNextPhaseButton(false);
			}
			*/
			
			break;
		case ATTACKING_PHASE: 
			if(view.attackButtonPressed())
			{
				//countries[0] = defender
				//countries[1] = attacker
				countries = view.getSelectedCountries();
				attackDices = this.rollDice(view.getAttackDiceCount());
				defenseDices = this.rollDice(countries[0].getTroops() > 1 ? 2 : 1);
				view.setAttackDices(attackDices);
				view.setDefenseDices(defenseDices);
				
				//Request number of troops to move into conquered area
				if(this.attack())
				{
					countryConquered = true;
					if(countries[1].getTroops() < view.getAttackDiceCount())
						view.requestTroopMovement(countries[1].getTroops()-1, countries[1].getTroops()-1);
					else
						view.requestTroopMovement(view.getAttackDiceCount(), countries[1].getTroops()-1);
				}
				view.resetUI();
			}
			else if(view.troopMovementSelected())
			{
				int troopsMoved = view.getTroopMovement();
				countries[1].moveTroops(troopsMoved);
				countries[0].addTroops(troopsMoved);
			}
			else if(view.nextPhaseButtonPressed())
			{
				state = FORTIFYING_PHASE;
				view.setNextPhaseButton(false);
				view.resetUI();
			}
			break;
		case FORTIFYING_PHASE:
			if(view.fortifyButtonPressed())
			{
				countries = view.getSelectedCountries();
				int force = view.getFortifyUnits();
				if(countries[0].getTroops() > force)
				{
					if(countries[0].isNeighbor(countries[1]))
					{
						countries[0].moveTroops(force);
						countries[1].addTroops(force);
					}
				}
				view.resetUI();
			}
			/*
			else if(view.endTurnButtonPressed())
			{
				this.endTurn();
			}
			*/
			break;
		case STARTING_PHASE:
			if(view.startTroopPlaced())
			{
				Country country = view.getStartTroopCountry();
				country.addTroops(1);
				if(currentPlayer == map.getPlayers().length-1)
				{
					currentPlayer = 0;
					startTroops--;
					if(startTroops == 0)
					{
						state = REINFORCEMENT_PHASE;
						view.resetUI();
					}
				}
				else
					currentPlayer++;
				
				
			}
			break;
		}
		view.updateUserInterface(state, map, currentPlayer);
	}
	
	/* Calculates the attack 
	 * returns true if Country is conquered, returns false if country is not conquered
	 * 
	 */
	
	private boolean attack() {

		Arrays.sort(attackDices);
		Arrays.sort(defenseDices);
		
		for(int i = 0; i < attackDices.length && i < defenseDices.length; i++)
		{
			if(attackDices[i] > defenseDices[i])
			{
				countries[0].moveTroops(1);
			}
			else
			{
				countries[1].moveTroops(1);
			}
		}
		
		//If Defender has no Troops left the country is successfully conquered and the attacker may move troops
		if(countries[0].getTroops() == 0)
		{
			return true;
		}
		return false;
	}

	private void addForces(int currentPlayer) {
		this.forcesAdded = true;
		map.getPlayer(currentPlayer).addReinforcement(map.getOwnedCountriesForPlayer(currentPlayer) > 11 ? map.getOwnedCountriesForPlayer(currentPlayer)/3 : 3);

		//map.getOwnedCountriesForPlayer(currentPlayer) > 11 ? map.getOwnedCountriesForPlayer(currentPlayer)/3 : 3
		
		for(Continent x : map.getContinents())
		{
			if(x.isOwned(map.getPlayer(currentPlayer), map.getCountries()))
				map.getPlayer(currentPlayer).addReinforcement(x.getBonusTroops());
		}
		
	}

	public Player getTurnPlayer(){
		return map.getPlayer(currentPlayer);
	}
	
	/* Ends the turn and resets state and chooses next player
	 * Players that conquered a country in their turn will also get a random Card
	 * 
	 */
	public void endTurn()
	{
		if(state == STARTING_PHASE)
		{
			if(currentPlayer == map.getPlayers().length-1){
				if(map.getPlayer(currentPlayer).getReinforcement() == 0)
					reset();
				currentPlayer = 0;
			}
			else
				currentPlayer++;
			return;
		}
		if(state != REINFORCEMENT_PHASE)
		{
			if(map.getPlayer(currentPlayer).checkMissionForWin())
			{
				//TODO: WIN
				
			}
			
			if(countryConquered) 
			{
				countryConquered = false;
				map.getPlayer(currentPlayer).addCard(map.getRandomCard());
			}
			if(currentPlayer == map.getPlayers().length-1)
				currentPlayer = 0;
			else
				currentPlayer++;
			reset();
		}
	}
	
	private void reset() {
		state = 0;
		forcesAdded = false;
		countryConquered = false;
		view.setReinforce(false);
		attackDices = null;
		defenseDices = null;
		view.disableNextPhase();
		view.resetUI();
	}

	public int[] rollDice(int diceCount)
	{
		int[] dices = new int[diceCount];
		for(int i = 0; i < dices.length; i++)
		{
			dices[i] = (int) (Math.random() * 6);
		}
		return dices;	
	}

	public int getState() {
		return state;
	}

	public void setReinforceCountry(Country ownerEntity) {
		if(map.getPlayer(currentPlayer).getReinforcement() > 0 && this.getState() == 3){
			ownerEntity.addTroops(1);
			map.getPlayer(currentPlayer).substractReinforcement(1);
			this.endTurn();
		}else if(map.getPlayer(currentPlayer).getReinforcement() > 0 && this.getState() == 0)
		{
			ownerEntity.addTroops(1);
			map.getPlayer(currentPlayer).substractReinforcement(1);
			if(map.getPlayer(currentPlayer).getReinforcement() == 0)
				this.state = 1;
		}
	}

	public int getCurrentPlayerIndex() {
		return currentPlayer;
	}
}
