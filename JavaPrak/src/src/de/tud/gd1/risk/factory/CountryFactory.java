package src.de.tud.gd1.risk.factory;


import org.newdawn.slick.geom.Vector2f;



import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.interfaces.IEntityFactory;
import src.de.tud.gd1.risk.actions.SelectAction;

import src.de.tud.gdi1.risk.model.entities.Country;


public class CountryFactory implements IEntityFactory {

	private String name;
	private Vector2f position;
	private Country country;
	
	public CountryFactory(String name, Vector2f position)
	{
		this.name = name;
		this.position = position;
		
	}
	
	@Override
	public Entity createEntity()
	{
		float scale = 0.312f;
		country = new Country(name);
		country.setPosition(position);
		
		country.setSize(new Vector2f(50,50));
		country.setScale(scale);
		//country.addComponent(new ImageRenderComponent(new Image("assets/country.jpg")));
		ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		mainEvents.addAction(new SelectAction());
		country.addComponent(mainEvents);
		return country;
	}
	
	public void updateFactory(String name, Vector2f position)
	{
		this.name = name;
		//this.action = action;
		this.position = position;
	}
}
