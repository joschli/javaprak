package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import src.de.tud.gd1.risk.actions.CancelAction;
import src.de.tud.gd1.risk.actions.SelectAction;
import src.de.tud.gd1.risk.actions.TradeInCardsAction;
import src.de.tud.gdi1.risk.model.entities.Card;

public class CardState extends SuperBasicGameState {

	private ArrayList<Card> cards = new ArrayList<Card>(); // CardList of the current player
	private float height = 0;
	private float width = 0;
	private ArrayList<UISelection> selections = new ArrayList<UISelection>(); 
	private Card[] result; // Cards the player has selected to trade in
	private Entity background;
	
	public CardState(int stateID)
	{
		super(stateID);
		this.entityManager = StateBasedEntityManager.getInstance();
	}
	
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.height = container.getHeight();
		this.width = container.getWidth();
		this.result = new Card[3];
		background = new Entity("background");
		background.setPosition(new Vector2f(0,0));
		background.addComponent(new ImageRenderComponent(new Image("assets/card_background.jpg")));
		background.setScale(2);
		//Events
		ANDEvent cancelEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		cancelEvent.addAction(new CancelAction());
		ANDEvent tradeEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		tradeEvent.addAction(new TradeInCardsAction());
		//Buttons
		UIButton cancelButton = new UIButton("cancelButton", "Cancel", new Vector2f(container.getWidth()/5, container.getHeight()/2), new Vector2f(128,32), Color.gray, Color.black);
		UIButton tradeButton = new UIButton("tradeButton", "Trade In", new Vector2f(container.getWidth()/5, container.getHeight()/2-48), new Vector2f(128, 32), Color.gray, Color.black);
		cancelButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		tradeButton.setRenderComponent(new ImageRenderComponent(new Image("assets/button_texture2.jpg")));
		cancelButton.addComponent(cancelEvent);
		tradeButton.addComponent(tradeEvent);
		tradeButton.disableButton();
		
		//Selections
		UISelection selection1 = new UISelection("selection1");
		UISelection selection2 = new UISelection("selection2");
		UISelection selection3 = new UISelection("selection3");
		selection1.setOval(false);
		selection2.setOval(false);
		selection3.setOval(false);
		selections.add(selection1);
		selections.add(selection2);
		selections.add(selection3);
		entityManager.addEntity(this.getID(), cancelButton);
		entityManager.addEntity(this.getID(), tradeButton);
		entityManager.addEntity(this.getID(), selection1);
		entityManager.addEntity(this.getID(), selection2);
		entityManager.addEntity(this.getID(), selection3);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.render(container, game, g);
		for(Card c : cards){
			c.render(container, game, g);
		}
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
		for(Card c : cards)
			c.update(container, game, delta);
	}
	
	
	/**
	 * sets the cardset of the current Player, it prepares the cards to be shown to the player
	 * @param cards of the current player
	 * @throws SlickException
	 */
	public void setCards(ArrayList<Card> cards) throws SlickException
	{
		
		reset();
		int index = 0;
		int line = 0;
		for(int i = 0; i < cards.size(); ++i)
		{
			if(i == 5 || i == 10){
				index -= 5;
				line++;
			}
			float cardWidth = width/3+(i+index)*112;
			float cardHeight = height/5+line*161;
			this.setUpCard(cards.get(i), cardWidth, cardHeight);
		}
		UIButton button = (UIButton) entityManager.getEntity(this.getID(), "cancelButton");
		if(this.cards.size() > 5)
			button.disableButton();
		else
			button.enableButton();
	}
	
	/**
	 * resets the CardState. It clears the cards, the selection and alle cardNames
	 */
	private void reset() {
		this.cards.clear();
		List<Entity> entities = entityManager.getEntitiesByState(this.getID());
		for(Entity e : entities)
			if(e.getID() == "cardLabel")
				entityManager.removeEntity(this.getID(), e);
			else if(e.getID() == "tradeButton"){
				UIButton button = (UIButton) e;
				button.disableButton();
			}
		for(UISelection s : selections)
			s.resetSelection();
		this.result = new Card[3];
	}

	/**
	 * gives the card the right ImageRendererComponent, a SelectAction and it gets added to the cardlist
	 * @param c
	 * @param cardWidth
	 * @param cardHeight
	 * @throws SlickException
	 */

	private void setUpCard(Card c, float cardWidth, float cardHeight) throws SlickException
	{
		ANDEvent event = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		event.addAction(new SelectAction());
		c.setPosition(new Vector2f(cardWidth, cardHeight));
		ImageRenderComponent image = null;
		if(c.getValue() == Card.ARTILLERY)
			image = new ImageRenderComponent(new Image("assets/card_canon.png"));
		else if(c.getValue() == Card.INFANTRY)
			image = new ImageRenderComponent(new Image("assets/card_infantry.png"));
		else
			image = new ImageRenderComponent(new Image("assets/card_cavalry.png"));
		c.setScale(1);
		c.setSize(new Vector2f(95, 145));
		c.addComponent(image);
		c.addComponent(event);
		UILabel label = new UILabel("cardLabel", c.getCountry().getName(), Color.red, new Vector2f(c.getPosition().x, c.getPosition().y-30));
		label.setSize(new Vector2f(95, 70));
		entityManager.addEntity(this.getID(), label);
		this.cards.add(c);
	}

	/**
	 * returns the cards that the player would like to trade in.
	 * @return 3 cards that the player wants to trade
	 */
	public Card[] getTradeIn() {
		return result;
	}

	/**
	 * checks if the player has selected 3 cards that he can trade in.
	 * If he has selected 3 cards the cards will be saved in the result cardlist.
	 */
	private void checkForTradeIn() {
		int count = 0;
		for(int i = 0; i < selections.size(); ++i)
		{
			if(selections.get(i).hasEntitySelected())
				count++;
		}
		UIButton button = (UIButton) entityManager.getEntity(this.getID(), "tradeButton");
		if(count == selections.size()){
			result[0] = (Card) selections.get(0).getSelectedEntity();
			result[1] = (Card) selections.get(1).getSelectedEntity();
			result[2] = (Card) selections.get(2).getSelectedEntity();
			button.enableButton();
		}else
			button.disableButton();
	}



	/**
	 * Implementation of the abstract SelectAction
	 * This Method is called when a selectable Entity in this state is pressed.
	 * It determines if the given entity should be selected or not.
	 */
	@Override
	public void selectAction(Entity entity) {
		Card ownerEntity = (Card) entity;
		int infantryCount = 0;
		int cavlaryCount = 0;
		int artilleryCount = 0;
		for(int i = 0; i < selections.size(); ++i)
		{
			if(selections.get(i).hasEntitySelected() && selections.get(i).getSelectedEntity().getID() == ownerEntity.getID())
			{
				selections.get(i).resetSelection();
				checkForTradeIn();
				return;
			}
		}
		for(int i = 0; i < selections.size(); ++i)
		{
			if(selections.get(i).hasEntitySelected())
			{
				Card c = (Card) selections.get(i).getSelectedEntity();
				if(c.getValue() == Card.ARTILLERY)
					artilleryCount++;
				else if(c.getValue() == Card.CAVALRY)
					cavlaryCount++;
				else
					infantryCount++;
			}
		}
		if(
				(infantryCount == 2 && ownerEntity.getValue() != Card.INFANTRY)
				||
				(cavlaryCount == 2 && ownerEntity.getValue() != Card.CAVALRY)
				||
				(artilleryCount == 2 && ownerEntity.getValue() != Card.ARTILLERY)
				||
				(infantryCount == 1 && cavlaryCount == 1 && ownerEntity.getValue() != Card.ARTILLERY)
				|| 
				(cavlaryCount == 1 && artilleryCount == 1 && ownerEntity.getValue() != Card.INFANTRY)
				|| 
				(artilleryCount == 1 && infantryCount == 1 && ownerEntity.getValue() != Card.CAVALRY))
			return;
		
		
		
		
		for(int i = 0; i < selections.size(); ++i){
			if(!selections.get(i).hasEntitySelected())
			{
				selections.get(i).selectEntity(ownerEntity);
				checkForTradeIn();
				return;
			}
		}
	}



	/**
	 * Implementation of the abstract CancelAction method.
	 * This method is called from a CancelButton to enter the GameplayState again.
	 */
	@Override
	public void cancelAction(StateBasedGame game) {
		game.enterState(Launch.GAMEPLAY_STATE);
	}

}
