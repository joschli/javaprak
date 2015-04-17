package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.factory.ButtonFactory;
import src.de.tud.gdi1.risk.model.Options;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class WinState extends BasicGameState {

	private int stateID;// Identifier von diesem BasicGameState
	private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager
	private Options options;
	private final int distance = 100;
    private final int start_Position = 150;
    


	
	public WinState(int winState) {
		stateID = winState;
	    entityManager = StateBasedEntityManager.getInstance();
	    options = Options.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		UILabel Winner = new UILabel("winner", "Player 0 won !" , Color.red, new Vector2f(container.getWidth()/2, container.getHeight()/2));
		entityManager.addEntity(this.stateID, Winner);
    	
	}

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
    	UILabel winnerLabel = (UILabel) entityManager.getEntity(this.stateID, "winner");
    	winnerLabel.setLabelName(options.getWinner() + " won!");
		entityManager.updateEntities(container, game, delta);
	}
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, 
												Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}
	

}
