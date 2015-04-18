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
import eea.engine.component.RenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;

public class UIGroup extends UIElement{
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private Vector2f relativePosition;
	private ImageRenderComponent imageRenderComponent;
	private boolean border;
	private int borderWidth;
	private Color borderColor;
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
			if(imageRenderComponent == null)
			{
				g.setColor(new Color(100, 100, 100, 100));
				g.fillRect(relativePosition.x, relativePosition.y, this.getSize().x, this.getSize().y);
			}
			else
			{
				
				this.imageRenderComponent.render(container, game, g);
			}
			if(border)
			{
				g.setColor(this.borderColor);
				g.setLineWidth(borderWidth);
				g.drawRect(relativePosition.x, relativePosition.y, this.getSize().x, this.getSize().y);
				g.setLineWidth(1);
			}
			for(UIElement element : components)
			{
				if(element.isVisible())
					element.render(container, game, g);
			}
		}
	}

	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		for(UIElement uiElement : components)
			uiElement.update(container, game, delta);
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
		for(UIElement element : this.components)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				if(!b)
					button.disableButton();
				else 
					button.enableButton();
			}
	}
	
	public void setRenderComponent(ImageRenderComponent component)
	{
		this.imageRenderComponent = component;
		this.imageRenderComponent.setOwnerEntity(this);
	}
	
	public void setComponentVisiblity(String entityID, boolean b)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			element.setVisible(b);
	}

	public void setBorder(boolean b, int i, Color color) {
		this.border = b;
		this.borderWidth = i;
		this.borderColor = color;
	}
	
}
