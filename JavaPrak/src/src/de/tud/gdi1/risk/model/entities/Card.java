package src.de.tud.gdi1.risk.model.entities;

import eea.engine.entity.Entity;

public class Card extends Entity{

	private Country country;
	private int unitValue;
	public static final int INFANTRY = 1; 
	public static final int CAVALRY = 2;
	public static final int ARTILLERY = 3;
	public static final int INFANTRYVALUE = 4; 
	public static final int CAVALRYVALUE = 6;
	public static final int ARTILLERYVALUE =  8;
	public static final int MIXEDVALUE = 10;
	public static final int COUNTRYVALUE = 2;
	
	public Card(Country country, int unitValue) {
		super(country.getName() + "Card");
		this.unitValue = unitValue;
		this.country = country;
	}

	public Country getCountry(){
		return country;
	}
	
	public int getValue()
	{
		return unitValue;
	}
}
