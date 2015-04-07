package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class UISelection extends UIElement {

	public UISelection(String entityID) {
		super(entityID);
		this.setVisible(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible())
		{
			g.setColor(Color.red);
			g.drawRect(this.getPosition().x-(this.getSize().x/2), this.getPosition().y-(this.getSize().x / 2), this.getSize().x, this.getSize().y);
		}
	}
}
