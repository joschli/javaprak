import java.util.ArrayList;


public class Mission {

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
