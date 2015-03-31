package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.factory.CountryFactory;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * @author Timo Bähr
 *
 * Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 * erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {

	private int stateID; 							// Identifier dieses BasicGameState
	private StateBasedEntityManager entityManager;
	private GameController gameController;// zugehoeriger entityManager
	private UserInterface userInterface;
	private Country selectedCountry_1, selectedCountry_2;
	private boolean reinforce = false;
	
    GameplayState( int sid) {
       stateID = sid;
       entityManager = StateBasedEntityManager.getInstance();
       userInterface = new UserInterface();
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	
    	// Hintergrund laden
    	Entity background = new Entity("background");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("assets/background.png"))); // Bildkomponente
    	    	
    	// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
    	entityManager.addEntity(stateID, background);
    	
    	// Bei Drücken der ESC-Taste zurueck ins Hauptmenue wechseln
    	Entity esc_Listener = new Entity("ESC_Listener");
    	KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
    	esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
    	esc_Listener.addComponent(esc_pressed);    	
    	entityManager.addEntity(stateID, esc_Listener);
    	gameController = new GameController(this);
    	gameController.init();
    	for(Entity e : gameController.getMap().getCountries())
    		entityManager.addEntity(stateID, e);
    	updateUserInterface();
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
    	entityManager.updateEntities(container, game, delta);
    	gameController.update();
    	userInterface.update(container, game, delta);
	}
    
    public void updateUserInterface() {
		userInterface.updateData(gameController.getTurnPlayer());
	}

	/**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		entityManager.renderEntities(container, game, g);
		userInterface.render(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}

	public Country getSelectedCountry() {
		// TODO Auto-generated method stub
		return selectedCountry_1;
	}

	public boolean isReinforceButtonPushed() {
		// TODO Auto-generated method stub
		return reinforce;
	}
	
	public void setReinforce(boolean b)
	{
		this.reinforce = b;
	}

	public int getReinforcement() {
		// TODO Auto-generated method stub
		return userInterface.getReinforcement();
	}

	public void disableReinforcement() {
		// TODO Auto-generated method stub
		
	}

	public boolean attackButtonPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	public Country[] getSelectedCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAttackDiceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getDefenseDiceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAttackDices(int[] rollDice) {
		// TODO Auto-generated method stub
		
	}

	public void setDefenseDices(int[] rollDice) {
		// TODO Auto-generated method stub
		
	}

	public Vector2f[] getAttackMoves() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean attackMoveSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	public void enableNextPhase() {
		// TODO Auto-generated method stub
		
	}

	public void disableNextPhase() {
		// TODO Auto-generated method stub
		
	}

	public boolean nextPhaseButtonPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNextPhaseButton(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public boolean endTurnButtonPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean fortifyButtonPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getFortifyUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void resetUI() {
		// TODO Auto-generated method stub
		
	}
		
}
