package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Class for holding all the UIElements
 *
 */
public class UserInterface {
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>(); // all UI Components

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
	
	/**
	 * checking if a component is visible
	 * @param entityID the ID of the component to be checked
	 * @return if the component is visible
	 */
	public boolean isComponenetVisible(String entityID)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			return element.isVisible();
		return false;
	}
	
	/**
	 * sets the visibility of a component by its ID
	 * @param entityID the ID of the component you want to set the visibilty
	 * @param b if b is true the component is visible, else its not visible
	 */
	public void setVisibility(String entityID, boolean b)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			element.setVisible(b);
	}
	
	/**
	 * gets a component by its ID
	 * @param entityID the ID from the component you want to get
	 * @return the component with the entityID or null if none was found
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
	 * Adds a new UIElement to the UserInterface
	 * @param element to be added
	 */
	public void addComponent(UIElement element)
	{
		if(element != null)
			this.components.add(element);
	}
	
	/**
	 * returns all components which contains this String
	 * @param partEntityID the String that should be contained in the ID
	 * @return a ArrayList of UIElements which contain the given String in there ID
	 */
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
	
	/**
	 * Enables the Button by its ID
	 * @param entityID the ID of the Button
	 * @return if true is returned the button was enables else false
	 */
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
	
	/**
	 * disables the Button by its ID
	 * @param entityID the ID of the Button
	 * @return if true is returned the button was disabled else false
	 */
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
