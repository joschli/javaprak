package src.de.tud.gdi1.risk.model;
import org.newdawn.slick.Color;

import src.de.tud.gdi1.risk.model.entities.Card;

import java.util.ArrayList;

public class Player {

	private Color color;
	private String name;
	private Mission mission;
	private ArrayList<Card> cardlist;
	private int reinforcementUnits;
	
	public Player(Color color, String name){
		this.color = color;
		this.name  = name;
		this.cardlist = new ArrayList<Card>();
		this.reinforcementUnits = 0;
	}
	
	public void assignMission(Mission mission){
		this.mission = mission;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String getName(){
		return name;
	}
	
	public String getMissionText(){
		return mission.getMissionText();
	}
	
	/**
	 * checks if the player fullfilled his mission.
	 * @param map the GameMap
	 * @return true if the player fullfilled his mission, else false
	 */
	public boolean checkMissionForWin(GameMap map){
		return mission.isFullfilled(map,this);
	}
	

	public void addCard(Card card)
	{
		cardlist.add(card);
	}
	
	public void removeCard(Card card)
	{
		cardlist.remove(card);
	}
	
	public ArrayList<Card> getCardList()
	{
		return cardlist;
	}
	
	/**
	 * adds reinforcements to the players reinforcement troops
	 * @param reinforcement to be added
	 */
	public void addReinforcement(int reinforcement)
	{
		this.reinforcementUnits += reinforcement;
	}
	
	/**
	 * subtracts reinforcements from the players reinforcement troops
	 * @param units to subtract
	 */
	public void substractReinforcement(int units)
	{
		this.reinforcementUnits -= units;
	}

	public int getReinforcement() {
		return this.reinforcementUnits;
	}

	/**
	 * removes Cards from the players cards.
	 * @param cards to be removed
	 */
	public void removeCards(Card[] cards) {
		for(Card c: cards)
			removeCard(c);
	}
	
	
}
