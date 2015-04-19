package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.actions.ResumeAction;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * @author Timo Bähr
 *
 * Diese Klasse repraesentiert das Menuefenster, indem ein neues
 * Spiel gestartet werden kann und das gesamte Spiel beendet 
 * werden kann.
 */
public class MainMenuState extends BasicGameState {

	private int stateID; 							// Identifier von diesem BasicGameState
	private StateBasedEntityManager entityManager;
	private Entity background;

	
    MainMenuState( int sid ) {
       stateID = sid;
       entityManager = StateBasedEntityManager.getInstance();
    }
    
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		background = new Entity("background");
		background.setPosition(new Vector2f(0,0));
		background.addComponent(new ImageRenderComponent(new Image("assets/card_background.jpg")));
		background.setScale(2);
    	// NewGameAction
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new ChangeStateAction(Launch.OPTIONS_STATE);
    	mainEvents.addAction(new_Game_Action);
    	// QuitAction
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    	mainEvents_q.addAction(quit_Action);
    	// Resume Action
    	ANDEvent resumeEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	resumeEvent.addAction(new ResumeAction());
    	
    	//Buttons
    	UIButton resumeButton = new UIButton("resumeButton", "Resume Game", new Vector2f(container.getWidth()/2, container.getHeight()/2-64), new Vector2f(128,32), Color.gray, Color.black);
    	UIButton newGameButton = new UIButton("newGameButton", "New Game", new Vector2f(container.getWidth()/2, container.getHeight()/2), new Vector2f(128, 32), Color.gray, Color.black);
    	UIButton exitGameButton = new UIButton("exitGameButton", "Exit Game", new Vector2f(container.getWidth()/2, container.getHeight()/2+64), new Vector2f(128, 32), Color.gray, Color.black);
    	resumeButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
    	newGameButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
    	exitGameButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
    	newGameButton.addComponent(mainEvents);
    	exitGameButton.addComponent(mainEvents_q);
    	resumeButton.addComponent(resumeEvent);
    	//adding the buttons to the entityManager
    	entityManager.addEntity(this.stateID, newGameButton);
    	entityManager.addEntity(this.stateID, exitGameButton);
    	entityManager.addEntity(this.stateID, resumeButton);
    	// Disabling resume
    	resumeButton.disableButton();
    }

 
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, 
												Graphics g) throws SlickException {
		g.setBackground(new Color(0,0,0));
		g.clear();
		background.render(container, game, g);
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}

	/**
	 * enables or disables the resume button
	 * @param b determines if the button should be enabled because there is a started game if b = true or if 
	 * the button should be disabled because no game is running (b = false)
	 */
	public void setGameStarted(boolean b) {
		UIButton button = (UIButton) entityManager.getEntity(this.stateID, "resumeButton");
		if(b)
			button.enableButton();
		else
			button.disableButton();
	}
	
}
