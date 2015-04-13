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

public class UIWindow extends UIElement{
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private Vector2f relativePosition;
	public UIWindow(String entityID, Vector2f position, Vector2f size) {
		super(entityID);
		this.setPosition(position);
		this.setSize(size);
		this.relativePosition = new Vector2f(this.getPosition().x-this.getSize().x/2, this.getPosition().y - this.getSize().y/2);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible())
		{
			g.setColor(Color.black);
			g.fillRect(relativePosition.x, relativePosition.y, this.getSize().x, this.getSize().y);
			for(UIElement element : components)
			{
				element.render(container, game, g);
			}
		}
	}
	
	public void addLabel(String name, String content, float x, float y, Color color){
		UILabel label = new UILabel(name, content, color, new Vector2f(this.relativePosition.x + x,this.relativePosition.y + y));
		components.add(label);
	}
	
	public void addButton(String name, String content, float x, float y, float width, float height, Vector2f padding, Color color, Component event)
	{
		UIButton button = new UIButton(name, content, new Vector2f(this.relativePosition.x + x,this.relativePosition.y + y), new Vector2f(width,height), padding, color.gray, color);
		button.addComponent(event);
		components.add(button);
	}
	
	public void addCounter(String name, float x, float y, int maxCount, int minCount)
	{
		UICounter counter = new UICounter(name, new Vector2f(this.relativePosition.x+x, this.relativePosition.y+y), maxCount, minCount);
		components.add(counter);
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
	
}
