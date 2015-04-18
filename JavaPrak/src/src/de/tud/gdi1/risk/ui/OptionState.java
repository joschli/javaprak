package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.actions.StartGameAction;
import src.de.tud.gdi1.risk.model.Options;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class OptionState extends BasicGameState {
	
	private int stateID;
	private StateBasedEntityManager entityManager; 
	private Options options;
	private boolean startGame = false;
	private ArrayList<UIElement> components = new ArrayList<UIElement>();
	private Entity background;

	public OptionState(int optionsState) {
		this.stateID = optionsState;
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
		ANDEvent backEvent = new ANDEvent(new MouseClickedEvent(), new MouseEnteredEvent());
		backEvent.addAction(new ChangeStateAction(Launch.MAINMENU_STATE));
		
		// Menu
		UICounter playerCounter = new UICounter("playerCounter", new Vector2f(container.getWidth()/2, container.getHeight()/2-64), options.getMaxPlayerCount(), options.getMinPlayerCount());
		UILabel playerCountLabel = new UILabel("playerCountLabel", "Playercount: " , Color.red, new Vector2f(container.getWidth()/3, container.getHeight()/2-64));
		UIButton startGameButton = new UIButton("startGameButton", "Start Game", new Vector2f(container.getWidth()/5, container.getHeight()/2-64), new Vector2f(128, 32), new Vector2f(10,10), Color.gray, Color.black);
		UIButton backButton = new UIButton("backButton", "Back", new Vector2f(container.getWidth()/5, container.getHeight()/2), new Vector2f(128,32), new Vector2f(10,10), Color.gray, Color.black);
		
		//Actions for Buttons
		startGameButton.addComponent(startGameEvent);
		backButton.addComponent(backEvent);

		entityManager.addEntity(this.stateID, playerCounter);
		entityManager.addEntity(this.stateID, startGameButton);
		entityManager.addEntity(this.stateID, backButton);
		
		entityManager.addEntity(this.stateID, playerCountLabel);


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
		entityManager.updateEntities(container, game, delta);
		if(startGame){
			MainMenuState main = (MainMenuState) game.getState(Launch.MAINMENU_STATE);
			GameplayState state = (GameplayState) game.getState(Launch.GAMEPLAY_STATE);
			state.init(container, game);
			main.setGameStarted();
			game.enterState(Launch.GAMEPLAY_STATE);
			startGame = false;
		}

	}

	@Override
	public int getID() {
		return stateID;
	}

	public void startNewGame() {
		UICounter counter = (UICounter) entityManager.getEntity(this.stateID, "playerCounter");
		this.options.setPlayerCount(counter.getCounter());
		this.startGame = true;
	}

}
