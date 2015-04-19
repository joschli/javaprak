package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import eea.engine.component.render.ImageRenderComponent;

/**
 * Class for grouping UIElements and drawing them relative to this Group
 * @author jonas_000
 *
 */
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

	/**
	 * Gets a component by its ID
	 * @param entityID the ID of the entity that is needed
	 * @return the entity with this ID or null if none is found
	 */
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
	
	/**
	 * Adds a UIElement to the UIGroup, repositioning in the groupWindow
	 * @param element to be added
	 */
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
	
	/**
	 * Sets the Visibility of the whole Group
	 */
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
	
	/**
	 * Sets a background of the groupWindow
	 * @param component ImageRenderComponent that should be drawn as background
	 */
	public void setRenderComponent(ImageRenderComponent component)
	{
		this.imageRenderComponent = component;
		this.imageRenderComponent.setOwnerEntity(this);
	}
	
	/**
	 * Sets the Visibility of a single component by its ID
	 * @param entityID the Entitys ID 
	 * @param b the Visibility of the Entity
	 */
	public void setComponentVisiblity(String entityID, boolean b)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			element.setVisible(b);
	}

	/**
	 * sets a border around the group Window
	 * @param b true if there should be a border, else false
	 * @param i width of the border
	 * @param color color of the border
	 */
	public void setBorder(boolean b, int i, Color color) {
		this.border = b;
		this.borderWidth = i;
		this.borderColor = color;
	}
	
}
