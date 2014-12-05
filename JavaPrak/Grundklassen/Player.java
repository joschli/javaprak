import java.awt.Color;
import java.util.ArrayList;


public class Player {

	private Color color;
	private String name;
	private Mission mission;
	private int ownedCountries;
	private ArrayList<Card> cardlist;
	
	public Player(Color color, String name){
		this.color = color;
		this.name  = name;
		this.ownedCountries = 0;
		this.cardlist = new ArrayList<Card>();
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
	
	
	
}
