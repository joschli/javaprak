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
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.actions.AttackAction;
import src.de.tud.gd1.risk.actions.CancelAction;
import src.de.tud.gd1.risk.actions.DiceAction;
import src.de.tud.gd1.risk.actions.EndTurnAction;
import src.de.tud.gd1.risk.actions.FortifyAction;
import src.de.tud.gd1.risk.actions.HideMissionAction;
import src.de.tud.gd1.risk.actions.NextPhaseAction;
import src.de.tud.gd1.risk.actions.ShowCardAction;
import src.de.tud.gd1.risk.actions.ShowMissionAction;
import src.de.tud.gd1.risk.actions.StartFortifyAction;
import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Card;
import src.de.tud.gdi1.risk.model.entities.Country;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;


public class GameplayState extends SuperBasicGameState {

	private GameController gameController; // state machine
	private UserInterface userInterface;
	private ArrayList<Country> countries; // list of all countries to draw them
	// ImageRenderComponents for the Dice
	private ArrayList<ImageRenderComponent[]> blueDiceImages = new ArrayList<ImageRenderComponent[]>();
	private ArrayList<ImageRenderComponent[]> redDiceImages = new ArrayList<ImageRenderComponent[]>();
	private boolean showCards = false;
	// Fields for showing a timed message on the screen
	private boolean showMessage = false;
	private int timer = 0;
	private int timerDelay = 2000;
	private boolean playerWon = false;
	//Background
	private Entity background;
    public GameplayState( int sid) {
       super(sid);
       
    }
    
  
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	userInterface = new UserInterface();
		this.background = new Entity("background");
		this.background.setPosition(new Vector2f(0,0));
		this.background.addComponent(new ImageRenderComponent(new Image("assets/card_background.jpg")));
		this.background.setScale(2.1f);
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
    	UIGroup headerGroup = new UIGroup("Header", new Vector2f(container.getWidth()/5+200, 30), new Vector2f(500, 32));
    	headerGroup.setRenderComponent(new ImageRenderComponent(new Image("assets/header.jpg")));
		UILabel playerName = new UILabel("playerNameLabel", null, null, new Vector2f(container.getWidth()/5,30));
		UILabel phaseName = new UILabel("phaseNameLabel", null, Color.red, new Vector2f(container.getWidth()/5+150,30));
		UILabel reinforcementCount = new UILabel("reinforcementCountLabel", null, null, new Vector2f(container.getWidth()/5+350, 30));
		// Buttons
		UIButton turnButton = new UIButton("turnButton", "End Turn", new Vector2f((int)5*buttonWidth+20, container.getHeight()-buttonHeight+buttonHeight/4), new Vector2f(128, 32), Color.gray, Color.black);
		UIButton attackButton = new UIButton("attackButton", "Attack!", new Vector2f((int)2*buttonWidth+5, container.getHeight()-buttonHeight+buttonHeight/4), new Vector2f(128, 32), Color.gray, Color.black);
		UIButton phaseButton = new UIButton("nextPhaseButton", "Next Phase", new Vector2f((int)4*buttonWidth+15, container.getHeight()-buttonHeight+buttonHeight/4), new Vector2f(128, 32), Color.gray, Color.black);
		UIButton fortifyButton = new UIButton("fortifyButton", "Fortify!", new Vector2f((int)3*buttonWidth+10, container.getHeight()-buttonHeight+buttonHeight/4), new Vector2f(128,32), Color.gray, Color.black);
		UIButton showMissionButton = new UIButton("showMissionButton", "Show Mission", new Vector2f((int)1 *buttonWidth, container.getHeight()-buttonHeight+buttonHeight/4), new Vector2f(128,32), Color.gray, Color.black);
		UIButton showCardButton = new UIButton("showCardButton", "Show Cards", new Vector2f((int)6* buttonWidth+25, container.getHeight() - buttonHeight+buttonHeight/4), new Vector2f(128, 32), Color.gray, Color.black);
		turnButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		attackButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		phaseButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		fortifyButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		showMissionButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		showCardButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		// Events
		ANDEvent turnEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		turnEvent.addAction(new EndTurnAction());
		turnButton.addComponent(turnEvent);
		ANDEvent attackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		attackEvent.addAction(new AttackAction());
		ANDEvent diceEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		diceEvent.addAction(new DiceAction());
		ANDEvent cancelAttackEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		cancelAttackEvent.addAction(new CancelAction());
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
		ANDEvent showCardEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		showCardEvent.addAction(new ShowCardAction());
		showCardButton.addComponent(showCardEvent);
		//
		attackButton.addComponent(attackEvent);
		UISelection selection_1 = new UISelection("selection1");
		UISelection selection_2 = new UISelection("selection2");
		selection_1.setVisible(true);
		selection_2.setVisible(true);
		// Window Overlay for ATTACK_PHASE & FORITFY_PHASE
		UIGroup commandWindow = new UIGroup("commandGroup", new Vector2f(container.getWidth()-80, 300), new Vector2f(145, 500));
		commandWindow.setRenderComponent(new ImageRenderComponent(new Image("assets/test_command.jpg")));
		UILabel aw_description = new UILabel("description", "Attack Window", Color.red, new Vector2f(commandWindow.getSize().x/2,15));
		UICounter aw_counter = new UICounter("counter", new Vector2f(commandWindow.getSize().x/2, 50), 3, 1);
		UIButton aw_diceButton = new UIButton("diceButton", "Roll!", new Vector2f(commandWindow.getSize().x/2,116), new Vector2f(128,32), Color.gray, Color.black);
		UIButton aw_cancelButton = new UIButton("cancelButton", "Cancel", new Vector2f(commandWindow.getSize().x/2, 164), new Vector2f(128, 32), Color.gray, Color.black);
		UIButton redButton1 = new UIButton("red1Button", "", new Vector2f(commandWindow.getSize().x/2-32, 228), new Vector2f(64,64), Color.gray, Color.black);
		UIButton redButton2 = new UIButton("red2Button", "", new Vector2f(commandWindow.getSize().x/2-32, 308), new Vector2f(64,64), Color.gray, Color.black);
		UIButton redButton3 = new UIButton("red3Button", "", new Vector2f(commandWindow.getSize().x/2-32, 388), new Vector2f(64,64), Color.gray, Color.black);
		UIButton blueButton1 = new UIButton("blue1Button", "", new Vector2f(commandWindow.getSize().x/2+32, 228), new Vector2f(64,64), Color.gray, Color.black);
		UIButton blueButton2 = new UIButton("blue2Button", "", new Vector2f(commandWindow.getSize().x/2+32, 308), new Vector2f(64,64), Color.gray, Color.black);
		UILabel firstAttack = new UILabel("firstAttackLabel", ">", Color.red, new Vector2f(commandWindow.getSize().x/2, 228));
		UILabel secondAttack = new UILabel("secondAttackLabel", ">", Color.red, new Vector2f(commandWindow.getSize().x/2, 308));
		aw_diceButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		aw_cancelButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		aw_counter.setIncreaseImageRendererComponent(new ImageRenderComponent(new Image("assets/counter_button.jpg")));
		aw_counter.setDecreaseImageRendererComponent(new ImageRenderComponent(new Image("assets/counter_button.jpg")));
		aw_description.setColor(Color.black);
		aw_counter.setColor(Color.black);
		redButton2.setScale(0.312f);
		redButton3.setScale(0.312f);
		blueButton1.setScale(0.312f);
		blueButton2.setScale(0.312f);
		redButton1.setScale(0.312f);
		commandWindow.setBorder(true, 3, Color.black);
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
		commandWindow.addComponent(firstAttack);
		commandWindow.addComponent(secondAttack);
		// MISSION WINDOW
		UIGroup missionWindow = new UIGroup("missionGroup", new Vector2f(container.getWidth() - 500, container.getWidth() - 750), new Vector2f(250, 250));
		UILabel mw_mission = new UILabel("mission", "", Color.white, new Vector2f(missionWindow.getSize().x/2, missionWindow.getSize().y/4));
		mw_mission.setSize(new Vector2f(200, 250));
		missionWindow.addComponent(mw_mission);
		missionWindow.setScale((float) 0.5);
		missionWindow.setRenderComponent(new ImageRenderComponent(new Image("assets/missionBackground.jpg")));
		missionWindow.setBorder(true, 3, Color.black);
		// Adding all Components to the userInterface
		userInterface.addComponent(headerGroup);
		userInterface.addComponenet(playerName);
		userInterface.addComponent(phaseName);
		userInterface.addComponent(reinforcementCount);
		userInterface.addComponent(turnButton);
		userInterface.addComponent(attackButton);
		userInterface.addComponent(phaseButton);
		userInterface.addComponent(fortifyButton);
		userInterface.addComponenet(showMissionButton);
		userInterface.addComponent(showCardButton);
		userInterface.addComponent(selection_1);
		userInterface.addComponent(selection_2);
		userInterface.addComponent(commandWindow);
		userInterface.addComponenet(missionWindow);
		//Setting the Visibility of some UIElements
		userInterface.setVisibility("commandGroup", false);
		UIGroup group = (UIGroup) userInterface.getComponent("commandGroup");
		group.setComponentVisiblity("red1Button", false);
		group.setComponentVisiblity("red2Button", false);
		group.setComponentVisiblity("red3Button", false);
		group.setComponentVisiblity("blue1Button", false);
		group.setComponentVisiblity("blue2Button", false);
		group.setComponentVisiblity("firstAttackLabel", false);
		group.setComponentVisiblity("secondAttackWindow", false);
		group.setComponentVisiblity("countryConqueredLabel", false);
		userInterface.setVisibility("missionGroup", false);
		
    	// Map Background
    	Entity background = new Entity("map");	
    	background.setPosition(new Vector2f(400+buttonWidth/2,300));	
    	background.addComponent(new ImageRenderComponent(new Image("assets/Blank Risk.PNG")));
    	entityManager.addEntity(this.getID(), background);
    	
    	// Pressing ESC for the MAINMENU
    	Entity esc_Listener = new Entity("ESC_Listener");
    	KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
    	esc_pressed.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
    	esc_Listener.addComponent(esc_pressed);    	
    	entityManager.addEntity(this.getID(), esc_Listener);
    	
    	// Creating the state machine && initializing it
    	try {
			gameController = new GameController(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	gameController.init();
    	countries = gameController.getMap().getCountries();
    	//rearanging all countries
    	for(Country country : countries)
    	{
    		country.setPosition(new Vector2f(country.getPosition().x + buttonWidth/2, country.getPosition().y));
    	}
    	updateUserInterface();
    }

 
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
    	if(showMessage){
    		timer += delta;
    		if(timer > this.timerDelay)
    		{
    			this.showMessage = false;
    			timer = 0;
    			userInterface.setVisibility("missionGroup", false);
    		}else
    			return;
    	}
    	entityManager.updateEntities(container, game, delta);
    	for(Country c: countries)
    		c.update(container, game, delta);
    	gameController.update();
    	userInterface.update(container, game, delta);
    	if(playerWon){
    		MainMenuState state = (MainMenuState) game.getState(Launch.MAINMENU_STATE);
    		state.setGameStarted(false);
    		game.enterState(Launch.MAINMENU_STATE);
    	}
    	if(gameController.getState() == 4){
    		this.playerWon(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()));
    		playerWon = true;
    		return;
    	}
    	else if(showCards)
    	{
    		CardState state = (CardState) game.getState(Launch.CARD_STATE);
    		state.setCards(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getCardList());
    		showCards = false;
    		game.enterState(Launch.CARD_STATE);
    	}
	}
    
    /**
     * If this method is called a timed message for the winning player is shown
     * @param player who won
     */
    private void playerWon(Player player) {
    	this.showMessage = true;
    	this.timerDelay = 5000;
		UIGroup missionWindow = (UIGroup) userInterface.getComponent("missionGroup");
		UILabel missionLabel = (UILabel) missionWindow.getComponent("mission");
		missionLabel.setLabelName(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getName() + "won!");
		userInterface.setVisibility("missionGroup", true);
	}

    /**
     * updates the information on the UserInterface 
     * and it enables/disables buttons as needed for the states.
     */
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
			userInterface.enableButton("showCardButton");
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
			userInterface.disableButton("showCardButton");
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
			userInterface.disableButton("showCardButton");
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
			userInterface.disableButton("showCardButton");
			break;
		}
		phaseNameLabel.setLabelName(labelName);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// Rendering the map and the background
		Entity e = entityManager.getEntity(this.getID(), "map");
		this.background.render(container, game, g);
		g.setLineWidth(1);
		entityManager.renderEntities(container, game, g);
		g.setColor(Color.black);
		g.setLineWidth(3);
		g.drawRect(e.getPosition().x -e.getSize().x/2, e.getPosition().y-e.getSize().y/2, e.getSize().x, e.getSize().y);
		g.setLineWidth(1);
		// Rendering all countries
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
		
		// Rendering that nice arrows
		if(this.getCountriesSelected())
		{
			Country[] countries = this.getSelectedCountries();
			float deltaX = (countries[1].getPosition().x - countries[0].getPosition().x);
			float deltaY = (countries[1].getPosition().y - countries[0].getPosition().y);
			Vector2f delta = new Vector2f(deltaX, deltaY);
			delta.normalise();

			g.setColor(new Color(255,0,0));
			g.setLineWidth(3);
			float size = 12;
			float c1x = countries[1].getPosition().x - delta.x * size;
			float c1y = countries[1].getPosition().y - delta.y * size;
			float c0x = countries[0].getPosition().x + delta.x * size;
			float c0y = countries[0].getPosition().y + delta.y * size;
			float arrowLength = 10;
			
			g.drawLine(c0x, c0y, c1x, c1y);
			//g.draw(shape);
			float ang = 30;
			g.rotate(c1x, c1y, ang);
			g.drawLine(c1x, c1y, c1x-delta.x*arrowLength, c1y-delta.y*arrowLength);
			g.resetTransform();
			g.rotate(c1x, c1y, -ang);
			g.drawLine(c1x, c1y, c1x-delta.x*arrowLength, c1y-delta.y*arrowLength);
			

		}
	}

	/**
	 * ends the current turn and resets the UI for the new player
	 */
	public void endTurnButtonPressed() {
		gameController.endTurn();
		this.reset();
	}

	/**
	 * shows the window for making an attack, but only if two valid countries are selected.
	 */
	public void AttackEvent() {
	
		if(getCountriesSelected())
		{
			showAttackWindow();
		}
		
	}

	/**
	 * shows the window for fortifying a country.
	 * @param minCount the minimal Amount of Troops the player can fortify
	 * @param maxCount the maximal Amount of Troops the player can fortify
	 */
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
	
	/**
	 * tells the state machine how many dices the attacker wants to use and
	 * on which countries
	 */
	public void rollDices() {
		gameController.rollDiceEvent(this.getDiceCount(), this.getSelectedCountries());
	}

	/**
	 * gets the rolled dices and shows them on the attackWindow.
	 * if the attacking Player conquered a country a timed message is shown.
	 * @param attackDices - rolled attack dices
	 * @param defenseDices - rolled defense dices
	 * @param countryConquered - true if the current player conquered the attacked country
	 */
	public void showDiceResult(int[] attackDices, int[] defenseDices, boolean countryConquered) {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UIButton redButton1 = (UIButton) attackWindow.getComponent("red1Button");
		UIButton redButton2 = (UIButton) attackWindow.getComponent("red2Button");
		UIButton redButton3 = (UIButton) attackWindow.getComponent("red3Button");
		UIButton blueButton1 = (UIButton) attackWindow.getComponent("blue1Button");
		UIButton blueButton2 = (UIButton) attackWindow.getComponent("blue2Button");
		UILabel firstAttackLabel = (UILabel) attackWindow.getComponent("firstAttackLabel");
		UILabel secondAttackLabel = (UILabel) attackWindow.getComponent("secondAttackLabel");
		redButton1.setRenderComponent(redDiceImages.get(attackDices[0]-1)[0]);
		redButton1.setVisible(true);
		if(attackDices.length > 1){
			redButton2.setRenderComponent(redDiceImages.get(attackDices[1]-1)[1]);
			redButton2.setVisible(true);
		}else
			redButton2.setVisible(false);
		if(attackDices.length > 2){
			redButton3.setRenderComponent(redDiceImages.get(attackDices[2]-1)[2]);
			redButton3.setVisible(true);
		}else
			redButton3.setVisible(false);
		blueButton1.setRenderComponent(blueDiceImages.get(defenseDices[0]-1)[0]);
		blueButton1.setVisible(true);
		if(defenseDices.length > 1)
		{
			blueButton2.setRenderComponent(blueDiceImages.get(defenseDices[1]-1)[1]);
			blueButton2.setVisible(true);
		}else
			blueButton2.setVisible(false);
		
		if(attackDices[0] > defenseDices[0])
			firstAttackLabel.setLabelName(">");
		else
			firstAttackLabel.setLabelName("<");
		firstAttackLabel.setVisible(true);
		if(attackDices.length > 1 && defenseDices.length > 1){
			if(attackDices[1] > defenseDices[1])
				secondAttackLabel.setLabelName(">");
			else
				secondAttackLabel.setLabelName("<");
			secondAttackLabel.setVisible(true);
		}
		
		if(countryConquered)
		{
			UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
			Country country = (Country) selection_2.getSelectedEntity();
			this.countryConquered(country);
		}
	}
	
	/**
	 * Shows a timed message for the player when he conquered a country
	 * @param country that was conquered
	 */
	private void countryConquered(Country country) {
		this.showMessage = true;
		this.timerDelay = 2000;
		UIGroup missionWindow = (UIGroup) userInterface.getComponent("missionGroup");
		UILabel missionLabel = (UILabel) missionWindow.getComponent("mission");
		missionLabel.setLabelName(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getName() + " conquered " + country.getName());
		userInterface.setVisibility("missionGroup", true);
	}

	/**
	 * updates the Counter in the attackWindow
	 */
	public void adjustCounter()
	{
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		//Set Counter to right value
		UIButton useButton = (UIButton) attackWindow.getComponent("diceButton");
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UICounter counter = (UICounter) attackWindow.getComponent("counter");
		Country country = (Country) selection_1.getSelectedEntity();
		if(country.getTroops() <= 1)
		{
			counter.setMaxCount(0);
			counter.setMinCount(0);
			useButton.disableButton();
		}
		else
		{
			counter.setMaxCount(country.getTroops()-1 > 3 ? 3 : country.getTroops()-1);
			counter.setMinCount(1);
		}
	}
	
	/**
	 * Updates the selected countries. If the give country is already selected the selection is removed and if it was the 
	 * first country selected all selections are removed. if it wasnt selected before it gets selected.
	 * @param country that should be selected
	 */
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
	
	/**
	 * Returns the first selected country
	 * @return country that got selected first
	 */
	public Entity getFirstCountrySelected()
	{
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		return selection_1.getSelectedEntity();
	}
	
	/**
	 * Returns all countries that got selected
	 * @return c - all countries that got selected
	 */
	public Country[] getSelectedCountries()
	{
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		Country[] c = {(Country) selection_1.getSelectedEntity(), (Country) selection_2.getSelectedEntity()};
		return c;
	}

	/**
	 * Shows the attackWindow when called, sets the current minimal and maximal dice count for 
	 * the counter in the window and disables all buttons in the UI that are not on the window.
	 */
	public void showAttackWindow() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UIButton useButton = (UIButton) attackWindow.getComponent("diceButton");
		UILabel label = (UILabel) attackWindow.getComponent("description");
		label.setLabelName("Attack");
		useButton.enableButton();
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
		attackWindow.setComponentVisiblity("firstAttackLabel", false);
		attackWindow.setComponentVisiblity("secondAttackLabel", false);
		attackWindow.setComponentVisiblity("countryConqueredLabel", false);
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				if(element.getID() != "showMissionButton")
					button.disableButton();
			}
	}
	

	/**
	 * Hides the attackWindow when called and enables all Buttons of the userInterface.
	 */
	public void hideAttackWindow(){
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				button.enableButton();
			}
		attackWindow.setComponentVisiblity("red1Button", false);
		attackWindow.setComponentVisiblity("red2Button", false);
		attackWindow.setComponentVisiblity("red3Button", false);
		attackWindow.setComponentVisiblity("blue1Button", false);
		attackWindow.setComponentVisiblity("blue2Button", false);
		attackWindow.setComponentVisiblity("firstAttackLabel", false);
		attackWindow.setComponentVisiblity("secondAttackLabel", false);
		attackWindow.setComponentVisiblity("countryConqueredLabel", false);
		attackWindow.setVisible(false);
	}

	/**
	 * Returns if two countries are selected.
	 * @return true if two countries are selected, else false
	 */
	public boolean getCountriesSelected() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		return selection_1.hasEntitySelected() && selection_2.hasEntitySelected();
	}

	/**
	 * Returns if the attackWindow is visible.
	 * @return true if the window is visible, else false
	 */
	public boolean isAttackWindowVisible() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		return attackWindow.isVisible();
	}

	/**
	 * Resets the selections and hides the attackWindow.
	 */
	public void reset() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		UISelection selection_2 = (UISelection) userInterface.getComponent("selection2");
		selection_1.resetSelection();
		selection_2.resetSelection();
		hideAttackWindow();
	}

	/**
	 * Returns the choosen attackdice count.
	 * @return the amount of dices the current player wants to attack with
	 */
	public int getDiceCount() {
		UIGroup attackWindow = (UIGroup) userInterface.getComponent("commandGroup");
		UICounter counter = (UICounter) attackWindow.getComponent("counter");
		return counter.getCounter();
	}
	
	/**
	 * Tells the state machine what countries the current player wants to fortify and how many troops 
	 * should be moved, it also hides the window and resets the UI.
	 */
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

	/**
	 * Tells the state machine that the current player wants to change the phase
	 */
	public void gotoNextPhase() {
		gameController.nextPhase();
	}

	/**
	 * Starts the fortifying process
	 */
	public void startFortify() {
		UISelection selection_1 = (UISelection) userInterface.getComponent("selection1");
		Country country = (Country) selection_1.getSelectedEntity();
		this.requestTroopMovement(1, country.getTroops()-1);
	}

	/**
	 * Shows the missionWindow with the mission of the current player and disables all other buttons on UI.
	 */
	public void showMissionText() {
		userInterface.setVisibility("missionGroup", true);
		UIButton missionButton = (UIButton) userInterface.getComponent("showMissionButton");
		missionButton.setLabelName("Hide Mission");
		ANDEvent event = (ANDEvent) missionButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new HideMissionAction());
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				if(element.getID() != "showMissionButton")
					button.disableButton();
			}
	}

	/**
	 * Hides the missionWindow and enables all other buttons on the UI
	 */
	public void hideMissionText() {
		userInterface.setVisibility("missionGroup", false);
		UIButton missionButton = (UIButton) userInterface.getComponent("showMissionButton");
		missionButton.setLabelName("Show Mission");
		ANDEvent event = (ANDEvent) missionButton.getEvent("ANDEvent");
		event.clearActions();
		event.addAction(new ShowMissionAction());
		ArrayList<UIElement> buttons = userInterface.getComponents("Button");
		for(UIElement element : buttons)
			if(element instanceof UIButton)
			{
				UIButton button = (UIButton) element;
				if(element.getID() != "showMissionButton")
					button.enableButton();
			}
	}
	
	/**
	 * Returns if the missionWindow is visible
	 * @return true if the missionWindow is visible, else false
	 */
	public boolean isMissionTextVisibible()
	{
		return userInterface.isComponenetVisible("missionGroup");
	}

	/**
	 * Activates the CardState
	 */
	public void showCards() {
		this.showCards = true;
	}

	/**
	 * Tells the state machine what cards the current player wants to trade
	 * @param tradeIn
	 */
	public void setTradeIn(Card[] tradeIn) {
		gameController.tradeInCards(tradeIn);
	}

	/**
	 * Shows a timed message if a player was defeated
	 * @param defendingPlayer
	 */
	public void playerDefeated(Player defendingPlayer) {
		this.showMessage = true;
		this.timerDelay = 2000;
		UIGroup missionWindow = (UIGroup) userInterface.getComponent("missionGroup");
		UILabel missionLabel = (UILabel) missionWindow.getComponent("mission");
		missionLabel.setLabelName(gameController.getMap().getPlayer(gameController.getCurrentPlayerIndex()).getMissionText());
		userInterface.setVisibility("missionGroup", true);
	}

	/**
	 * Selects a country if i can be selected in the current state
	 */
	@Override
	public void selectAction(Entity entity) {
		Country ownerEntity = (Country) entity;
		System.out.println("Country selected= " + ownerEntity.getName());
		System.out.println("Owner: " + ownerEntity.getOwner().getName());
		if(!userInterface.isComponenetVisible("missionGroup")){
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
					else if(this.getFirstCountrySelected() == null && ownerEntity.getTroops() > 1)
						this.updateSelection(ownerEntity);
			}
		}
		
	}

	/** 
	 * Cancels the attack/foritfyWindow
	 */
	@Override
	public void cancelAction(StateBasedGame game) {
		this.reset();
	}




}
