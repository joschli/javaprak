package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;
import java.util.Collection;

import org.newdawn.slick.Color;

import src.de.tud.gdi1.risk.model.entities.Country;


public class Continent {
	private ArrayList<Integer> countries = new ArrayList<Integer>();
	private int bonusTroops;
	private Color color;
	public Continent(int bonusTroops, Color color){
		this.bonusTroops = bonusTroops;
		this.color = color;
	}
	
	public void addCountry(int index)
	{
		countries.add(index);
	}

	public Collection<Integer> getCountries() {
		return countries;
	}
	
	public boolean isOwned(Player player, ArrayList<Country> realCountries){
		for(int x : countries)
		{
			if(!realCountries.get(x).isOwner(player)){
				return false;
			}
		}
		return true;
	}
	
	public int getBonusTroops()
	{
		return bonusTroops;
	}

	public Color getColor() {
		return color;
	}


	
}
