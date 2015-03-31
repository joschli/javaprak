package src.de.tud.gdi1.risk.controller;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import src.de.tud.gdi1.risk.model.Card;
import src.de.tud.gdi1.risk.model.GameMap;
import src.de.tud.gdi1.risk.model.Options;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.GameplayState;


public class GameController {
	private GameMap map;
	private Player[] players;
	private int state = 0;
	private int currentPlayer;
	private GameplayState view;
	private Options options;
	private ArrayList<Card> cards;
	private Country[] countries;	
	private static final int REINFORCEMENT_PHASE = 0;
	private static final int ATTACKING_PHASE = 1;
	private static final int FORTIFYING_PHASE = 2;
	private boolean forcesAdded = false;
	private boolean dicesRolled = false;
	private int[] attackDices, defenseDices;
	
	public GameController(GameplayState view){
		options = Options.getInstance();
		players = new Player[options.getPlayerCount()];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(options.getColor(i), "Player " + i);
		this.view = view;
		this.cards = new ArrayList<Card>();
		this.currentPlayer = 0;
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
			if(view.isReinforceButtonPushed())
			{
				view.getSelectedCountry().addForce(view.getReinforcement());
				players[currentPlayer].substractReinforcement(view.getReinforcement());
				if(players[currentPlayer].getReinforcement() == 0)
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
			
			break;
		case ATTACKING_PHASE: 
			if(view.attackButtonPressed() && !dicesRolled)
			{
				countries = view.getSelectedCountries();
				attackDices = this.rollDice(view.getAttackDiceCount());
				defenseDices = this.rollDice(view.getDefenseDiceCount());
				view.setAttackDices(attackDices);
				view.setDefenseDices(defenseDices);
				dicesRolled = true;
			}
			else if(dicesRolled && view.attackMoveSelected())
			{
				Vector2f[] attack = view.getAttackMoves();
				this.attack(attack);
				dicesRolled = false;
				view.resetUI();
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
			else if(view.endTurnButtonPressed())
			{
				this.endTurn();
			}
			break;
		}
		view.updateUserInterface();
	}
	
	private void attack(Vector2f[] attack) {
		for(Vector2f a : attack)
		{
			if(defenseDices[(int) a.x] >= attackDices[(int) a.y])
			{
				countries[1].moveTroops(defenseDices[(int) a.x]);
			}
			else
			{
				countries[0].moveTroops(attackDices[(int) a.y]);
			}
		}
	}

	private void addForces(int currentPlayer) {
		players[currentPlayer].addReinforcement(5);
	}

	public Player getTurnPlayer(){
		return players[currentPlayer];
	}
	
	public void endTurn()
	{
		if(state != REINFORCEMENT_PHASE)
		{
			if(currentPlayer == players.length-1)
				currentPlayer = 0;
			else
				currentPlayer++;
			reset();
		}
	}
	
	private void reset() {
		state = 0;
		forcesAdded = false;
		view.setReinforce(false);
		attackDices = null;
		defenseDices = null;
		dicesRolled = false;
		view.disableNextPhase();
		view.resetUI();
	}

	public void init() {
		map = new GameMap();
		createCards(map.getCountries());
		givePlayerCards();
		view.updateUserInterface();
	}

	private void createCards(ArrayList<Country> countries) {
		for(Country c : countries)
		{
			cards.add(new Card(c, c.getCardValue()));
		}
	}

	private void givePlayerCards() {
		int index = 0;
		while(!cards.isEmpty()){
			int random = (int) (Math.random() * cards.size());
			players[index].addCard(cards.remove(random));
			index = index == 0 ? 1 : 0;
		}
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
}
