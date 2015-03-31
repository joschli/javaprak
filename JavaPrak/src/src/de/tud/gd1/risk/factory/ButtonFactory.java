package src.de.tud.gd1.risk.factory;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.Action;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.interfaces.IEntityFactory;

public class ButtonFactory implements IEntityFactory{
	
	private String name;
	private Action action;
	private int entry_height = 0;
	private Entity entry;
	
	public ButtonFactory(String name, Action action, int entry_height)
	{
		this.name = name;
		this.action = action;
		this.entry_height = entry_height;
	}

	@Override
	public Entity createEntity() {
		try
		{
		float scale = 0.312f;
		
		entry = new Entity(name);
		entry.setPosition(new Vector2f(400, entry_height));
		entry.setScale(scale);
		entry.addComponent(new ImageRenderComponent(new Image("assets/entry.png")));
		ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		mainEvents.addAction(action);
		entry.addComponent(mainEvents);
		return entry;
		} catch (SlickException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateFactory(String name, Action action, int entry_height)
	{
		this.name = name;
		this.action = action;
		this.entry_height = entry_height;
	}

}
