package src.de.tud.gdi.risk.tests.adapter;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;
import eea.engine.test.TestAppGameContainer;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.Continent;
import src.de.tud.gdi1.risk.model.Options;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.GameplayState;
import src.de.tud.gdi1.risk.ui.Launch;

public class RiskTestAdapterMinimal {

	GameController risk;
	GameplayState view;
	public static final int REINFORCEMENT_PHASE = 0;
	public static final int ATTACKING_PHASE = 1;
	public static final int FORTIFYING_PHASE = 2;
	
	
	public RiskTestAdapterMinimal() {
		super();
		risk = null;
	}
	
	public void initializeGame() throws IOException {
		view = new TestGameplayState(0);
		risk = new GameController(view);
		//skip starting phase that is not part of the minimal stage
		for(Player p : risk.getMap().getPlayers())
		{
			p.substractReinforcement(p.getReinforcement());
			risk.endTurn();
		}
		risk.update();
			
	}
	
	public void stopGame() {
	
	}
	
	/**
	 * 
	 * @return the number of Countries in the momentary Game of Risk
	 */

	public int getNumberOfCountries() {
		return risk.getMap().getCountries().size();
	}

	/**
	 * Continent Names should be unique!
	 * @return all Names from Continents
	 */
	public ArrayList<String> getContinentNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(Continent c : risk.getMap().getContinents())
			names.add(c.getName());
		return names;
	}

	/**
	 * Returns all Continents for all Countries 
	 * So return is the length of getNumberOfCountries
	 * @return Names of Continents for every Country in same sequence as saved
	 */
	public String[] getContinentNamesForCountries() {
		String[] names = new String[risk.getMap().getCountries().size()];
		int i = 0;
			
		for(Country c: risk.getMap().getCountries())
		{
			int index = risk.getMap().getCountries().indexOf(c);
			for(Continent co : risk.getMap().getContinents())
			{	
				if(co.getCountries().contains(index))
				{
					names[i] = co.getName();
					i++;
					break;
				}
			}
			
		}
		return names;
	}

	/**
	 * Example: If Player 0 and Player 1 are the first and second Player 
	 * this method would return them in this sequence: Player 0, Player 1
	 * So the first Player in the return is automatically the start Player
	 * @return the name of all players in sequence of the appearance in the games 
	 */
	public ArrayList<String> getPlayerNames() {
		ArrayList<String> names = new ArrayList<String>();
		for(Player p : risk.getMap().getPlayers())
			names.add(p.getName());
		return names;
		
	}

	/**
	 * 
	 * @returns the name of all Countries
	 */
	public ArrayList<String> getCountryNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		for(Country c: risk.getMap().getCountries())
			names.add(c.getName());
		return names;
	}

	/**
	 * 
	 * @returns the number of Players
	 */
	public int getPlayerCount() {
		return risk.getMap().getPlayers().length;
	}

	/**
	 * Returns the Owner of an Country
	 * @param c the Country which we want to have the Owner for
	 * @return the Owner or rather the name of the owner
	 */
	public String getOwnerNameForCountry(String c) {
	
		return risk.getMap().getCountry(c).getOwner().getName();
		
	}

	/**
	 * 
	 * @param c 
	 * @returns the number of Troops in a Country c
	 */
	public int getTroopsForCountry(String c) {
		return risk.getMap().getCountry(c).getTroops();
	}

	/**
	 * Indices of the playres should go from 0 to playerCount-1 
	 * @returns the index of the current Player
	 */
	public int getCurrentPlayerNumber() {
		return risk.getCurrentPlayerIndex();
	}

	/**
	 * For Minimal the 3 States should be 0 = Reinforcement, 1 = Attack, 2 = Fortify
	 * This method should only return one of those  
	 * @returns the state the game is in
	 */
	public int getPhase() {
		return risk.getState();
	}

	/**
	 * 
	 * @param player or rather the index of the Player as described above
	 * @returns the Reinforcements a Player has in a Reinforcement-Phase
	 */
	public int getReinforcementsForPlayer(int player) {
		return risk.getMap().getPlayer(player).getReinforcement();
	}

	/**
	 * Changes the Phase to the next Phase
	 * This should trigger some kind of Changing of States
	 * The 3 Main States that should be switched between are described at getPhase() above
	 */
	public void nextPhase() {
		risk.nextPhase();
		risk.update();
	}

	/**
	 * randomly sets the Reinforcement of the current Player in a Country he owns
	 * this is used to get into attack state, since reinforcement phase should not be skippable
	 */
	public void setAllReinforcements() {
		int r = risk.getMap().getPlayer(risk.getCurrentPlayerIndex()).getReinforcement();
		for(int i=0; i<r ; i++)
		{
			risk.setReinforceCountry(risk.getMap().getCountryListForPlayer(risk.getMap().getPlayer(risk.getCurrentPlayerIndex())).get(0));
		}

	}

	/**
	 * This should end a Turn 
	 */
	public void endTurn() {
		risk.endTurn();
		risk.update();
	}

	/**
	 * Gets a Name of a Country that is owned by a Player
	 * @param currentPlayerNumber the index of the Player 
	 * @returns Name of a Country
	 */
	public String getCountryNameOwnedByPlayer(int currentPlayerNumber) {
		return risk.getMap().getCountryListForPlayer(risk.getMap().getPlayer(currentPlayerNumber)).get(0).getName();
	}

	/**
	 * Callls the Function to reinforce a Country 
	 * @param country The Country to be reinforced
	 * @param i the number of units to reinforce with
	 */
	public void reinforceCountry(String country, int i) {
		for(int j = 0; j < i; j++)
			risk.setReinforceCountry(risk.getMap().getCountry(country));
	}

	/**
	 * Sets the Troops of a Country for Test Purposes
	 * The Troops of the Country should be exactly i after calling this method
	 * @param country name of the Country
	 * @param i number of Troops
	 */
	public void setTroopsForCountry(String country, int i) {
		Country c =risk.getMap().getCountry(country);
		if( i > c.getTroops())
			c.addTroops(i-c.getTroops());
		if( i == c.getTroops())
			return;
		if( i < c.getTroops())
			c.moveTroops(c.getTroops()-i);
	}

	/**
	 * Gets a Country that is a Neighbor to the Country and 
	 * that is not owned by the player
	 * @param country 
	 * @param player the index of the player
	 * @return A Name of a Country 
	 */
	public String getNeighborCountryNotOwnedByPlayer(String country,
			int player) {
		Country c = risk.getMap().getCountry(country);
		for(Country c2 : risk.getMap().getCountries())
		{
			if(c2.isNeighbor(c) && c2.getOwner() != c.getOwner())
				return c2.getName();
		}
		return null;
	}

	/**
	 * Returns a Country owned by a player
	 * which is at a Border, which means that there is atleast 
	 * one neighbor that is owned by an Enemy Player
	 * @param currentPlayerNumber
	 * @return
	 */
	public String getBorderCountryOwnedByPlayer(int currentPlayerNumber) {
		for(Country c: risk.getMap().getCountries())
		{	
			if(c.getOwner() != risk.getMap().getPlayer(currentPlayerNumber))
			{
				continue;
			}
			for(Country c2 : risk.getMap().getCountries())
			{
				if(c2.isNeighbor(c) && c2.getOwner() != c.getOwner())
					return c.getName();
			}
		}
		return null;
	}

	/**
	 * calls an Attack
	 * This should give the two Countries to the game and make an Attack 
	 * @param attCountry Attacking Country
	 * @param defCountry Defending country
	 * @param i Number of Attackdice
	 * @return the number of Units the Attacker Lost in the Exchange
	 */
	public int attack(String attCountry, String defCountry, int i) {
		Country c1 = risk.getMap().getCountry(attCountry);
		Country c2 = risk.getMap().getCountry(defCountry);
		Country[] c = {c1,c2};
		risk.rollDiceEvent(i,c );
		int[] attD = risk.getAttackDices();
		int[] defD = risk.getDefenseDices();
		int ret = 0;
		if(attD != null && defD != null)
		{
			for(int j = 0; j < attD.length && j < defD.length; j++)
			{
				if(attD[j] > defD[j])
				{
					
				}
				else
				{
					ret++;
				}
			}
		}
		
		return ret;
	}

	/**
	 * Returns two Countries that are Neighboored 
	 * One Country has to be owned by Player 1
	 * The other Country has to be owned by Player2
	 * @param player1 index of one player
	 * @param player2 index of the other player
	 * @return an Array of the two Names of the countries
	 */
	public String[] getNeighboredCountries(int player1,
			int player2) {
		String[] ret = new String[2];
		for(Country c: risk.getMap().getCountries())
		{	
			if(c.getOwner() != risk.getMap().getPlayer(player1))
			{
				continue;
			}
			for(Country c2 : risk.getMap().getCountries())
			{
				if(c2.isNeighbor(c) && c2.getOwner() == risk.getMap().getPlayer(player1))
				{
					ret[0] = c.getName();
					ret[1] = c2.getName();
				}
			}
		}
		return ret;
	}
	
	/**
	 * Returns the owner index of a country
	 * @param country 
	 * @return the index of the player that owns country
	 */
	public int getOwnerOfCountry(String country) {
		for(int i = 0; i < risk.getMap().getPlayers().length; i++)
		{
			if(risk.getMap().getPlayer(i) == risk.getMap().getCountry(country).getOwner())
			{
				return i;
			}
				
		}
			
			return -1; 
	}

	/**
	 * calls a move Troops action that moves troops from c1 to c2
	 * @param c1 the first country that troops are moved out of
	 * @param c2 the second country that troops are moved into
	 * @param i amount of troops that will be moved
	 */
	public void moveTroops(String c1, String c2, int i) {
		Country[] countries = {risk.getMap().getCountry(c1), risk.getMap().getCountry(c2)};
		risk.troopsMovedEvent(i, countries);
	}

	/**
	 * returns the Winner of the Game
	 * @return
	 */
	public String getWinner() {
		return Options.getInstance().getWinner();
	}

	/**
	 * returns the name of the player with index i
	 * @param i
	 * @return
	 */
	public String getNameOfPlayer(int i) {
		return risk.getMap().getPlayer(i).getName();
	}
	
	/**
	 * sets the owner of all countries to player p
	 * @param p index of player
	 */
	public void conquerWorld(int p) {
		for(Country c:risk.getMap().getCountries())
		{
			c.setOwner(risk.getMap().getPlayer(p));
		}
	}
	
}
