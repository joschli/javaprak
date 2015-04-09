package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;


public class Mission {

	public Mission(String missionText, Player player,
			ArrayList<Continent> continents, int countryCount) {
		super();
		this.missionText = missionText;
		this.player = player;
		this.continents = continents;
		this.countryCount = countryCount;
	}

	private String missionText;
	private Player player;
	private ArrayList<Continent> continents;
	private int countryCount;

	
	public boolean isFullfilled(){
		return false;
	}
	
	public String getMissionText(){
		return missionText;
	}
}
