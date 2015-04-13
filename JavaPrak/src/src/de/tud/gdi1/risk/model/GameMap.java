package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import src.de.tud.gd1.risk.factory.CountryFactory;
import src.de.tud.gdi1.risk.model.entities.Country;


public class GameMap {

	private ArrayList<Continent> continents;
	private CountryFactory countryFactory;
	private Player[] players;
	private Mission[] missions;
	private ArrayList<Country> countries = new ArrayList<Country>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	//Constructor for loading a map from a txt file
	public GameMap(String path){
		loadMap(path);
	}
	
	public GameMap(Player[] players)
	{
		this.continents = new ArrayList<Continent>();
		this.players = new Player[players.length];
		this.players =	players;
		
		init();
		assignCountries();
		initPossibleMissions();
		createCards();
		createReinforcements();
		colorizeCountries();
	}
	
	private void colorizeCountries() {
		for(Continent conti : continents)
		{
			for(int index : conti.getCountries())
			{
				countries.get(index).setColor(conti.getColor());
			}
		}
	}

	private void createReinforcements() {
		for(Player p : players)
		{
			p.addReinforcement((2 * getCountries().size())/players.length);	
				
		}
	}

	//loads a Map txt-file and creates the continents and countries for it
	private static void loadMap(String path){
		
	}
	
	public void saveMap(String path){
		
	}
	
	public void init()
	{
		Continent A = new Continent(2, Color.cyan);
		Continent B = new Continent(1, Color.magenta);
		Continent C = new Continent(3, Color.yellow);
		countryFactory = new CountryFactory("a", 1, new Vector2f(200,200));
		Country a = (Country) countryFactory.createEntity();
		A.addCountry(0);
		countries.add(a);
		countryFactory.updateFactory("b", 1, new Vector2f(200,360));
		Country b = (Country) countryFactory.createEntity();
		B.addCountry(1);
		countries.add(b);
		countryFactory.updateFactory("c", 5, new Vector2f(360, 200));
		Country c = (Country) countryFactory.createEntity();
		C.addCountry(2);
		countries.add(c);
		countryFactory.updateFactory("d", 10, new Vector2f(360,360));
		Country d = (Country) countryFactory.createEntity();
		C.addCountry(3);
		countries.add(d);
		
		continents.add(A);
		continents.add(B);
		continents.add(C);
	}
	
	public void init(String path)
	{
		
	}
	
	public ArrayList<Country> getCountries()
	{
		return countries;
	}
	
	public ArrayList<Continent> getContinents()
	{
		return continents;
		
	}
	
	public void setCountries(ArrayList<Country> c)
	{
		countries = c;
	}
	
	public Player[] getPlayers()
	{
		return players;
	}
	
	public Player getPlayer(int index)
	{
		return players[index];
	}
	
	public int getOwnedCountriesForPlayer(int index)
	{
		int count = 0;
		
		for(Country c : getCountries())
		{
			if(c.isOwner(players[index]))
				count++;
		}
		
		return count;
		
	}
	
	
	
	private void assignCountries() {
		
		int index = 0;
		int playerIndex = 0;
		while(index < countries.size())
		{
			int random = (int) (Math.random() * countries.size());
			while(countries.get(random).hasOwner())
			{
				random = (int) (Math.random() * countries.size());
			}
			countries.get(random).setOwner(players[playerIndex]);
			countries.get(random).addTroops(1);
			playerIndex = (playerIndex == players.length-1) ? 0 : playerIndex+1;
			index++;
		}
		
		for(int i = 0; i < countries.size(); ++i)
		{
			System.out.println("Country: " + countries.get(i).getName() + " | Owner: " + countries.get(i).getOwner().getName());
		}
	}
	
	private void initPossibleMissions()
	{
		missions = new Mission[10];
		//String missionText, Player player,
		//ArrayList<Continent> continents, int countryCount
		missions[0] = new Mission("Defeat Player 0", players[0], null, -1);
		missions[1] = new Mission("Defeat Player 1", players[1], null, -1);
		ArrayList<Continent> con = new ArrayList<Continent>();
		con.add(continents.get(0));
		con.add(continents.get(2));
		missions[2] = new Mission("Conquer the following Continents: A,C",null , con, -1);
		con.clear();
		con.add(continents.get(0));
		con.add(continents.get(1));
		missions[3] = new Mission("Conquer the following Continents: A,B", null, con, -1);
		missions[4] = new Mission("Conquer 4 Countries", null, null, 4);
		/*
		missions[5] = new Mission("Defeat Player 0", players[0], null, -1);
		missions[6] = new Mission("Defeat Player 0", players[0], null, -1);
		missions[7] = new Mission("Defeat Player 0", players[0], null, -1);
		missions[8] = new Mission("Defeat Player 0", players[0], null, -1);
		missions[9] = new Mission("Defeat Player 0", players[0], null, -1);
		*/
		boolean[] taken = new boolean[10];
		for(Player p: players)
		{
			int random = (int) (Math.random() * missions.length);
			while (taken[random])
				 random = (int) (Math.random() * missions.length);
			p.assignMission(missions[random]);
			taken[random] = true;	
		}
		
	}



	private void createCards() {
		for(Country c : countries)
		{
			cards.add(new Card(c, c.getCardValue()));
		}
	}

	public Card getRandomCard() {
		int random = (int) (Math.random() * cards.size());
		return cards.remove(random);
	}
}
