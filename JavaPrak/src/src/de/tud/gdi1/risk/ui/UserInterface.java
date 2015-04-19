package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class UserInterface {
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();

	public UserInterface()
	{
		
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		for(UIElement element : components)
			element.render(container, game, g);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		for(UIElement element : components)
			element.update(container, game, delta);
	}
	
	public ArrayList<UIElement> getElements()
	{
		return components;
	}
	
	public void addComponenet(UIElement element)
	{
		if(element != null)
			this.components.add(element);
	}
	
	public boolean isComponenetVisible(String entityID)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			return element.isVisible();
		return false;
	}
	
	public void setVisibility(String entityID, boolean b)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			element.setVisible(b);
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
		if(element != null)
			this.components.add(element);
	}
	
	public ArrayList<UIElement> getComponents(String partEntityID)
	{
		ArrayList<UIElement> elements = new ArrayList<UIElement>();
		for(UIElement element : components)
		{
			if(element.getID().contains(partEntityID))
				elements.add(element);
		}
		return elements;
	}
	
	public boolean enableButton(String entityID)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null && element instanceof UIButton){
			UIButton button = (UIButton) this.getComponent(entityID);
			button.enableButton();
			return true;
		}
		return false;
	}
	
	public boolean disableButton(String entityID){
		UIElement element = this.getComponent(entityID);
		if(element != null && element instanceof UIButton){
			UIButton button = (UIButton) this.getComponent(entityID);
			button.disableButton();
			return true;
		}
		return false;
		
	}
	
}
