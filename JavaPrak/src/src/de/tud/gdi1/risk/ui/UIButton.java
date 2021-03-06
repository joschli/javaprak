package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class UIButton extends UIElement{
	
	private Color color, borderColor, labelColor;
	private ImageRenderComponent renderComponent;
	private UILabel label;
	private Entity owner;
	private boolean usable = true;
	
	public UIButton(String entityID, String buttonName, Vector2f position, Vector2f size, Color buttonColor, Color labelColor) {
		super(entityID);
		
		this.color = buttonColor;
		this.setScale(1);
		this.borderColor = Color.black;
		// Label on the Button
		label = new UILabel(entityID+"Label", buttonName, labelColor, new Vector2f((this.getPosition().x-this.getSize().x/2), (this.getPosition().y-this.getSize().y/2) ));
		label.setVisible(true);
		this.labelColor = labelColor;
		this.setPosition(position);
		this.setSize(size);
	}
	
	/**
	 * sets a ImageRenderComponent that should be drawn as the button Background
	 * @param renderComponent
	 */
	public void setRenderComponent(ImageRenderComponent renderComponent)
	{
		this.renderComponent = renderComponent;
		renderComponent.setOwnerEntity(this);
		this.setSize(renderComponent.getSize());
	}
	
	/**
	 * Draws a rect in the buttonColor if no renderComponent is assigned
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible()){
			float x,y;
			x = this.getPosition().x-this.getSize().x/2;
			y = this.getPosition().y-this.getSize().y/2;
			if(renderComponent != null){
				renderComponent.render(container, game, g);
			}
			else 
			{
				if(usable){
					g.setColor(color);
				}
				else{
					g.setColor(new Color(100, 100, 100, 100));
				}
				g.fillRect(x, y, this.getSize().x, this.getSize().y);
			}
			if(!usable)
				label.setColor(new Color(100,100,100));
			else
				label.setColor(labelColor);
			this.setLabelPosition(g);
			label.render(container, game, g);
			g.setColor(borderColor);
			g.drawRect(x, y, this.getSize().x, this.getSize().y);
		}
	}

	/**
	 * autoposition of the label on the button, influenced by the labellength
	 * @param g
	 */
	private void setLabelPosition(Graphics g) {
		String text = label.getContent();
		float textWidth = g.getFont().getWidth(text);
		float textHeight = g.getFont().getHeight(text);
		float boxHeight = this.getSize().y;
		float boxWidth = this.getSize().x;
		float widthPadding = boxWidth - textWidth;
		float heightPadding = boxHeight - textHeight;
		if(widthPadding < 0)
		{
			this.setSize(new Vector2f(textWidth+10, this.getSize().y));
			this.label.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2)+5, this.label.getPosition().y));
		}
		else
			this.label.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2)+widthPadding/2, (this.label.getPosition().y)));
		if(heightPadding < 0){
			this.setSize(new Vector2f(this.getSize().x, textHeight+5));
			this.label.setPosition(new Vector2f(this.label.getPosition().x, this.getPosition().y - this.getSize().y/2 + 2.5f));
		}
		else
			this.label.setPosition(new Vector2f(this.label.getPosition().x, this.getPosition().y - this.getSize().y/2 + heightPadding/2));
	}

	public void setButtonColor(Color color)
	{
		this.color = color;
	}
	public void setLabelName(String labelName)
	{
		this.label.setLabelName(labelName);
	}
	
	public void setLabelColor(Color color)
	{
		this.label.setColor(color);
		this.labelColor = color;
	}
	
	public void setOwner(Entity owner)
	{
		this.owner = owner;
	}
	
	public Entity getOwner()
	{
		return this.owner;
	}
	
	public void setPosition(Vector2f position)
	{
		super.setPosition(position);
		label.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2), (this.getPosition().y-this.getSize().y/2) ));
		
	}
	
	public void setSize(Vector2f size)
	{
		super.setSize(size);
		label.setPosition(new Vector2f((this.getPosition().x-this.getSize().x/2), (this.getPosition().y-this.getSize().y/2) ));
	}
	
	public void setBorderColor(Color color)
	{
		if(color != null)
			this.borderColor = color;
	}
	
	public void disableButton()
	{
		this.usable = false;
	}
	
	public void enableButton()
	{
		this.usable = true;
	}
	
	public boolean getUsability()
	{
		return usable;
	}

	public UILabel getLabel() {
		return label;
	}
}
