package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class UILabel extends UIElement{

	private Color color;
	private String labelName;
	
	public UILabel(String entityID, String labelName, Color color, Vector2f position) {
		super(entityID);
		this.labelName = labelName;
		this.setPosition(position);
	}
	
	public void update(String labelName, Color color)
	{
		this.color = color;
		this.labelName = labelName;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(color);
		g.drawString(labelName, this.getPosition().x, this.getPosition().y);
	}

	


}
