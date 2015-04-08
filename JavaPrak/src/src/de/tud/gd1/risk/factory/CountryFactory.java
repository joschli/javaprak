package src.de.tud.gd1.risk.factory;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.interfaces.IEntityFactory;
import src.de.tud.gd1.risk.actions.SelectAction;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.GameplayState;

public class CountryFactory implements IEntityFactory {

	private String name;
	private Vector2f position;
	private Country country;
	private int cardValue;
	
	public CountryFactory(String name, int cardValue, Vector2f position)
	{
		this.name = name;
		this.position = position;
		this.cardValue = cardValue;
	}
	
	@Override
	public Entity createEntity()
	{
		float scale = 0.312f;
		country = new Country(name, cardValue);
		country.setPosition(position);
		
		country.setSize(new Vector2f(100,100));
		country.setScale(scale);
		//country.addComponent(new ImageRenderComponent(new Image("assets/country.jpg")));
		ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		mainEvents.addAction(new SelectAction());
		country.addComponent(mainEvents);
		return country;
	}
	
	public void updateFactory(String name, int cardValue, Vector2f position)
	{
		this.name = name;
		this.cardValue = cardValue;
		//this.action = action;
		this.position = position;
	}
}
