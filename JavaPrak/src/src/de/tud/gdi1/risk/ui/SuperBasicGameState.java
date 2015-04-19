package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public abstract class SuperBasicGameState extends BasicGameState {

	private int stateID;
	protected StateBasedEntityManager entityManager;
	
	SuperBasicGameState(int stateID)
	{
		this.stateID = stateID;
		this.entityManager = StateBasedEntityManager.getInstance();
	}
	
	@Override
	public abstract void init(GameContainer container, StateBasedGame game)
			throws SlickException;
	@Override
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException; 

	@Override
	public abstract void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException; 

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public abstract void selectAction(Entity entity);

	public abstract void cancelAction(StateBasedGame game);

}
