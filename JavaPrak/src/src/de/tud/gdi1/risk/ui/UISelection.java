package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

public class UISelection extends UIElement {

	private Entity selectedEntity = null;
	private boolean oval = true;
	
	public UISelection(String entityID) {
		super(entityID);
		this.setVisible(true);
	}
	
	/**
	 * Selects the given Entity
	 * @param entity to be selected
	 */
	public void selectEntity(Entity entity)
	{
		this.selectedEntity = entity;
	}

	/**
	 * resets the current selection
	 */
	public void resetSelection()
	{
		this.selectedEntity = null;
	}
	
	/**
	 * returns if something is selected
	 * @return true if something is selected, else false
	 */
	public boolean hasEntitySelected()
	{
		return this.selectedEntity != null;
	}

	/**
	 * Draws a rectangle or oval around the selected entity if one is selected
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.selectedEntity != null && this.isVisible())
		{
			g.setColor(new Color(0,175,0,255));
			g.setLineWidth(3);
			if(oval)
				g.drawOval(selectedEntity.getPosition().x-(selectedEntity.getSize().x/2), selectedEntity.getPosition().y-(selectedEntity.getSize().x / 2), selectedEntity.getSize().x, selectedEntity.getSize().y);
			else
				g.drawRect(selectedEntity.getPosition().x-(selectedEntity.getSize().x/2), selectedEntity.getPosition().y-(selectedEntity.getSize().y / 2), selectedEntity.getSize().x, selectedEntity.getSize().y);
			g.setLineWidth(1);
		}
	}

	/**
	 * returns the selected Entity
	 * @return the selected Entity
	 */
	public Entity getSelectedEntity() {
		return this.selectedEntity;
	}
	
	/**
	 * sets if the selection should be a oval or a rectangle
	 * @param b if true the selection is oval, else a rectangle
	 */
	public void setOval(boolean b)
	{
		this.oval = b;
	}
}
