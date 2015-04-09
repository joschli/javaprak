package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class UILabel extends UIElement{

	public Color color;
	private String labelName;
	
	public UILabel(String entityID, String labelName, Color color, Vector2f position) {
		super(entityID);
		this.labelName = labelName;
		this.color = color;
		this.setPosition(position);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setColor(color);
		g.drawString(labelName, this.getPosition().x, this.getPosition().y);
	}
	
	public void setLabelName(String labelName)
	{
		this.labelName = labelName;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	


}
