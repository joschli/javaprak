package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

public class UILabel extends UIElement{

	public Color color;
	private String labelName;
	private Entity owner;
	
	public UILabel(String entityID, String labelName, Color color, Vector2f position) {
		super(entityID);
		this.labelName = labelName;
		this.color = color;
		this.setPosition(position);
		this.owner = null;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible()){
			if(owner != null)
			{
				if(g.getFont().getWidth(labelName) > owner.getSize().x)
				{
					String text = "";
					float letterWidth = g.getFont().getWidth("a");
					int letterPerLine = (int) ((int) owner.getSize().x / letterWidth);
					letterPerLine -= 10;
					for(int i = 0; i < labelName.length(); i++)
					{
						text += labelName.substring(i, i*letterPerLine);
						text = text + "/n";
					}
				}
			}
			g.setColor(color);
			g.drawString(labelName, this.getPosition().x, this.getPosition().y);
			
		}
	}
	
	public void setLabelName(String labelName)
	{
		this.labelName = labelName;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setOwner(Entity owner)
	{
		this.owner = owner;
	}



}
