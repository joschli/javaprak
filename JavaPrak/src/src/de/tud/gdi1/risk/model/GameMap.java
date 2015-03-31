package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import src.de.tud.gd1.risk.factory.CountryFactory;
import src.de.tud.gdi1.risk.model.entities.Country;


public class GameMap {

	private ArrayList<Continent> continents;
	private CountryFactory countryFactory;
	 //TODO: Hash Map Countries?
	
	//Constructor for loading a map from a txt file
	public GameMap(String path){
		loadMap(path);
	}
	
	public GameMap()
	{
		this.continents = new ArrayList<Continent>();
		init();
	}
	
	//loads a Map txt-file and creates the continents and countries for it
	private static void loadMap(String path){
		
	}
	
	public void saveMap(String path){
		
	}
	
	public void init()
	{
		Continent A = new Continent(2);
		Continent B = new Continent(1);
		Continent C = new Continent(3);
		countryFactory = new CountryFactory("a", 1, new Vector2f(200,200));
		A.addCountry(countryFactory.createEntity());
		countryFactory.updateFactory("b", 1, new Vector2f(200,360));
		B.addCountry(countryFactory.createEntity());
		countryFactory.updateFactory("c", 5, new Vector2f(360, 200));
		C.addCountry(countryFactory.createEntity());
		countryFactory.updateFactory("d", 10, new Vector2f(360,360));
		C.addCountry(countryFactory.createEntity());
		
		continents.add(A);
		continents.add(B);
		continents.add(C);
	}
	
	public ArrayList<Country> getCountries()
	{
		ArrayList<Country> countries = new ArrayList<Country>();
		
		for(Continent c : continents)
		{
			countries.addAll(c.getCountries());
		}
		return countries;
	}
	
}
