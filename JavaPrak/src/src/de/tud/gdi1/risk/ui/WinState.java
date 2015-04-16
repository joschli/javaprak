package src.de.tud.gdi1.risk.ui;

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
	
	private final int distance = 100;
    private final int start_Position = 150;
    


	
	public WinState(int winState) {
			stateID = winState;
	       entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
    	ButtonFactory buttonFactory;
    	// Hintergrund laden
    	Entity background = new Entity("menu");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("assets/background.jpg"))); // Bildkomponente
   
    	// Action von New Game Button
    	entityManager.addEntity(stateID, background);
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new ChangeStateAction(Launch.GAMEPLAY_STATE);
    	
    	// Action von Beenden Button
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    
    	// Neues Spiel Button
    	buttonFactory = new ButtonFactory("Neues Spiel", new_Game_Action, start_Position);
    	entityManager.addEntity(this.stateID, buttonFactory.createEntity());
    	
    	// Beenden Button
    	buttonFactory.updateFactory("Beenden", quit_Action, start_Position+distance);
    	entityManager.addEntity(this.stateID, buttonFactory.createEntity());
	}

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
	}
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, 
												Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
		
		int counter = 0;
		g.drawString("Neues Spiel", 245, start_Position+counter*distance);
		counter++;
		g.drawString("Beenden", 245, start_Position+counter*distance);
		counter++;
		g.drawString("WINNNER", 245, start_Position+counter*distance);
		counter++;
	}

	@Override
	public int getID() {
		return stateID;
	}
	

}
