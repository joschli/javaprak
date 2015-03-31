package src.de.tud.gdi1.risk.model;
import org.newdawn.slick.Color;
import java.util.ArrayList;


public class Player {

	private Color color;
	private String name;
	private Mission mission;
	private int ownedCountries;
	private ArrayList<Card> cardlist;
	private int reinforcementUnits;
	
	public Player(Color color, String name){
		this.color = color;
		this.name  = name;
		this.ownedCountries = 0;
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
	
	public boolean checkMissionForWin(){
		return mission.isFullfilled();
	}
	
	public int getOwnedCountries(){
		return ownedCountries;
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
	
	public void addReinforcement(int reinforcement)
	{
		this.reinforcementUnits = reinforcement;
	}
	
	public void substractReinforcement(int units)
	{
		this.reinforcementUnits -= units;
	}

	public int getReinforcement() {
		return this.reinforcementUnits;
	}
	
	
}