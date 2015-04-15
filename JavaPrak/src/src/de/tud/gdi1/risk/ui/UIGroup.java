package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;

public class UIGroup extends UIElement{
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private Vector2f relativePosition;
	public UIGroup(String entityID, Vector2f position, Vector2f size) {
		super(entityID);
		this.setPosition(position);
		this.setSize(size);
		this.relativePosition = new Vector2f(this.getPosition().x-this.getSize().x/2, this.getPosition().y - this.getSize().y/2);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible())
		{
			g.setColor(new Color(100, 100, 100, 100));
			g.fillRect(relativePosition.x, relativePosition.y, this.getSize().x, this.getSize().y);
			for(UIElement element : components)
			{
				if(element.isVisible())
					element.render(container, game, g);
			}
		}
	}
	
	public void setUIButtonName(String ID, String content)
	{
		for(UIElement uiElement : components)
		{
			if(uiElement instanceof UIButton && uiElement.getID() == ID)
			{
				UIButton updateButton = (UIButton) uiElement;
				updateButton.setLabelName(content);
			}
		}
	}
	
	public void setUILabelName(String ID, String content)
	{
		for(UIElement uiElement : components)
		{
			if(uiElement instanceof UILabel && uiElement.getID() == ID)
			{
				UILabel updateLabel = (UILabel) uiElement;
				updateLabel.setLabelName(content);
			}
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		for(UIElement uiElement : components)
			uiElement.update(container, game, delta);
	}

	public void setCounter(Country country) {
		for(UIElement uiElement : components)
		{
			if(uiElement instanceof UICounter)
			{
				UICounter counter = (UICounter) uiElement;
				counter.setMaxCount(country.getTroops()-1 >= 3? 3 : country.getTroops());
			}
		}
	}

	public int getCounter() {
		for(UIElement uiElement : components)
		{
			if(uiElement instanceof UICounter)
			{
				UICounter counter = (UICounter) uiElement;
				return counter.getCounter();
			}
		}
		return 0;
	}
	
	public UIElement getComponent(String entityID)
	{
		for(UIElement element : components)
		{
			if(element.getID() == entityID)
			{
				return element;
			}
		}
		return null;
	}
	
	
	public void addComponent(UIElement element)
	{
		if(element != null){
			element.setPosition(new Vector2f(this.relativePosition.x + element.getPosition().x, this.relativePosition.y + element.getPosition().y));
			this.components.add(element);
		}
	}
	
	public void setPosition(Vector2f position)
	{
		super.setPosition(position);
		this.relativePosition = new Vector2f(this.getPosition().x-this.getSize().x/2, this.getPosition().y - this.getSize().y/2);
	}
	
	public void setSize(Vector2f size)
	{
		super.setSize(size);
		this.relativePosition = new Vector2f(this.getPosition().x-this.getSize().x/2, this.getPosition().y - this.getSize().y/2);
	}
	
	public void setVisible(boolean b)
	{
		super.setVisible(b);
		for(UIElement element : components)
			element.setVisible(b);
	}
	
}
