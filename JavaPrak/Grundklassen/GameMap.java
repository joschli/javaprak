import java.util.ArrayList;


public class GameMap {

	private ArrayList<Continent> continents;
	 //TODO: Hash Map Countries??
	
	public GameMap(ArrayList<Continent> continents){
		this.continents = continents;
	}
	
	//Constructor for loading a map from a txt file
	public GameMap(String path){
		loadMap(path);
	}
	
	//loads a Map txt-file and creates the continents and countries for it
	private static void loadMap(String path){
		
	}
	
	public void saveMap(String path){
		
	}
	
}
