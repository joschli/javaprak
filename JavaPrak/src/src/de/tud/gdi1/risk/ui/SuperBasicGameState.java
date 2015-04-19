package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * Abstract Class for the GameStates that are using UISelections and CancelButtons
 * @author jonas_000
 *
 */
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
	
	/**
	 * Selection method that is called by the SelectAction
	 * @param entity that the player wants to select
	 */
	public abstract void selectAction(Entity entity);

	/**
	 * Cancel method that is called by the CancelAction
	 * @param game for changing states
	 */
	public abstract void cancelAction(StateBasedGame game);

}
