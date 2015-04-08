package src.de.tud.gdi1.risk.model;
import java.util.ArrayList;
import java.util.Collection;

import eea.engine.entity.Entity;
import src.de.tud.gdi1.risk.model.entities.Country;


public class Continent {
	private ArrayList<Country> countries = new ArrayList<Country>();
	private int bonusTroops;
	
	public Continent(int bonusTroops){
		this.bonusTroops = bonusTroops;
	}
	
	public void addCountry(Entity entity)
	{
		countries.add((Country) entity);
	}

	public Collection<? extends Country> getCountries() {
		return countries;
	}
	
	public boolean isOwned(Player player){
		for(Country x : countries)
		{
			if(!x.isOwner(player)){
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
