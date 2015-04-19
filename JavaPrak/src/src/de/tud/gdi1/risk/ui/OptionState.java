package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.actions.CancelAction;
import src.de.tud.gdi1.risk.actions.SelectAction;
import src.de.tud.gdi1.risk.actions.StartGameAction;
import src.de.tud.gdi1.risk.model.Options;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class OptionState extends SuperBasicGameState {
	
	private Options options;
	private boolean startGame = false;
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private Entity background;

	public OptionState(int optionsState) {
		super(optionsState);
		entityManager = StateBasedEntityManager.getInstance();
		options = Options.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Entity("background");
		background.setPosition(new Vector2f(0,0));
		background.addComponent(new ImageRenderComponent(new Image("assets/card_background.jpg")));
		background.setScale(2);
		// Events
		ANDEvent startGameEvent = new ANDEvent(new MouseClickedEvent(), new MouseEnteredEvent());
		startGameEvent.addAction(new StartGameAction());
		ANDEvent selectEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		selectEvent.addAction(new SelectAction());
		ANDEvent selectEvent2 = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		selectEvent2.addAction(new SelectAction());
		ANDEvent testEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		testEvent.addAction(new CancelAction());
		//Selection
		UISelection selection1 = new UISelection("selection1");
		selection1.setOval(false);
		// Menu
		UICounter playerCounter = new UICounter("playerCounter", new Vector2f(container.getWidth()/2+80, container.getHeight()/2-64), options.getMaxPlayerCount(), options.getMinPlayerCount());
		UILabel playerCountLabel = new UILabel("playerCountLabel", "Playercount: " , Color.red, new Vector2f(container.getWidth()/2-80, container.getHeight()/2-64));
		UIButton startGameButton = new UIButton("startGameButton", "Start Game", new Vector2f(container.getWidth()/2-80, container.getHeight()/2 +200), new Vector2f(128, 32), Color.gray, Color.black);
		UIButton option1Button = new UIButton("option1", "Missions", new Vector2f(container.getWidth()/2-80, container.getHeight()/2), new Vector2f(128,32), Color.gray, Color.black);
		UIButton option2Button = new UIButton("option2", "Domination", new Vector2f(container.getWidth()/2 + 80, container.getHeight()/2), new Vector2f(128,32), Color.gray, Color.black);
		UIButton testButton = new UIButton("testButton", "Cancel", new Vector2f(container.getWidth()/2+80, container.getHeight()/2+ 200), new Vector2f(128,32), Color.gray, Color.black);
		startGameButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		option1Button.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		option2Button.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		testButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		playerCounter.setDecreaseImageRendererComponent(new ImageRenderComponent(new Image("assets/counter_button.jpg")));
		playerCounter.setIncreaseImageRendererComponent(new ImageRenderComponent(new Image("assets/counter_button.jpg")));
		//Actions for Buttons
		startGameButton.addComponent(startGameEvent);
		option1Button.addComponent(selectEvent);
		option2Button.addComponent(selectEvent2);
		testButton.addComponent(testEvent);
		entityManager.addEntity(this.getID(), playerCountLabel);
		entityManager.addEntity(this.getID(), playerCounter);
		entityManager.addEntity(this.getID(), startGameButton);
		entityManager.addEntity(this.getID(), option1Button);
		entityManager.addEntity(this.getID(), option2Button);
		entityManager.addEntity(this.getID(), testButton);
		entityManager.addEntity(this.getID(), selection1);
		components.add(option1Button);
		components.add(option2Button);
		components.add(testButton);
		components.add(selection1);
		//components.add(backButton);
		


	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setBackground(new Color(0,0,0));
		g.clear();
		background.render(container, game, g);
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		for(UIElement element :components)
			element.update(container, game, delta);
		entityManager.updateEntities(container, game, delta);
		if(startGame){
			MainMenuState main = (MainMenuState) game.getState(Launch.MAINMENU_STATE);
			GameplayState state = (GameplayState) game.getState(Launch.GAMEPLAY_STATE);
			state.init(container, game);
			UISelection selection1 = (UISelection) entityManager.getEntity(this.getID(), "selection1");
			if(selection1.hasEntitySelected()){
				if(selection1.getSelectedEntity().getID() == "option1")
					options.setMissions(true);
				else
					options.setMissions(false);
			}
			else
				options.setMissions(true);
			main.setGameStarted(true);
			game.enterState(Launch.GAMEPLAY_STATE);
			startGame = false;
		}


	}

	/**
	 * starts a new game with the playerCount from the counter
	 */
	public void startNewGame() {
		UICounter counter = (UICounter) entityManager.getEntity(this.getID(), "playerCounter");
		this.options.setPlayerCount(counter.getCounter());
		this.startGame = true;
	}

	/**
	 * Selects a optionButton
	 */
	@Override
	public void selectAction(Entity entity) {
		UIButton button = (UIButton) entity;
		UISelection selection1 = (UISelection) this.entityManager.getEntity(this.getID(), "selection1");
		if(selection1.hasEntitySelected() && selection1.getSelectedEntity().getID() == entity.getID())
			selection1.resetSelection();
		else
			selection1.selectEntity(button);
	}

	/**
	 * Cancels the game creation and goes back to the main menu
	 */
	@Override
	public void cancelAction(StateBasedGame game) {
		game.enterState(Launch.MAINMENU_STATE);
	}

}
