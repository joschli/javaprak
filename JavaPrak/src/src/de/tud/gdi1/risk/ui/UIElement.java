package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.model.Player;
import eea.engine.entity.Entity;

public abstract class UIElement extends Entity {

	public UIElement(String entityID) {
		super(entityID);
	}
	
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g);
	
	

	
}
