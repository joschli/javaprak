package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Color;

import src.de.tud.gdi1.risk.model.entities.Country;


public class Continent {
	
	private ArrayList<Integer> countries = new ArrayList<Integer>();
	private int bonusTroops;
	private Color color;
	private String name;
	
	public Continent(int bonusTroops, Color color, String name){
		this.bonusTroops = bonusTroops;
		this.color = color;
		this.name = name;
	}
	
	public void addCountry(int index)
	{
		countries.add(index);
	}

	public Collection<Integer> getCountries() {
		return countries;
	}
	
	
	/**
	 * checks if a player owns the continent.
	 * @param player 
	 * @param realCountries, all countries on the map
	 * @return true if the player owns the continent, else false
	 */
	public boolean isOwned(Player player, ArrayList<Country> realCountries){
		for(int x : countries)
		{
			if(!realCountries.get(x).isOwner(player)){
				return false;
			}
		}
		return true;
	}
	
	public String getName()
	{
		return name;
	}
	public int getBonusTroops()
	{
		return bonusTroops;
	}

	public Color getColor() {
		return color;
	}


	
}
