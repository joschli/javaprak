package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;

import src.de.tud.gdi1.risk.model.entities.Country;


public class Mission {

	public Mission(String missionText, Player player,
			ArrayList<Continent> continents, int countryCount,
			int troopsPerCountry,boolean optionalContinent) {
		this.missionText = missionText;
		this.player = player;
		this.continents = continents;
		this.countryCount = countryCount;
		this.optionalContinent = optionalContinent;
		this.troopsPerCountry = troopsPerCountry;
	}
	/**
	 * Constructor for defeat player x or conquer y countries
	 * @param missionText
	 * @param player
	 * @param countryCount
	 */
	public Mission(String missionText, Player player,
			 int countryCount) {
		this(missionText, player, new ArrayList<Continent>(), countryCount, 0, false);
	}
	/**
	 * Constructor for x Continents and one other Continent
	 * @param missionText
	 * @param continents
	 * @param optionalContinent
	 */
	public Mission(String missionText,ArrayList<Continent> continents,
			boolean optionalContinent) {
		
		this(missionText, null, continents, 0, 0, optionalContinent);
	}
	/**
	 * Constructor for x Continents
	 * @param missionText
	 * @param continents
	 */
	public Mission(String missionText,ArrayList<Continent> continents) {
		
		this(missionText, null, continents, 0, 0, false);
	}
	/**
	 * Constructor for x Countries with Y troops
	 * @param missionText
	 * @param countryCount
	 * @param troopsPerCountry
	 */
	public Mission(String missionText,int countryCount, int troopsPerCountry) {
		
		this(missionText, null, new ArrayList<Continent>(), countryCount, troopsPerCountry, false);
	}
	/**
	 * Constructor for Conquer X Countries
	 * @param missionText
	 * @param countryCount
	 */
	public Mission(String missionText, int countryCount)
	{
		this(missionText, null, new ArrayList<Continent>(), countryCount, 0 , false);
	}
	/**
	 * Constructor for Defeat Player X
	 * DONT USE IN REAL GAME SINCE PLAYERS MIGHT HAVE TO DEFEAT THEMSELVES
	 * @param missionText
	 * @param player
	 */
	public Mission(String missionText, Player player)
	{
		this(missionText, player , new ArrayList<Continent>(),0,0,false); 
	}
	
	

	private String missionText;
	private Player player;
	private ArrayList<Continent> continents;
	private boolean optionalContinent;
	private int troopsPerCountry = 0;
	private int countryCount = 0;

	
	public boolean isFullfilled(GameMap map, Player p){
		System.out.println(p.getMissionText());
		System.out.println(missionText + " -> " + (player!= null? player.getName() : "Null") + " -> " + optionalContinent + " -> " + countryCount);
		if(player != null)
		{
			if(player != p)
			{
				if(map.getOwnedCountriesForPlayer(player) == 0)
					return true;
			}
			else
			{
				if(map.getOwnedCountriesForPlayer(p) >= countryCount)
					return true;
			}
			return false;
		}
		
		if(!continents.isEmpty())
		{
			for(Continent c : continents)
			{
				if(!c.isOwned(p, map.getCountries()))
					return false;
			}
			if(optionalContinent)
			{
				for(Continent c : map.getContinents())
				{
					if(c.isOwned(p, map.getCountries())&& !continents.contains(c))
					{
						return true;
					}
				}
				return false;
			}
			else
			{
				return true;	
			}
			
		}
		
		if(countryCount > 0)
		{
			ArrayList<Country> countries = map.getCountryListForPlayer(p);
			if(countries.size() >= countryCount)
			{
				if(troopsPerCountry > 0)
				{
					int count = 0;
					for(Country c: countries)
					{
						if(c.getTroops() >= troopsPerCountry)
						{
							count ++;
						}
					}
					if(count >= countryCount )
					{
						return true;
					}else
					{
						return false;
					}
				}
				return true;
			}
			else
				return false;
		}
		return false;
	}
	
	public String getMissionText(){
		return missionText;
	}
}
