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
import src.de.tud.gd1.risk.actions.EndTurnAction;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;

public class UserInterface {
	
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private UILabel playerName, phaseName;
	private UIButton turnButton;
	private UISelection selection_1;
	private UISelection selection_2;
	private Entity firstCountrySelected = null;

	public UserInterface()
	{
		// Player Label
		playerName = new UILabel("PlayerName", null, null, new Vector2f(50,50));
		phaseName = new UILabel("PhaseName", null, null, new Vector2f(150,50));
		// End Turn Button
		turnButton = new UIButton("TurnButton", new Vector2f(200, 500), new Color(Color.black));
		ANDEvent turnEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		turnEvent.addAction(new EndTurnAction());
		turnButton.addComponent(turnEvent);
		
		selection_1 = new UISelection("Selection1");
		selection_2 = new UISelection("Selection2");
		selection_1.setVisible(true);
		selection_2.setVisible(true);
		
		components.add(playerName);
		components.add(phaseName);
		components.add(turnButton);
		components.add(selection_1);
		components.add(selection_2);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
	{
		for(UIElement element : components)
			element.render(container, game, g);
	}

	public void updateData(Player turnPlayer) {
		playerName.update(turnPlayer.getName(), turnPlayer.getColor());
	}
	
	public void updateTurnData(int state)
	{
		String labelName = "";
		switch(state)
		{
		case 0:
			labelName = "REINFORCEMENT";
			break;
		case 1:
			labelName = "ATTACKPHASE";
			break;
		case 2:
			labelName = "FORTIFY";
			break;
		case 3:
			labelName = "STARTINGPHASE";
			break;
		}
		phaseName.update(labelName, Color.red);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		turnButton.update(container, game, delta);
	}
	
	public ArrayList<UIElement> getElements()
	{
		return components;
	}
	
	public void updateSelection(Country country)
	{
		
		if(selection_1.hasEntitySelected() && selection_1.getSelectedEntity().getID() == country.getID())
		{
			selection_1.resetSelection();
			selection_2.resetSelection();
			firstCountrySelected = null;
		}
		else if(selection_2.hasEntitySelected() && selection_2.getSelectedEntity().getPosition().equals(country.getPosition()))
			selection_2.resetSelection();
		else if(selection_1.hasEntitySelected()){
			selection_2.selectEntity(country);
		}
		else{
			selection_1.selectEntity(country);
			firstCountrySelected = country;
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
		return firstCountrySelected;
	}
}
