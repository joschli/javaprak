package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.GameMap;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;


public class GameplayState extends BasicGameState {

	private int stateID; 							// Identifier dieses BasicGameState
	private StateBasedEntityManager entityManager;
	private GameController gameController;// zugehoeriger entityManager
	private UserInterface userInterface;
	private Country selectedCountry_1, selectedCountry_2;
	private boolean reinforce = false;
	private boolean attackButtonPressed = false;
	private ArrayList<Country> countries;

    public GameplayState( int sid) {
       stateID = sid;
       entityManager = StateBasedEntityManager.getInstance();
       
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	userInterface = new UserInterface(container.getHeight(), container.getWidth());
    	// Hintergrund laden
    	Entity background = new Entity("background");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("assets/background.jpg"))); // Bildkomponente
    	    	
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
    	/*
    	for(Entity e : gameController.getMap().getCountries())
    		entityManager.addEntity(stateID, e);
    	*/
    	countries = gameController.getMap().getCountries();
    	updateUserInterface(gameController.getState(), gameController.getMap(), gameController.getCurrentPlayerIndex());
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
    	entityManager.updateEntities(container, game, delta);
    	for(Country c: countries)
    		c.update(container, game, delta);
    	gameController.update();
    	userInterface.update(container, game, delta);
	}
    
    public void updateUserInterface(int state, GameMap map, int currentPlayer) {
		userInterface.updateData(state, map, currentPlayer);
	}

	/**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		g.setLineWidth(1);
		entityManager.renderEntities(container, game, g);
		for(Country c: countries)
		{
			g.setColor(c.getColor());
			g.fillRect(c.getPosition().x-c.getSize().x/2, c.getPosition().y-c.getSize().y/2, c.getSize().x, c.getSize().y);
			
			g.setColor(c.getOwner().getColor());
			g.fillRect(c.getPosition().x-16, c.getPosition().y-16, 32, 32);
			g.setColor(Color.black);
			g.drawRect(c.getPosition().x-16, c.getPosition().y-16, 32, 32);
			g.drawString(new Integer(c.getTroops()).toString(), c.getPosition().x-5, c.getPosition().y-8);
		}
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
		if(attackButtonPressed)
		{
			attackButtonPressed = false;
			return true;
		}
		return false;
	}

	public Country[] getSelectedCountries() {
		return userInterface.getSelectedCountries();
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

	public void endTurnButtonPressed() {
		gameController.endTurn();
		this.resetUI();
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
		userInterface.reset();
		
	}

	public void AttackEvent() {
		if(userInterface.getCountriesSelected())
		{
			attackButtonPressed = true;
			userInterface.showAttackWindow();
		}
		
	}

	public void requestTroopMovement(int i, int j) {
		// TODO Auto-generated method stub
		userInterface.requestTroopMovement(i,j);
	}

	public boolean troopMovementSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getTroopMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Country getStartTroopCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean startTroopPlaced() {
		// TODO Auto-generated method stub
		return false;
	}

	public void selectCountry(Country ownerEntity) {
		//userInterface.updateSelection(ownerEntity);
		System.out.println("Country selected= " + ownerEntity.getName());
		System.out.println("Owner: " + ownerEntity.getOwner().getName());
		
		if(ownerEntity != null && gameController.getState() == 1 && !userInterface.isAttackWindowVisible()){
			if((userInterface.getFirstCountrySelected() == null || userInterface.getFirstCountrySelected().getID() == ownerEntity.getID()) && ownerEntity.isOwner(gameController.getTurnPlayer()) && ownerEntity.getTroops() > 1)
			{
				userInterface.updateSelection(ownerEntity);
			}
			else if(userInterface.getFirstCountrySelected() != null && !ownerEntity.isOwner(gameController.getTurnPlayer()))
			{
				userInterface.updateSelection(ownerEntity);
			}
		}
		else if(ownerEntity != null && gameController.getState() == 3)
		{
			if(ownerEntity.getOwner().getName() == gameController.getTurnPlayer().getName())
				gameController.setReinforceCountry(ownerEntity);
		}
		else if(ownerEntity != null && gameController.getState() == 0)
		{
			if(ownerEntity.getOwner().getName() == gameController.getTurnPlayer().getName())
				gameController.setReinforceCountry(ownerEntity);
		}
		
	}
	


}
