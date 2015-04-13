package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

public class UISelection extends UIElement {

	private Entity selectedEntity = null;
	
	public UISelection(String entityID) {
		super(entityID);
		this.setVisible(true);
	}
	
	public void selectEntity(Entity entity)
	{
		this.selectedEntity = entity;
	}

	public void resetSelection()
	{
		this.selectedEntity = null;
	}
	
	public boolean hasEntitySelected()
	{
		return this.selectedEntity != null;
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.selectedEntity != null && this.isVisible())
		{
			g.setColor(Color.green);
			g.setLineWidth(5);
			g.drawRect(selectedEntity.getPosition().x-(selectedEntity.getSize().x/2), selectedEntity.getPosition().y-(selectedEntity.getSize().x / 2), selectedEntity.getSize().x, selectedEntity.getSize().y);
		}
	}

	public Entity getSelectedEntity() {
		return this.selectedEntity;
	}
}
