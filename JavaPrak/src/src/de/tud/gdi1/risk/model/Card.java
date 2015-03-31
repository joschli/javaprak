package src.de.tud.gdi1.risk.model;

import eea.engine.entity.Entity;
import src.de.tud.gdi1.risk.model.entities.Country;

public class Card extends Entity{

	private Country country;
	private int unitValue;
	
	public Card(Country country, int unitValue) {
		super(country.getName() + "Card");
		this.unitValue = unitValue;
	}

	public Country getCountry(){
		return country;
	}
	
	public int getValue()
	{
		return unitValue;
	}
}
