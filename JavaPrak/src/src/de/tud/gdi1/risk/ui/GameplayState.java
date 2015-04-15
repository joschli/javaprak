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

import src.de.tud.gd1.risk.actions.AttackAction;
import src.de.tud.gd1.risk.actions.CancelAttackAction;
import src.de.tud.gd1.risk.actions.DiceAction;
import src.de.tud.gd1.risk.actions.EndTurnAction;
import src.de.tud.gd1.risk.actions.FortifyAction;
import src.de.tud.gd1.risk.actions.NextPhaseAction;
import src.de.tud.gd1.risk.actions.StartFortifyAction;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.GameMap;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;


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
    	userInterface = new UserInterface();
    	// Player Label
		UILabel playerName = new UILabel("playerNameLabel", null, null, new Vector2f(50,50));
		UILabel phaseName = new UILabel("phaseNameLabel", null, Color.red, new Vector2f(150,50));
		UILabel reinforcementCount = new UILabel("reinforcementCountLabel", null, null, new Vector2f(350, 50));
		// Buttons
		UIButton turnButton = new UIButton("turnButton", "End Turn", new Vector2f(64, container.getHeight()-32), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton attackButton = new UIButton("attackButton", "Attack!", new Vector2f(192, container.getHeight()-32), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton phaseButton = new UIButton("nextPhaseButton", "Next Phase", new Vector2f(320, container.getHeight()-32), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton fortifyButton = new UIButton("fortifyButton", "Fortify", new Vector2f(448, container.getHeight()-32), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		// Events
		ANDEvent turnEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		turnEvent.addAction(new EndTurnAction());
		turnButton.addComponent(turnEvent);
		ANDEvent attackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		attackEvent.addAction(new AttackAction());
		ANDEvent diceEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		diceEvent.addAction(new DiceAction());
		ANDEvent cancelAttackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		cancelAttackEvent.addAction(new CancelAttackAction());
		ANDEvent fortifyEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		fortifyEvent.addAction(new FortifyAction());
		ANDEvent nextPhaseEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		nextPhaseEvent.addAction(new NextPhaseAction());
		phaseButton.addComponent(nextPhaseEvent);
		ANDEvent startFortifyEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		startFortifyEvent.addAction(new StartFortifyAction());
		fortifyButton.addComponent(startFortifyEvent);
		//
		attackButton.addComponent(attackEvent);
		UISelection selection_1 = new UISelection("selection1");
		UISelection selection_2 = new UISelection("selection2");
		selection_1.setVisible(true);
		selection_2.setVisible(true);
		// Window Overlay for ATTACK_PHASE
		UIGroup attackWindow = new UIGroup("attackGroup", new Vector2f(container.getWidth()-100, container.getHeight()-150), new Vector2f(200, 300));
		UILabel aw_description = new UILabel("description", "Attack Window", Color.red, new Vector2f(50,15));
		UICounter aw_counter = new UICounter("counter", new Vector2f(50,50), 3, 1);
		UIButton aw_diceButton = new UIButton("diceButton", "Roll!", new Vector2f(114,116), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton aw_cancelButton = new UIButton("cancelButton", "Cancel", new Vector2f(114, 164), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		
		aw_diceButton.addComponent(diceEvent);
		aw_cancelButton.addComponent(cancelAttackEvent);
		attackWindow.addComponent(aw_cancelButton);
		attackWindow.addComponent(aw_counter);
		attackWindow.addComponent(aw_diceButton);
		attackWindow.addComponent(aw_description);
		// Window Overlay for FORTIFY_PHASE
		UIGroup fortifyWindow = new UIGroup("fortifyGroup", new Vector2f(container.getWidth()-100, container.getHeight()-450), new Vector2f(200, 300));
		UILabel fw_description = new UILabel("description", "Foritfy Window", Color.red, new Vector2f(50, 15));
		UICounter fw_counter = new UICounter("counter", new Vector2f(50,50), 3, 1);
		UIButton fw_fortifyButton = new UIButton("fortifyButton", "Fortify", new Vector2f(114, 116), new Vector2f(128, 32), new Vector2f(10, 10), Color.gray, Color.black);
		
		fw_fortifyButton.addComponent(fortifyEvent);
		fortifyWindow.addComponent(fw_fortifyButton);
		fortifyWindow.addComponent(fw_description);
		fortifyWindow.addComponent(fw_counter);
		
		userInterface.addComponenet(playerName);
		userInterface.addComponent(phaseName);
		userInterface.addComponent(reinforcementCount);
		userInterface.addComponent(turnButton);
		userInterface.addComponent(attackButton);
		userInterface.addComponent(phaseButton);
		userInterface.addComponent(fortifyButton);
		userInterface.addComponent(selection_1);
		userInterface.addComponent(selection_2);
		userInterface.addComponent(attackWindow);
		userInterface.addComponent(fortifyWindow);
		userInterface.setVisibility("attackGroup", false);
		userInterface.setVisibility("fortifyGroup", false);
		
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
    	updateUserInterface();
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
    	
    	if(gameController.getState() == 4)
    		new ChangeStateInitAction(Launch.GAMEPLAY_STATE).update(container, game, delta, null);
	}
    
    public void updateUserInterface() {
    	UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		ArrayList<UIElement> labels = userInterface.getComponents("Label");
		UILabel playerNameLabel = (UILabel) userInterface.getComponent("playerNameLabel");
		UILabel reinforcementCountLabel = (UILabel) userInterface.getComponent("reinforcementCountLabel");
		UILabel phaseNameLabel = (UILabel) userInterface.getComponent("phaseNameLabel");
		playerNameLabel.setLabelName(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getName());
		
		for(UIElement element : labels)
			if(element instanceof UILabel)
			{
				UILabel label = (UILabel) element;
				label.setColor(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getColor());
			}
		String labelName = "";
		switch(gameController.getState())
		{
		case 0:
			labelName = "REINFORCEMENT";
			reinforcementCountLabel.setVisible(true);
			reinforcementCountLabel.setLabelName("Reinforcements: " + gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getReinforcement());
			userInterface.disableButton("nextPhaseButton");
			userInterface.disableButton("attackButton");
			userInterface.disableButton("fortifyButton");
			userInterface.disableButton("turnButton");
			break;
		case 1:
			labelName = "ATTACKPHASE";
			if((!userInterface.isComponenetVisible("attackGroup") || !userInterface.isComponenetVisible("fortifyGroup")) && (selection_1.hasEntitySelected() && selection_2.hasEntitySelected())){
				userInterface.enableButton("attackButton");
				userInterface.enableButton("nextPhaseButton");
				userInterface.disableButton("fortifyButton");
				userInterface.enableButton("turnButton");
			}
			userInterface.disableButton("attackButton");
			reinforcementCountLabel.setVisible(false);
			userInterface.disableButton("nextPhaseButton");
			userInterface.disableButton("fortifyButton");
			userInterface.disableButton("turnButton");
			break;
		case 2:
			labelName = "FORTIFY";
			userInterface.disableButton("nextPhaseButton");
			userInterface.disableButton("attackButton");
			if(!userInterface.isComponenetVisible("fortifyGroup") || (selection_1.hasEntitySelected() && selection_2.hasEntitySelected())){
				userInterface.enableButton("fortifyButton");
				userInterface.enableButton("turnButton");
			}else 
				userInterface.disableButton("fortifyButton");
			userInterface.disableButton("turnButton");
			
			break;
		case 3:
			labelName = "STARTINGPHASE";
			reinforcementCountLabel.setVisible(true);
			reinforcementCountLabel.setLabelName("Reinforcements: " + gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getReinforcement());
			userInterface.disableButton("nextPhaseButton");
			userInterface.disableButton("attackButton");
			userInterface.disableButton("fortifyButton");
			userInterface.disableButton("turnButton");
			break;
		}
		phaseNameLabel.setLabelName(labelName);
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

	public boolean attackButtonPressed() {
		if(attackButtonPressed)
		{
			attackButtonPressed = false;
			return true;
		}
		return false;
	}

	public void endTurnButtonPressed() {
		gameController.endTurn();
		this.reset();
	}

	public void AttackEvent() {
		if(getCountriesSelected())
		{
			showAttackWindow();
		}
		
	}

	public void requestTroopMovement(int minCount, int maxCount) {
		UIGroup fortifyWindow = (UIGroup) userInterface.getComponent("fortifyGroup");
		UICounter counter = (UICounter) fortifyWindow.getComponent("counter");
		counter.setMaxCount(maxCount);
		counter.setMinCount(minCount);
		userInterface.setVisibility("fortifyGroup", true);
		userInterface.setVisibility("attackGroup", false);
		
	}

	public void selectCountry(Country ownerEntity) {
		System.out.println("Country selected= " + ownerEntity.getName());
		System.out.println("Owner: " + ownerEntity.getOwner().getName());
		
		if(ownerEntity != null && gameController.getState() == 1 && !userInterface.isComponenetVisible("attackGroup")){
			if((this.getFirstCountrySelected() == null || this.getFirstCountrySelected().getID() == ownerEntity.getID()) && ownerEntity.isOwner(gameController.getTurnPlayer()) && ownerEntity.getTroops() > 1)
			{
				this.updateSelection(ownerEntity);
			}
			else if(this.getFirstCountrySelected() != null && !ownerEntity.isOwner(gameController.getTurnPlayer()))
			{
				this.updateSelection(ownerEntity);
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
		else if(ownerEntity != null && gameController.getState() == 2 && !userInterface.isComponenetVisible("fortifyGroup"))
		{
			if(ownerEntity.getOwner().getName() == gameController.getTurnPlayer().getName())
				this.updateSelection(ownerEntity);
		}
		
	}

	public void cancelAttack() {
		this.reset();
	}

	public void rollDices() {
		gameController.rollDiceEvent(this.getDiceCount(), this.getSelectedCountries());
	}

	public void showDiceResult(int[] attackDices, int[] defenseDices) {
		// TODO show dices in attackGroup
	}
	
	public void updateSelection(Country country)
	{
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		if(selection_1.hasEntitySelected() && selection_1.getSelectedEntity().getID() == country.getID())
		{
			selection_1.resetSelection();
			selection_2.resetSelection();
		}
		else if(selection_2.hasEntitySelected() && selection_2.getSelectedEntity().getPosition().equals(country.getPosition()))
			selection_2.resetSelection();
		else if(selection_1.hasEntitySelected()){
			selection_2.selectEntity(country);
		}
		else{
			selection_1.selectEntity(country);
		}
		System.out.println("Selector1:" + selection_1.hasEntitySelected());
		System.out.println("Selector2:" + selection_2.hasEntitySelected());
	}
	
	public Entity getFirstCountrySelected()
	{
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		return selection_1.getSelectedEntity();
	}
	
	public Country[] getSelectedCountries()
	{
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		Country[] c = {(Country) selection_1.getSelectedEntity(), (Country) selection_2.getSelectedEntity()};
		return c;
	}

	public void showAttackWindow() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("attackGroup");
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		attackWindow.setVisible(true);
		attackWindow.setCounter((Country) selection_1.getSelectedEntity());
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.disableButton();
			}
	}
	

	
	public void hideAttackWindow(){
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("attackGroup");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.enableButton();
			}
		attackWindow.setVisible(false);
	}

	public boolean getCountriesSelected() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		return selection_1.hasEntitySelected() && selection_2.hasEntitySelected();
	}

	public boolean isAttackWindowVisible() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("attackGroup");
		return attackWindow.isVisible();
	}

	public void reset() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("attackGroup");
		selection_1.resetSelection();
		selection_2.resetSelection();
		attackWindow.setVisible(false);
	}

	public int getDiceCount() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("attackGroup");
		return attackWindow.getCounter();
	}

	public void fortifyCountry() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		Country[] countries = new Country[2];
		countries[0] = (Country) selection_1.getSelectedEntity();
		countries[1] = (Country) selection_2.getSelectedEntity();
		UIGroup fortifyWindow = (UIGroup) userInterface.getComponent("fortifyGroup");
		UICounter counter = (UICounter) fortifyWindow.getComponent("counter");
		gameController.troopsMovedEvent(counter.getCounter(), countries);
		userInterface.setVisibility("fortifyGroup", false);
		reset();
	}

	public void gotoNextPhase() {
		gameController.nextPhase();
	}

	public void startFortify() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		Country country = (Country) selection_1.getSelectedEntity();
		this.requestTroopMovement(1, country.getTroops()-1);
	}


}
