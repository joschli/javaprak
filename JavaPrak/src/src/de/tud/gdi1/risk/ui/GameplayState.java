package src.de.tud.gdi1.risk.ui;

import java.io.IOException;
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
import src.de.tud.gd1.risk.actions.HideMissionAction;
import src.de.tud.gd1.risk.actions.NextPhaseAction;
import src.de.tud.gd1.risk.actions.ShowMissionAction;
import src.de.tud.gd1.risk.actions.StartFortifyAction;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.basicactions.ChangeStateAction;
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
	private boolean attackButtonPressed = false;
	private ArrayList<Country> countries;
	private ArrayList<ImageRenderComponent[]> blueDiceImages = new ArrayList<ImageRenderComponent[]>();
	private ArrayList<ImageRenderComponent[]> redDiceImages = new ArrayList<ImageRenderComponent[]>();

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
    	int buttonWidth = 128;
    	int buttonHeight = 32;
    	// Load Dice Images
    	for(int i = 1; i < 7; i++){
    		ImageRenderComponent[] b = {new ImageRenderComponent(new Image("assets/blau_" + i + ".png")), new ImageRenderComponent(new Image("assets/blau_" + i + ".png"))};
    		ImageRenderComponent[] r = {new ImageRenderComponent(new Image("assets/rot_" + i + ".png")), new ImageRenderComponent(new Image("assets/rot_" + i + ".png")), new ImageRenderComponent(new Image("assets/rot_" + i + ".png"))};
    		redDiceImages.add(r);
    		blueDiceImages.add(b);
    	}
    	// Player Label
		UILabel playerName = new UILabel("playerNameLabel", null, null, new Vector2f(40,30));
		UILabel phaseName = new UILabel("phaseNameLabel", null, Color.red, new Vector2f(150,30));
		UILabel reinforcementCount = new UILabel("reinforcementCountLabel", null, null, new Vector2f(350, 30));
		// Buttons
		UIButton turnButton = new UIButton("turnButton", "End Turn", new Vector2f((int)5*buttonWidth, container.getHeight()-buttonHeight/2), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton attackButton = new UIButton("attackButton", "Attack!", new Vector2f((int)2*buttonWidth, container.getHeight()-buttonHeight/2), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton phaseButton = new UIButton("nextPhaseButton", "Next Phase", new Vector2f((int)4*buttonWidth, container.getHeight()-buttonHeight/2), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton fortifyButton = new UIButton("fortifyButton", "Fortify!", new Vector2f((int)3*buttonWidth, container.getHeight()-buttonHeight/2), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton showMissionButton = new UIButton("showMissionButton", "Show Mission", new Vector2f((int)1 *buttonWidth, container.getHeight()-buttonHeight/2), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
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
		ANDEvent showMissionEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		showMissionEvent.addAction(new ShowMissionAction());
		showMissionButton.addComponent(showMissionEvent);
		//
		attackButton.addComponent(attackEvent);
		UISelection selection_1 = new UISelection("selection1");
		UISelection selection_2 = new UISelection("selection2");
		selection_1.setVisible(true);
		selection_2.setVisible(true);
		// Window Overlay for ATTACK_PHASE & FORITFY_PHASE
		UIGroup commandWindow = new UIGroup("commandGroup", new Vector2f(container.getWidth()-100, container.getHeight()-300), new Vector2f(200, 600));
		//UIGroup attackWindow = new UIGroup("commandGroup", new Vector2f(container.getWidth()-100, container.getHeight()-150), new Vector2f(200, 300));
		UILabel aw_description = new UILabel("description", "Attack Window", Color.red, new Vector2f(commandWindow.getSize().x/2,15));
		UICounter aw_counter = new UICounter("counter", new Vector2f(commandWindow.getSize().x/2, 50), 3, 1);
		UIButton aw_diceButton = new UIButton("diceButton", "Roll!", new Vector2f(commandWindow.getSize().x/2,116), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton aw_cancelButton = new UIButton("cancelButton", "Cancel", new Vector2f(commandWindow.getSize().x/2, 164), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton redButton1 = new UIButton("red1Button", "", new Vector2f(48, 228), new Vector2f(64,64), new Vector2f(0,0), Color.gray, Color.black);
		UIButton redButton2 = new UIButton("red2Button", "", new Vector2f(48, 308), new Vector2f(64,64), new Vector2f(0,0), Color.gray, Color.black);
		UIButton redButton3 = new UIButton("red3Button", "", new Vector2f(48, 388), new Vector2f(64,64), new Vector2f(0,0), Color.gray, Color.black);
		UIButton blueButton1 = new UIButton("blue1Button", "", new Vector2f(152, 228), new Vector2f(64,64), new Vector2f(0,0), Color.gray, Color.black);
		UIButton blueButton2 = new UIButton("blue2Button", "", new Vector2f(152, 308), new Vector2f(64,64), new Vector2f(0,0), Color.gray, Color.black);
		redButton1.setVisible(false);
		redButton2.setVisible(false);
		redButton3.setVisible(false);
		blueButton1.setVisible(false);
		blueButton2.setVisible(false);
		aw_diceButton.addComponent(diceEvent);
		aw_cancelButton.addComponent(cancelAttackEvent);
		commandWindow.addComponent(aw_cancelButton);
		commandWindow.addComponent(aw_counter);
		commandWindow.addComponent(aw_diceButton);
		commandWindow.addComponent(aw_description);
		commandWindow.addComponent(redButton1);
		commandWindow.addComponent(redButton2);
		commandWindow.addComponent(redButton3);
		commandWindow.addComponent(blueButton1);
		commandWindow.addComponent(blueButton2);
		// MISSION WINDOW
		UIGroup missionWindow = new UIGroup("missionGroup", new Vector2f(container.getWidth() - 500, container.getWidth() - 750), new Vector2f(250, 250));
		UILabel mw_mission = new UILabel("mission", "", Color.white, new Vector2f(28, 32));
		mw_mission.setSize(new Vector2f(200, 250));
		missionWindow.addComponent(mw_mission);
		missionWindow.setScale((float) 0.5);
		missionWindow.setRenderComponent(new ImageRenderComponent(new Image("assets/missionBackground.jpg")));
		userInterface.addComponenet(playerName);
		userInterface.addComponent(phaseName);
		userInterface.addComponent(reinforcementCount);
		userInterface.addComponent(turnButton);
		userInterface.addComponent(attackButton);
		userInterface.addComponent(phaseButton);
		userInterface.addComponent(fortifyButton);
		userInterface.addComponenet(showMissionButton);
		userInterface.addComponent(selection_1);
		userInterface.addComponent(selection_2);
		userInterface.addComponent(commandWindow);
		userInterface.addComponenet(missionWindow);
		userInterface.setVisibility("commandGroup", false);
		UIGroup group = (UIGroup) userInterface.getComponent("commandGroup");
		group.setComponentVisiblity("red1Button", false);
		group.setComponentVisiblity("red2Button", false);
		group.setComponentVisiblity("red3Button", false);
		group.setComponentVisiblity("blue1Button", false);
		group.setComponentVisiblity("blue2Button", false);
		userInterface.setVisibility("missionGroup", false);
		
    	// Hintergrund laden
    	Entity background = new Entity("background");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400+buttonWidth/2,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("assets/Blank Risk.PNG"))); // Bildkomponente
    	    	
    	// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
    	entityManager.addEntity(stateID, background);
    	
    	// Bei DrÃ¼cken der ESC-Taste zurueck ins Hauptmenue wechseln
    	Entity esc_Listener = new Entity("ESC_Listener");
    	KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
    	esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
    	esc_Listener.addComponent(esc_pressed);    	
    	entityManager.addEntity(stateID, esc_Listener);
    	try {
			gameController = new GameController(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	gameController.init();
    	/*
    	for(Entity e : gameController.getMap().getCountries())
    		entityManager.addEntity(stateID, e);
    	*/
    	countries = gameController.getMap().getCountries();
    	for(Country country : countries)
    	{
    		country.setPosition(new Vector2f(country.getPosition().x + buttonWidth/2, country.getPosition().y));
    	}
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
    	
    	if(gameController.getState() == 4){
    		game.enterState(Launch.WIN_STATE);
    	}
		
	}
    
    public void updateUserInterface() {
    	UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		ArrayList<UIElement> labels = userInterface.getComponents("Label");
		UIGroup missionWindow = (UIGroup) userInterface.getComponent("missionGroup");
		UILabel playerNameLabel = (UILabel) userInterface.getComponent("playerNameLabel");
		UILabel reinforcementCountLabel = (UILabel) userInterface.getComponent("reinforcementCountLabel");
		UILabel phaseNameLabel = (UILabel) userInterface.getComponent("phaseNameLabel");
		UILabel missionLabel = (UILabel) missionWindow.getComponent("mission");
		missionLabel.setLabelName(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getMissionText());
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
			reinforcementCountLabel.setVisible(false);
			if((!userInterface.isComponenetVisible("commandGroup")) && (selection_1.hasEntitySelected() && selection_2.hasEntitySelected())){
				userInterface.enableButton("attackButton");
				userInterface.enableButton("nextPhaseButton");
				userInterface.disableButton("fortifyButton");
				userInterface.enableButton("turnButton");
				break;
			}
			if(userInterface.isComponenetVisible("commandGroup"))
			{
				userInterface.disableButton("attackButton");
				userInterface.disableButton("fortifyButton");
				userInterface.disableButton("turnButton");
				userInterface.disableButton("nextPhaseButton");
				break;
			}
			
			userInterface.disableButton("attackButton");
			userInterface.disableButton("fortifyButton");
			userInterface.enableButton("turnButton");
			userInterface.enableButton("nextPhaseButton");
			
			break;
		case 2:
			labelName = "FORTIFY";
			userInterface.disableButton("nextPhaseButton");
			userInterface.disableButton("attackButton");
			userInterface.disableButton("turnButton");
			userInterface.disableButton("fortifyButton");
			
			if(!userInterface.isComponenetVisible("commandGroup") && (selection_1.hasEntitySelected() && selection_2.hasEntitySelected())){
				userInterface.enableButton("fortifyButton");
				userInterface.enableButton("turnButton");			
			}else if (!userInterface.isComponenetVisible("commandGroup"))
			{
				userInterface.enableButton("turnButton");
				userInterface.disableButton("fortifyButton");
			}
				
			
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
			g.fillOval(c.getPosition().x-12, c.getPosition().y-12, 24, 24);
			//g.fillRect(c.getPosition().x-16, c.getPosition().y-16, 32, 32);
			g.setColor(Color.black);
			g.drawOval(c.getPosition().x-12, c.getPosition().y-12, 24, 24);
			//g.drawRect(c.getPosition().x-16, c.getPosition().y-16, 32, 32);
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
			System.out.println("Neighbor: " +this.getSelectedCountries()[0].isNeighbor(this.getSelectedCountries()[1]));
			showAttackWindow();
		}
		
	}

	public void requestTroopMovement(int minCount, int maxCount) {
		UIGroup fortifyWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UICounter counter = (UICounter) fortifyWindow.getComponent("counter");
		UILabel label = (UILabel) fortifyWindow.getComponent("description");
		label.setLabelName("Fortify");
		counter.setMaxCount(maxCount);
		counter.setMinCount(minCount);
		UIButton useButton = (UIButton) fortifyWindow.getComponent("diceButton");
		useButton.setLabelName("Fortify!");
		ANDEvent event = (ANDEvent) useButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new FortifyAction());
		userInterface.setVisibility("commandGroup", true);
		
	}

	public void selectCountry(Country ownerEntity) {
		System.out.println("Country selected= " + ownerEntity.getName());
		System.out.println("Owner: " + ownerEntity.getOwner().getName());
		if(ownerEntity != null && gameController.getState() == 1 && !userInterface.isComponenetVisible("commandGroup")){
			if((this.getFirstCountrySelected() == null || this.getFirstCountrySelected().getID() == ownerEntity.getID())
				&& ownerEntity.isOwner(gameController.getTurnPlayer()) 
				&& ownerEntity.getTroops() > 1)
			{
				this.updateSelection(ownerEntity);
			}
			else if(this.getFirstCountrySelected() != null
					&& !ownerEntity.isOwner(gameController.getTurnPlayer()) 
					&& ownerEntity.isNeighbor((Country) this.getFirstCountrySelected()))
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
		else if(ownerEntity != null && gameController.getState() == 2 && !userInterface.isComponenetVisible("commandGroup"))
		{
			if(ownerEntity.getOwner().getName() == gameController.getTurnPlayer().getName())
				if(this.getFirstCountrySelected() != null &&
					(ownerEntity.isNeighbor((Country) this.getFirstCountrySelected())
							|| ownerEntity.getID() == this.getFirstCountrySelected().getID() ))
					this.updateSelection(ownerEntity);
				else if(this.getFirstCountrySelected() == null)
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
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UIButton redButton1 = (UIButton) attackWindow.getComponent("red1Button");
		UIButton redButton2 = (UIButton) attackWindow.getComponent("red2Button");
		UIButton redButton3 = (UIButton) attackWindow.getComponent("red3Button");
		UIButton blueButton1 = (UIButton) attackWindow.getComponent("blue1Button");
		UIButton blueButton2 = (UIButton) attackWindow.getComponent("blue2Button");
		redButton1.setRenderComponent(redDiceImages.get(attackDices[0]-1)[0]);
		redButton1.setVisible(true);
		if(attackDices.length > 1){
			redButton2.setRenderComponent(redDiceImages.get(attackDices[1]-1)[1]);
			redButton2.setVisible(true);
		}
		if(attackDices.length > 2){
			redButton3.setRenderComponent(redDiceImages.get(attackDices[2]-1)[2]);
			redButton3.setVisible(true);
		}
		blueButton1.setRenderComponent(blueDiceImages.get(defenseDices[0]-1)[0]);
		blueButton1.setVisible(true);
		if(defenseDices.length > 1)
		{
			blueButton2.setRenderComponent(blueDiceImages.get(defenseDices[1]-1)[1]);
			blueButton2.setVisible(true);
		}
		
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
		System.out.println("HideAttackWindow");
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UIButton useButton = (UIButton) attackWindow.getComponent("diceButton");
		UILabel label = (UILabel) attackWindow.getComponent("description");
		label.setLabelName("Attack");
		useButton.setLabelName("Attack!");
		ANDEvent event = (ANDEvent) useButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new DiceAction());
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		attackWindow.setVisible(true);
		UICounter counter = (UICounter) attackWindow.getComponent("counter");
		Country country = (Country) selection_1.getSelectedEntity();
		counter.setMaxCount(country.getTroops()-1 > 3 ? 3 : country.getTroops()-1);
		counter.setMinCount(1);
		attackWindow.setComponentVisiblity("red1Button", false);
		attackWindow.setComponentVisiblity("red2Button", false);
		attackWindow.setComponentVisiblity("red3Button", false);
		attackWindow.setComponentVisiblity("blue1Button", false);
		attackWindow.setComponentVisiblity("blue2Button", false);
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.disableButton();
			}
	}
	

	
	public void hideAttackWindow(){
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		System.out.println("HideAttackWindow");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				System.out.println(button.getID());
				button.enableButton();
			}
		attackWindow.setComponentVisiblity("red1Button", false);
		attackWindow.setComponentVisiblity("red2Button", false);
		attackWindow.setComponentVisiblity("red3Button", false);
		attackWindow.setComponentVisiblity("blue1Button", false);
		attackWindow.setComponentVisiblity("blue2Button", false);
		attackWindow.setVisible(false);
	}

	public boolean getCountriesSelected() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		return selection_1.hasEntitySelected() && selection_2.hasEntitySelected();
	}

	public boolean isAttackWindowVisible() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		return attackWindow.isVisible();
	}

	public void reset() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		//UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		selection_1.resetSelection();
		selection_2.resetSelection();
		hideAttackWindow();
		//Nicht mehr nötig da in hide attack window eh drinnen? 
		/*attackWindow.setVisible(false);
		attackWindow.setComponentVisiblity("red1Button", false);
		attackWindow.setComponentVisiblity("red2Button", false);
		attackWindow.setComponentVisiblity("red3Button", false);
		attackWindow.setComponentVisiblity("blue1Button", false);
		attackWindow.setComponentVisiblity("blue2Button", false);*/
	}

	public int getDiceCount() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UICounter counter = (UICounter) attackWindow.getComponent("counter");
		return counter.getCounter();
	}

	public void fortifyCountry() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		Country[] countries = new Country[2];
		countries[0] = (Country) selection_1.getSelectedEntity();
		countries[1] = (Country) selection_2.getSelectedEntity();
		UIGroup fortifyWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UICounter counter = (UICounter) fortifyWindow.getComponent("counter");
		gameController.troopsMovedEvent(counter.getCounter(), countries);
		userInterface.setVisibility("commandGroup", false);
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

	public void showMissionText() {
		userInterface.setVisibility("missionGroup", true);
		UIButton missionButton = (UIButton) userInterface.getComponent("showMissionButton");
		missionButton.setLabelName("Hide Mission");
		ANDEvent event = (ANDEvent) missionButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new HideMissionAction());
	}

	public void hideMissionText() {
		userInterface.setVisibility("missionGroup", false);
		UIButton missionButton = (UIButton) userInterface.getComponent("showMissionButton");
		missionButton.setLabelName("Show Mission");
		ANDEvent event = (ANDEvent) missionButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new ShowMissionAction());
	}
	
	public boolean isMissionTextVisibible()
	{
		return userInterface.isComponenetVisible("missionGroup");
	}


}
