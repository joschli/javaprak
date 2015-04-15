package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import src.de.tud.gd1.risk.actions.AttackAction;
import src.de.tud.gd1.risk.actions.CancelAttackAction;
import src.de.tud.gd1.risk.actions.DiceAction;
import src.de.tud.gd1.risk.actions.EndTurnAction;
import src.de.tud.gdi1.risk.model.GameMap;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;

public class UserInterface {
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private UILabel playerName, phaseName, reinforcementCount;
	private UIButton turnButton, attackButton;
	private UISelection selection_1;
	private UISelection selection_2;
	private UIGroup attackWindow;
	private GameMap map;
	private int height, width;
	private int currentPlayer, state;

	public UserInterface(int height, int width)
	{
		this.height = height;
		this.width = width;
		// Player Label
		playerName = new UILabel("playerNameLabel", null, null, new Vector2f(50,50));
		phaseName = new UILabel("phaseNameLabel", null, Color.red, new Vector2f(150,50));
		reinforcementCount = new UILabel("reinforcementCountLabel", null, null, new Vector2f(350, 50));
		// End Turn Button
		turnButton = new UIButton("turnButton", "End Turn", new Vector2f(200, 500), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		attackButton = new UIButton("attackButton", "Attack!", new Vector2f(400, 500), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		
		// Events
		ANDEvent turnEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		turnEvent.addAction(new EndTurnAction());
		turnButton.addComponent(turnEvent);
		ANDEvent attackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		attackEvent.addAction(new AttackAction());
		ANDEvent diceEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		diceEvent.addAction(new DiceAction());
		ANDEvent cancelAttackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		cancelAttackEvent.addAction(new CancelAttackAction());
		//
		attackButton.addComponent(attackEvent);
		attackButton.setVisible(false);
		selection_1 = new UISelection("selection1");
		selection_2 = new UISelection("selection2");
		selection_1.setVisible(true);
		selection_2.setVisible(true);
		// Window Overlay for ReinforcementState
		attackWindow = new UIGroup("attackGroup", new Vector2f(width-100, height-150), new Vector2f(200, 300));
		UILabel aw_description = new UILabel("description", "Attack Window", Color.red, new Vector2f(50,15));
		UICounter aw_counter = new UICounter("counter", new Vector2f(50,50), 3, 1);
		UIButton aw_diceButton = new UIButton("diceButton", "Roll the dices", new Vector2f(114,116), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton aw_cancelButton = new UIButton("cancelButton", "Cancel", new Vector2f(114, 164), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		aw_diceButton.addComponent(diceEvent);
		aw_cancelButton.addComponent(cancelAttackEvent);
		attackWindow.addComponent(aw_cancelButton);
		attackWindow.addComponent(aw_counter);
		attackWindow.addComponent(aw_diceButton);
		attackWindow.addComponent(aw_description);
		
		components.add(playerName);
		components.add(phaseName);
		components.add(reinforcementCount);
		components.add(turnButton);
		components.add(attackButton);
		components.add(selection_1);
		components.add(selection_2);
		components.add(attackWindow);
		attackWindow.setVisible(false);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		for(UIElement element : components)
			element.render(container, game, g);
	}

	public void updateData(int state, GameMap map, int currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.state = state;
		this.map = map;
		ArrayList<UIElement> labels = this.getComponents("Label");
		UILabel playerNameLabel = (UILabel) this.getComponent("playerNameLabel");
		UILabel reinforcementCountLabel = (UILabel) this.getComponent("reinforcementCountLabel");
		UIButton attackButton = (UIButton) this.getComponent("attackButton");
		UILabel phaseNameLabel = (UILabel) this.getComponent("phaseNameLabel");
		playerNameLabel.setLabelName(map.getPlayer(currentPlayer).getName());
		
		for(UIElement element : labels)
			if(element instanceof UILabel)
			{
				UILabel label = (UILabel) element;
				label.setColor(map.getPlayer(currentPlayer).getColor());
			}
		String labelName = "";
		switch(state)
		{
		case 0:
			labelName = "REINFORCEMENT";
			reinforcementCountLabel.setVisible(true);
			reinforcementCountLabel.setLabelName("Reinforcements: " + map.getPlayer(currentPlayer).getReinforcement());
			
			break;
		case 1:
			labelName = "ATTACKPHASE";
			if(!this.isComponenetVisible("attackGroup"))
				attackButton.setVisible(true);
			reinforcementCountLabel.setVisible(false);
			break;
		case 2:
			labelName = "FORTIFY";
			break;
		case 3:
			labelName = "STARTINGPHASE";
			reinforcementCountLabel.setVisible(true);
			reinforcementCountLabel.setLabelName("Reinforcements: " + map.getPlayer(currentPlayer).getReinforcement());
			break;
		}
		phaseNameLabel.setLabelName(labelName);
		
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
	
	public void updateSelection(Country country)
	{
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		UISelection selection_2 = (UISelection) this.getComponent("selection2");
		if(selection_1.hasEntitySelected() && selection_1.getSelectedEntity().getID() == country.getID())
		{
			selection_1.resetSelection();
			selection_2.resetSelection();
		}
		else if(selection_2.hasEntitySelected() && selection_2.getSelectedEntity().getPosition().equals(country.getPosition()))
			selection_2.resetSelection();
		else if(selection_1.hasEntitySelected()){
			selection_2.selectEntity(country);
		}
		else{
			selection_1.selectEntity(country);
		}
		System.out.println("Selector1:" + selection_1.hasEntitySelected());
		System.out.println("Selector2:" + selection_2.hasEntitySelected());
	}

	public int getReinforcement() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void requestTroopMovement(int min, int max) {
		// TODO Auto-generated method stub
		
	}
	
	public Entity getFirstCountrySelected()
	{
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		return selection_1.getSelectedEntity();
	}
	
	public Country[] getSelectedCountries()
	{
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		UISelection selection_2 = (UISelection) this.getComponent("selection2");
		Country[] c = {(Country) selection_1.getSelectedEntity(), (Country) selection_2.getSelectedEntity()};
		return c;
	}

	public void showAttackWindow() {
		UIGroup attackWindow = (UIGroup) this.getComponent("attackGroup");
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		ArrayList<UIElement> buttons = this.getComponents("Button");
		attackWindow.setVisible(true);
		attackWindow.setCounter((Country) selection_1.getSelectedEntity());
		
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.setVisible(false);
			}
	}
	
	public void hideAttackWindow(){
		ArrayList<UIElement> buttons = this.getComponents("Button");
		UIGroup attackWindow = (UIGroup) this.getComponent("attackGroup");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.setVisible(true);
			}
		attackWindow.setVisible(false);
	}

	public boolean getCountriesSelected() {
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		UISelection selection_2 = (UISelection) this.getComponent("selection2");
		return selection_1.hasEntitySelected() && selection_2.hasEntitySelected();
	}

	public boolean isAttackWindowVisible() {
		UIGroup attackWindow = (UIGroup) this.getComponent("attackGroup");
		return this.attackWindow.isVisible();
	}

	public void reset() {
		UISelection selection_1 = (UISelection) this.getComponent("selection1");
		UISelection selection_2 = (UISelection) this.getComponent("selection2");
		UIGroup attackWindow = (UIGroup) this.getComponent("attackGroup");
		selection_1.resetSelection();
		selection_2.resetSelection();
		attackWindow.setVisible(false);
	}

	public int getDiceCount() {
		UIGroup attackWindow = (UIGroup) this.getComponent("attackGroup");
		return attackWindow.getCounter();
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
	
	public boolean setVisibility(String entityID)
	{
		UIElement element = this.getComponent(entityID);
		if(element != null)
			return element.isVisible();
		return false;
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
	
}
