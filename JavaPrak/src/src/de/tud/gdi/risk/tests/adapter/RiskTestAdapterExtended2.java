package src.de.tud.gdi.risk.tests.adapter;

import java.util.ArrayList;

import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Card;

public class RiskTestAdapterExtended2 extends RiskTestAdapterExtended1 {

	public static final int INFANTRY = 1;
	public static final int CAVALRY = 2;
	public static final int ARTILLERY = 3;
	
	/**
	 * This should skip the starting phase by automatically placing units randomly
	 */
	public void skipToReinforcementPhase() {
		for(Player p : risk.getMap().getPlayers())
		{
			p.substractReinforcement(p.getReinforcement());
			risk.endTurn();
		}
		risk.update();
	}
	/**
	 * gives a number of cards i to player p
	 * @param i number of random cards to be added
	 * @param p index of player
	 */
	public void giveCardsToPlayer(int i, int p) {
		for(int j = 0; j < i; j++)
			risk.getMap().getPlayer(p).addCard(risk.getMap().getRandomCard());	
	}
	/**
	 * should return all CardValues of cards that player p owns at the moment
	 * they are not meant to be unique which means
	 * that the first in the return is the value of the first card
	 * @param p
	 * @return
	 */
	public ArrayList<Integer> getPlayerCardValues(int p) {
	
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(Card c :risk.getMap().getPlayer(p).getCardList())
		{
			ret.add(c.getValue());
		}
		return ret;
	}
	/**
	 * should trade in cards for player p
	 * where card are the indices of the cards to be traded in 
	 * 
	 * @param cards indices of cards from the player to be traded in
	 * @param p index of the player
	 */
	public void tradeInCardsForPlayer(int[] cards, int p) {
		Card[] c = new Card[3];
		
		for(int i = 0; i < cards.length; i++)
		{
			c[i] = risk.getMap().getPlayer(p).getCardList().get(cards[i]);
		}
		risk.tradeInCards(c);
	}
	/**
	 * same as getPlayerCardValues but instead of returning the values
	 * it should return the names of the countries
	 * @param p index of the player
	 * @return names of the countries on players cards
	 */
	public ArrayList<String> getCardCountriesForPlayer(int p) {
		ArrayList<String> ret = new ArrayList<String>();
		for(Card c :risk.getMap().getPlayer(p).getCardList())
		{
			ret.add(c.getCountry().getName());
		}
		return ret;
	}
	
	/**
	 * returns all Cards that are generated at the beginning of the game
	 * @returns all Countrynames on all Cards
	 */
	public ArrayList<String> getCardCountries() {
		return risk.getMap().getAllCardCountryNames();
		
	}
	
	
	/**
	 * returns all Cards that are generated at the beginning of the game
	 * @returns all countryValues on all Cards
	 */
	public ArrayList<Integer> getCardValues() {
		return risk.getMap().getAllCardValues();
	}

}
