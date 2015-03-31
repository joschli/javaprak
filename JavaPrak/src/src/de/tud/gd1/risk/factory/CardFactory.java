package src.de.tud.gd1.risk.factory;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import src.de.tud.gdi1.risk.model.Card;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.Action;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.interfaces.IEntityFactory;

public class CardFactory implements IEntityFactory{
	
	private Country country;
	private int unitValue;
	private Action action;
	private Card card;
	private Vector2f position;
	
	public CardFactory(Country country, Vector2f position, Action action, int unitValue)
	{
		this.country = country;
		this.position = position;
		this.action = action;
		this.unitValue = unitValue;
	}

	@Override
	public Entity createEntity() {
		try
		{
			float scale = 0.312f;
			card = new Card(country, unitValue);
			card.setPosition(position);
			card.setVisible(false);
			card.setScale(scale);
			card.addComponent(new ImageRenderComponent(new Image("assets/country.jpg")));
			ANDEvent mainEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
			mainEvent.addAction(action);
			card.addComponent(mainEvent);
			return card;
		}
		catch(SlickException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateFactory(Country country, Vector2f position, Action action, int unitValue)
	{
		this.country = country;
		this.position = position;
		this.action = action;
		this.unitValue = unitValue;
	}

}
