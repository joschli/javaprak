package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gd1.risk.actions.IncreaseAction;
import src.de.tud.gd1.risk.actions.DecreaseAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class UICounter extends UIElement {
	
	private UIButton increaseButton, decreaseButton;
	private UILabel count;
	private int maxCount, minCount, currentCount;
	
	public UICounter(String entityID, Vector2f position, int maxCount, int minCount) {
		super(entityID);
		
		increaseButton = new UIButton("increaseButton"+entityID, "+", new Vector2f(this.getPosition().x+32, this.getPosition().y), new Vector2f(32,32), Color.gray, Color.red);
		decreaseButton = new UIButton("decreaseButton"+entityID, "-", new Vector2f(this.getPosition().x-32, this.getPosition().y), new Vector2f(32,32), Color.gray, Color.red);
		this.maxCount = maxCount;
		this.minCount = minCount;
		this.currentCount = minCount;
		count = new UILabel("count"+entityID, new Integer(minCount).toString(), Color.red, new Vector2f(this.getPosition().x, this.getPosition().y));
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
			this.setLabelPosition(g);
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
			currentCount = maxCount;
		count.setLabelName(new Integer(currentCount).toString());
	}

	public int getCounter() {
		return this.currentCount;
	}
	
	public void setPosition(Vector2f position)
	{
		super.setPosition(position);
		increaseButton.setPosition(new Vector2f(position.x+32, position.y));
		decreaseButton.setPosition(new Vector2f(position.x-32, position.y));
		count.setPosition(new Vector2f(position.x, position.y));
	}
	
	private void setLabelPosition(Graphics g) {
		String text = count.getContent();
		float textWidth = g.getFont().getWidth(text);
		float textHeight = g.getFont().getHeight(text);
		float boxHeight = this.getSize().y;
		float boxWidth = this.getSize().x;
		float widthPadding = boxWidth - textWidth;
		float heightPadding = boxHeight - textHeight;
		if(widthPadding < 0)
		{
			this.setSize(new Vector2f(textWidth+10, this.getSize().y));
			this.count.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2)+5, this.count.getPosition().y));
		}
		else
			this.count.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2)+widthPadding/2, (this.count.getPosition().y)));
		if(heightPadding < 0){
			this.setSize(new Vector2f(this.getSize().x, textHeight+5));
			this.count.setPosition(new Vector2f(this.count.getPosition().x, this.getPosition().y - this.getSize().y/2 + 2.5f));
		}
		else
			this.count.setPosition(new Vector2f(this.count.getPosition().x, this.getPosition().y - this.getSize().y/2 + heightPadding/2));
	}
	
	public void setIncreaseImageRendererComponent(ImageRenderComponent irc)
	{
		this.increaseButton.setRenderComponent(irc);
	}
	
	public void setDecreaseImageRendererComponent(ImageRenderComponent irc)
	{
		this.decreaseButton.setRenderComponent(irc);
	}

	public void setColor(Color color) {
		this.decreaseButton.setLabelColor(color);
		this.increaseButton.setLabelColor(color);
		this.count.setColor(color);
	}
}
