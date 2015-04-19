package src.de.tud.gdi1.risk.model;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;






import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import src.de.tud.gd1.risk.factory.CountryFactory;
import src.de.tud.gdi1.risk.model.entities.Card;
import src.de.tud.gdi1.risk.model.entities.Country;

public class GameMap {

	private static final String MISSIONS_FILE = "missions.txt";
	private static final int NR_OF_COUNTRIES_FOR_DEFEAT_MISSION = 24;
	private static final int TESTVALUE = 60;
	private ArrayList<Continent> continents;
	private CountryFactory countryFactory;
	private Player[] players;
	private ArrayList<Mission> missions;
	private ArrayList<Country> countries = new ArrayList<Country>();
	private ArrayList<Card> cards = new ArrayList<Card>();


	public GameMap(Player[] players, boolean missionMode) throws IOException {
		this.continents = new ArrayList<Continent>();
		this.players = new Player[players.length];
		this.players = players;

		init("world.txt");
		assignCountries();
		initPossibleMissions(missionMode);
		createCards();
		createReinforcements();
		colorizeCountries();
	}

	/**
	 * This method sets the Color of every Country on the Color of the continent it belongs to
	 */
	private void colorizeCountries() {
		for (Continent conti : continents) {
			for (int index : conti.getCountries()) {
				countries.get(index).setColor(conti.getColor());
			}
		}
	}
	
	/**
	 * This gives all Players new Reinforcements
	*/
	private void createReinforcements() {
		for (Player p : players) {
			//p.addReinforcement(3);
			p.addReinforcement((2 * getCountries().size()) / players.length);

		}
	}
	
	/**
	 * Not in use anymore, but this might be an init method for Minimal
	 */
	public void init() {
		Continent A = new Continent(2, new Color(100,0,0,100), "A");
		Continent B = new Continent(1, new Color(0,100,0,100), "B");
		Continent C = new Continent(3, new Color(0,0,100,100), "C");
		countryFactory = new CountryFactory("a",  new Vector2f(200, 200));
		Country a = (Country) countryFactory.createEntity();
		A.addCountry(0);
		countries.add(a);
		countryFactory.updateFactory("b", new Vector2f(200, 360));
		Country b = (Country) countryFactory.createEntity();
		B.addCountry(1);
		countries.add(b);
		countryFactory.updateFactory("c", new Vector2f(360, 200));
		Country c = (Country) countryFactory.createEntity();
		C.addCountry(2);
		countries.add(c);
		countryFactory.updateFactory("d", new Vector2f(360, 360));
		Country d = (Country) countryFactory.createEntity();
		C.addCountry(3);
		countries.add(d);
		ArrayList<Country> neighbors = new ArrayList<Country>();
		neighbors.add(b);
		neighbors.add(c);
		neighbors.add(d);
		a.setNeighbors(neighbors);
		ArrayList<Country> neighbors2 = new ArrayList<Country>();
		neighbors2.add(a);
		neighbors2.add(c);
		neighbors2.add(d);
		b.setNeighbors(neighbors2);
		ArrayList<Country> neighbors3= new ArrayList<Country>();
		neighbors3.add(a);
		neighbors3.add(b);
		neighbors3.add(d);
		c.setNeighbors(neighbors3);
		ArrayList<Country> neighbors4 = new ArrayList<Country>();
		neighbors4.add(b);
		neighbors4.add(c);
		neighbors4.add(a);
		d.setNeighbors(neighbors4);

		continents.add(A);
		continents.add(B);
		continents.add(C);
	}

	/**
	 * Initialization Method for a map
	 * reads all data from a given path
	 * Might throw an IOException if the file cant be found or 
	 * is in any other way corrupt
	 * @param path to the world map if in assets its just filename
	 * @throws IOException
	 */
	public void init(String path) throws IOException {
		
		countryFactory = new CountryFactory("", new Vector2f(0,0));
		String name = "";
		Vector2f position = new Vector2f(0,0);
		ArrayList<String> neighborStrings = new ArrayList<String>();
		int count = 0;
		int bonusTroops = 0;
		int r = 0;
		int b = 0;
		int g = 0;
		Path f  = FileSystems.getDefault().getPath("src/assets", path);
		
		for (String line : Files.readAllLines(f)) {
			if(line.startsWith("-"))
			{
				count++;
				continue;
			}
			
			if(count == 2)
			{
				
				String[] entrys = line.split("\\|");
				
			    name = entrys[0];
			    String[] colorValues = entrys[1].split(",");
			    r = Integer.parseInt(colorValues[0]);
			    b = Integer.parseInt(colorValues[1]);
			    g = Integer.parseInt(colorValues[2]);
			    bonusTroops = Integer.parseInt(entrys[2]);
			    continents.add(new Continent(bonusTroops, new Color(r,g,b,0), name));
			}
			
			if(count == 3)
			{
					
				name = "";
				position = new Vector2f(0,0);
				
				String[] entrys = line.split("\\|");
			    name = entrys[0];
			    neighborStrings.add(entrys[1]);
			    String[] coordinates = entrys[3].split(",");
			    position.x = Integer.parseInt(coordinates[0]) ;
			    position.y = Integer.parseInt(coordinates[1]) + TESTVALUE;
			    countryFactory.updateFactory(name, position);
			    
			    countries.add((Country) countryFactory.createEntity());
			    this.getContinent(entrys[2]).addCountry(countries.size()-1);
			    
			}
			
		}
		
		ArrayList<Country> neighbors;
		for(int i = 0; i < neighborStrings.size(); i++)
		{
			neighbors = new ArrayList<Country>();
			for(String country : neighborStrings.get(i).split(","))
			{
				neighbors.add(getCountry(country));
			}
			countries.get(i).setNeighbors(neighbors);
			
		}
	}
	
	/**
	 * 
	 * @param name of a country
	 * @returns country which has name as name
	 */
	public Country getCountry(String name)
	{
		for(Country c: countries)
		{
			if(c.getName().equals(name))
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param name of a continent
	 * @returns continent which has name as name
	 */
	public Continent getContinent(String name)
	{
		for(Continent c : continents)
			if(c.getName().equals(name))
				return c;
		return null;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public ArrayList<Continent> getContinents() {
		return continents;

	}

	
	public void setCountries(ArrayList<Country> c) {
		countries = c;
	}

	public Player[] getPlayers() {
		return players;
	}

	/**
	 * 
	 * @param index of the player
	 * @returns player which is the indexth player of the game starting with 0 
	 */
	public Player getPlayer(int index) {
		return players[index];
	}

	/**
	 * 
	 * @param index of the player
	 * @returns number of owned countries for a player
	 */
	public int getOwnedCountriesForPlayer(int index) {
		return getOwnedCountriesForPlayer(players[index]);
	}
	
	/**
	 * 
	 * @param player a player
	 * @returns number of owned countries for a player
	 */
	public int getOwnedCountriesForPlayer(Player player){
		int count = 0;

		for (Country c : getCountries()) {
			if (c.isOwner(player))

				count++;
		}

		return count;

	}
	
	/**
	 * 
	 * @param player
	 * @returns a list of countries for the player
	 */
	public ArrayList<Country> getCountryListForPlayer(Player player)
	{
		ArrayList<Country> ret = new ArrayList<Country>();
		for (Country c : getCountries()) {
			if(c.isOwner(player))
			{
				ret.add(c);
			}
		}
		return ret;
	}
	
	/**
	 * used to assign Countries to players at the start of the game
	 * Automatically adds 1 troop to a  country for the new Owner
	 */
	private void assignCountries() {

		int index = 0;
		int playerIndex = 0;
		while (index < countries.size()) {
			int random = (int) (Math.random() * countries.size());
			while (countries.get(random).hasOwner()) {
				random = (int) (Math.random() * countries.size());
			}
			countries.get(random).setOwner(players[playerIndex]);
			countries.get(random).addTroops(1);
			playerIndex = (playerIndex == players.length - 1) ? 0
					: playerIndex + 1;
			index++;
		}
	}

	/**
	 * initializes all risk Missions from the missions.txt file
	 * If the mode is world domination it creates as many Conquer World missions as there 
	 * are players in the game
	 * @param missionMode true = mission mode false= Worlddominationmode
	 * @throws IOException
	 */
	private void initPossibleMissions(boolean missionMode) throws IOException
	{
		
		if(missionMode)
		{

			missions = new ArrayList<Mission>();
			//String missionText, Player player,
			//ArrayList<Continent> continents, int countryCount
	
			for(int i = 0; i < players.length; i++)
			{
				missions.add( new Mission("Defeat " + players[i].getName() + "! If you are " +players[i].getName() + " conquer " 
						+ NR_OF_COUNTRIES_FOR_DEFEAT_MISSION + " countries instead!", players[i], NR_OF_COUNTRIES_FOR_DEFEAT_MISSION));	
			}
			
			Path f  = FileSystems.getDefault().getPath("src/assets", MISSIONS_FILE);
	
			for (String line : Files.readAllLines(f)) {
				if(line.startsWith("1"))
				{
					ArrayList<Continent> consToConquer = new ArrayList<Continent>();
					String [] cons = line.split("-")[1].split(",");
					consToConquer.add(this.getContinent(cons[0]));
					consToConquer.add(this.getContinent(cons[1]));
					missions.add(new Mission("Conquer " + cons[0] + " and " + cons[1] + " and a third continent!", consToConquer, true));
				}
				
				if(line.startsWith("2"))
				{
					ArrayList<Continent> consToConquer = new ArrayList<Continent>();
					String [] cons = line.split("-")[1].split(",");
					consToConquer.add(this.getContinent(cons[0]));
					consToConquer.add(this.getContinent(cons[1]));
					missions.add(new Mission("Conquer " + cons[0] + " and " + cons[1] + "!", consToConquer));
				}
				
				if(line.startsWith("3"))
				{
					int countryCount = Integer.parseInt(line.split("-")[1]);
					missions.add(new Mission("Conquer " + countryCount + " Countries!", countryCount));
				}
				
				if(line.startsWith("4"))
				{
					int countryCount = Integer.parseInt(line.split("-")[1].split("\\|")[0]);
					int troopCount = Integer.parseInt(line.split("-")[1].split("\\|")[1]);
					missions.add(new Mission("Conquer " + countryCount + " Countries and have " + troopCount+" troops in them", countryCount, troopCount));
				}
			
			}	
		}else
		{
			missions = new ArrayList<Mission>();
			for(@SuppressWarnings("unused") Player p : players)
			{
				missions.add(new Mission("Conquer the World!", countries.size()));
			}
		}
		
		/*
		for(Mission m : missions)
		{
			//System.out.println(m.getMissionText());
		}
		*/
		boolean[] taken = new boolean[missions.size()];
		for (Player p : players) {
			int random = (int) (Math.random() * missions.size());
			while (taken[random])
				random = (int) (Math.random() * missions.size());
			p.assignMission(missions.get(random));
			taken[random] = true;
		}

	}

	/**
	 * Creates all cards at the beginning
	 * For every country a card is created and a random value is assigned to the card
	 * The Value is between 1 and 3 for the Values that a card can have
	 * 
	 */
	private void createCards() {
		
		@SuppressWarnings("unchecked")
		ArrayList<Country> notUsedCountries = (ArrayList<Country>) countries.clone();
		int value = 0;
		for(int i = 0; i < countries.size(); i++)
		{
			Country c = notUsedCountries.get((int) (Math.random()*notUsedCountries.size()));
			cards.add(new Card(c, value+1));
			notUsedCountries.remove(c);
			value = (value+1)%3;
		}
	
	}
	
	/**
	 * 
	 * @returns a random card from all available cards at the moment
	 */
	public Card getRandomCard() {
		int random = (int) (Math.random() * cards.size());
		return cards.remove(random);
	}

	/**
	 * adds an array of cards back to the available cards
	 * @param cardArray
	 */
	public void addCardsBack(Card[] cardArray) {
		for(Card c : cardArray)
		{
			cards.add(c);
		}
	}
	
	/**
	 * For test purposes only
	 * @returns cardValues for all cards 
	 */
	public ArrayList<Integer> getAllCardValues()
	{

		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(Card c : this.cards)
		{
			ret.add(c.getValue());
		}
		return ret;
		
	}
	
	/**
	 * For test purposes only
	 * @returns the names of all countries that are on cards
	 */
	public ArrayList<String> getAllCardCountryNames()
	{
		ArrayList<String> ret = new ArrayList<String>();
		for(Card c : this.cards)
		{
			ret.add(c.getCountry().getName());
		}
		return ret;
		
	}

	
	public int getNumberOfMissions() {
		return missions.size();
	}
}
