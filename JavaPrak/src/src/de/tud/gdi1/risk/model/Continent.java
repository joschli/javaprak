package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;
import java.util.Collection;

import src.de.tud.gdi1.risk.model.entities.Country;


public class Continent {
	private ArrayList<Integer> countries = new ArrayList<Integer>();
	private int bonusTroops;
	
	public Continent(int bonusTroops){
		this.bonusTroops = bonusTroops;
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


	
}
