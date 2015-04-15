package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.actions.IncreaseAction;
import src.de.tud.gd1.risk.actions.DecreaseAction;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class UICounter extends UIElement {
	
	private UIButton increaseButton, decreaseButton;
	private UILabel count;
	private int maxCount, minCount, currentCount;
	
	public UICounter(String entityID, Vector2f position, int maxCount, int minCount) {
		super(entityID);
		
		increaseButton = new UIButton("increaseButton"+entityID, "+", new Vector2f(this.getPosition().x+96, this.getPosition().y+16), new Vector2f(32,32), new Vector2f(12,8), Color.gray, Color.red);
		decreaseButton = new UIButton("decreaseButton"+entityID, "-", new Vector2f(this.getPosition().x+16, this.getPosition().y+16), new Vector2f(32,32), new Vector2f(12,8), Color.gray, Color.red);
		this.maxCount = maxCount;
		this.minCount = minCount;
		this.currentCount = minCount;
		count = new UILabel("count"+entityID, new Integer(minCount).toString(), Color.red, new Vector2f(this.getPosition().x+52, this.getPosition().y+8));
		ANDEvent event2 = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		ANDEvent event = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		event.addAction(new IncreaseAction());
		event2.addAction(new DecreaseAction());
		increaseButton.addComponent(event);
		increaseButton.setOwner(this);
		decreaseButton.setOwner(this);
		decreaseButton.addComponent(event2);
		this.setPosition(position);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible()){
			increaseButton.render(container,game,g);
			decreaseButton.render(container,game,g);
			count.render(container, game, g);
		}
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta)
	{
		increaseButton.update(container, game, delta);
		decreaseButton.update(container, game, delta);
	}
	

	public void increaseCount() {
		currentCount++;
		if(currentCount <= maxCount)
			count.setLabelName(new Integer(currentCount).toString());
		else
			currentCount = maxCount;
	}
	
	public void decreaseCount()
	{
		currentCount--;
		if(currentCount >= minCount)
			count.setLabelName(new Integer(currentCount).toString());
		else
			currentCount = minCount;
	}
	
	public void setMinCount(int minCount)
	{
		this.minCount = minCount;
		if(currentCount < minCount)
			currentCount = minCount;
		count.setLabelName(new Integer(currentCount).toString());
	}
	
	public void setMaxCount(int maxCount)
	{
		this.maxCount = maxCount;
		if(currentCount > maxCount)
			currentCount = minCount;
		count.setLabelName(new Integer(currentCount).toString());
	}

	public int getCounter() {
		return this.currentCount;
	}
	
	public void setPosition(Vector2f position)
	{
		super.setPosition(position);
		increaseButton.setPosition(new Vector2f(position.x+96, position.y+16));
		decreaseButton.setPosition(new Vector2f(position.x+16, position.y+16));
		count.setPosition(new Vector2f(position.x+52, position.y+8));
	}
}
