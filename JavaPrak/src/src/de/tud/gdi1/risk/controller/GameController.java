package src.de.tud.gdi1.risk.controller;
import java.io.IOException;
import java.util.Arrays;

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
	private GameplayState view;
	private Options options;
	private ErrorPrinter printer;
	
	private static final int REINFORCEMENT_PHASE = 0;
	private static final int ATTACKING_PHASE = 1;
	private static final int FORTIFYING_PHASE = 2;
	private static final int STARTING_PHASE = 3;
	
	private boolean forcesAdded = false;
	private boolean countryConquered = false;
	private int[] attackDices, defenseDices;
	private Country[] countries;	
	
	public GameController(GameplayState view) throws IOException{
		this.view = view;
		options = Options.getInstance();
		Player[] players = new Player[options.getPlayerCount()];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(options.getColor(i), "Player " + i);
		this.currentPlayer = 0;
		this.state = 3;
		map = new GameMap(players);
		this.printer = new ErrorPrinter();
	}
	
	public void init()
	{
		view.updateUserInterface();		
	}
	
	public void nextPhase()
	{
		switch(state)
		{
		case REINFORCEMENT_PHASE:
			printer.printError(printer.PHASEERROR);
			break;
		case ATTACKING_PHASE:
			state = 2;
			break;
		case FORTIFYING_PHASE:
			printer.printError(printer.PHASEERROR);
			break;
		case STARTING_PHASE: 
			printer.printError(printer.PHASEERROR);
			break;
		default:
			break;
		}
	}


	public void update(){
		
		switch(state)
		{
		case REINFORCEMENT_PHASE:
			if(!forcesAdded)
				addForces();
		}
		view.updateUserInterface();
	}
	
	
	/**
	 * Calculates the Result of an Attack
	 * Uses Attackdices and Defensedices Arrays to do this
	 * Sorts AttackDices and DefenseDices Arrays
	 * Moves Troops depending on the results saved in AttackDices and DefenseDices
	 * @return true if attack conquered a Country and the Defender has no Troops left, false if not
	 */
	private boolean attack() {

		Arrays.sort(attackDices);
		Arrays.sort(defenseDices);
		reverse(attackDices);
		reverse(defenseDices);
		
		for(int i = 0; i < attackDices.length && i < defenseDices.length; i++)
		{
			if(attackDices[i] > defenseDices[i])
			{
				countries[1].moveTroops(1);
			}
			else
			{
				countries[0].moveTroops(1);
			}
		}
		
		//If Defender has no Troops left the country is successfully conquered and the attacker may move troops
		if(countries[1].getTroops() == 0)
		{
			return true;
		}
		return false;
	}

	private void reverse(int[] arr) {
		for(int i = 0; i < arr.length / 2; i++)
		{
		    int temp = arr[i];
		    arr[i] = arr[arr.length - i - 1];
		    arr[arr.length - i - 1] = temp;
		}
	}

	/**
	 * Adds Reinforcement to a Player a the Start of the Reinforcement Phase that he can use on his countries
	 * Normal Reinforcements are calculated with ownedCountries/3 but minimum of 3
	 * Additional Reinforcements may come from owning an entire continent
	 */
	private void addForces() {
		this.forcesAdded = true;
		map.getPlayer(currentPlayer).addReinforcement(map.getOwnedCountriesForPlayer(currentPlayer) > 11 ? map.getOwnedCountriesForPlayer(currentPlayer)/3 : 3);

	
		for(Continent x : map.getContinents())
		{
			if(x.isOwned(map.getPlayer(currentPlayer), map.getCountries()))
				map.getPlayer(currentPlayer).addReinforcement(x.getBonusTroops());
		}
		
	}

	
	
	/**
	 * Ends the Turn and chooses the next Player
	 * Changes the currentPlayer to the next Player
	 * Depending on the state a Reset is called or not (Not in StartingPhase to prevent going into not wanted states)
	 * In Normal Game State this method assigns Cards to the player, if he conquered a Country in their turn
	 * Checks for Win Condition
	 * Does nothing when in REINFORCEMENT_PHASE because you should not be able to end your turn 
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
			if(map.getPlayer(currentPlayer).checkMissionForWin(map))
			{
				//TODO: WIN
				System.out.println("WIN");
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
		attackDices = null;
		defenseDices = null;
		view.reset();
	}

	/**
	 * rolls diceCount dice and returns the result in an Array
	 * @param diceCount amount of dice to be rolled
	 * @return Array of size diceCount with results of the dicerolls (1-6) saved  
	 */
	private int[] rollDice(int diceCount)
	{
		int[] dices = new int[diceCount];
		for(int i = 0; i < dices.length; i++)
		{
			dices[i] = (int) (Math.random() * 6) + 1;
		}
		return dices;	
	}

	/**
	 * Used in the starting Phase and Reinforcement phase of Risk to place troops on a country 
	 * Automatically ends the turn for a Player if the game is in the starting phase to make an alternated placing of units possible
	 * Called from Gameplaystate whenever a unit is placed
	 * @param ownerEntity Country in which the Troop is placed
	 */
	public void setReinforceCountry(Country ownerEntity) {
		if(map.getPlayer(currentPlayer).getReinforcement() > 0 && this.getState() == STARTING_PHASE){
			ownerEntity.addTroops(1);
			map.getPlayer(currentPlayer).substractReinforcement(1);
			this.endTurn();
		}else if(map.getPlayer(currentPlayer).getReinforcement() > 0 && this.getState() == REINFORCEMENT_PHASE)
		{
			ownerEntity.addTroops(1);
			map.getPlayer(currentPlayer).substractReinforcement(1);
			if(map.getPlayer(currentPlayer).getReinforcement() == 0)
				this.state = 1;
		}
	}

	/**
	 * Called in the Attackphase whenever the rolldice Button is pressed
	 * Calculates the Attack on a Country and changes troops accordingly
	 * Passes the results to the View to display them
	 * If a Country is conquered with this troopMovements are requested (if the country has more than 2 units left, if this is the case 1 unit is moved automatically)
	 * @param diceCount amount of dice that the attacker wants to use (between 1 and 3)
	 * @param countries attacking(index 0)  and defending country(index 1) in an attack 
	 */
	public void rollDiceEvent(int diceCount, Country[] countries) {
		
		if(this.state != ATTACKING_PHASE)
		{
			printer.printError(printer.PHASEERROR);
			return;
		}
			
		if(countries[0].getOwner() == countries[1].getOwner())
		{
			printer.printError(printer.OWNERATTACKERROR);
			return;
		}
		if(!countries[0].isNeighbor(countries[1]))
		{
			printer.printError(printer.NOTNEIGHBORERROR);
			return;
		}
		if(diceCount > countries[0].getTroops()-1)
		{
			printer.printError(printer.NOTENOUGHTROOPSATTACKERROR);
			return;
		}
		if(countries[1].getTroops() <= 0)
		{
			printer.printError(printer.NOTENOUGHTROOPSDEFENDERROR);
			return;
		}
		if(countries[1].getTroops() == 0)
		{
			//TODO: Show Error!
			System.out.println("Länder ohne Truppen können nicht angegriffen werden");
			return;
		}
		this.countries = countries;
		attackDices = this.rollDice(diceCount);
		defenseDices = this.rollDice(countries[1].getTroops() > 1 ? 2 : 1);
		countryConquered = this.attack();
		
		System.out.println("ROLL THE DICE");
		System.out.println(diceToString());
	
		view.showDiceResult(attackDices, defenseDices);
		if(countryConquered)
		{
			countries[1].setOwner(map.getPlayer(currentPlayer));
			if(countries[0].getTroops() == diceCount+1)
			{
				troopsMovedEvent(diceCount, countries);
				view.requestTroopMovement(0, 0);
			}
				else
			{
				troopsMovedEvent(diceCount, countries);
				view.requestTroopMovement(0, this.countries[0].getTroops()-1);	
			}
		}
	}
	
	private String diceToString() {
		String s = "AttackDices: ";
		for(int i : attackDices)
		{
			s = s + i + "|";
		}
		s = s + "\n" + "DefenseDice: ";
		for(int i : defenseDices)
			s = s + i + "|";
		return s;
	}

	/**
	 * Called in the Attackphase after a Country got conquered and Troops are moved into the conquered Country
	 * Or in the Fortifying Phase when moving troops
	 * Moves the troops from Countries[0] to countries[1]
	 * @param amount of troops moved from Countries[0] to Countries[1] that got declared in rollDiceEvent
	 */
	public void troopsMovedEvent(int amount, Country[] countries )
	{	
		if(this.state != ATTACKING_PHASE && this.state != FORTIFYING_PHASE)
		{
			printer.printError(printer.PHASEERROR);
			return;
		}
			
		if(countries[0].getOwner() != countries[1].getOwner())
		{
			printer.printError(printer.OWNERMOVEERROR);
			return;
		}
			
		if(!countries[0].isNeighbor(countries[1]))
		{
			printer.printError(printer.NOTNEIGHBORERROR);
			return;
		}
			
		
		if(countries[0].moveTroops(amount))
		{
			countries[1].addTroops(amount);
		}
		else
		{
			printer.printError(printer.NOTENOUGHTROOPSMOVEERROR);
		}
		
	}
	
	public int getCurrentPlayerIndex() {
		return currentPlayer;
	}
	
	public Player getTurnPlayer(){
		return map.getPlayer(currentPlayer);
	}
	
	public int getState() {
		return state;
	}
	
	public GameMap getMap(){
		return map;
	}
	


}
