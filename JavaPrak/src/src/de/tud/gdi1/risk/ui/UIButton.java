package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.Component;
import eea.engine.component.RenderComponent;
import eea.engine.component.render.ImageRenderComponent;

public class UIButton extends UIElement{
	
	private Color color;
	private ImageRenderComponent renderComponent;
	private Vector2f padding;
	private UILabel label;
	
	public UIButton(String entityID, String buttonName, Vector2f position, Vector2f size, Vector2f padding, Color buttonColor, Color labelColor) {
		super(entityID);
		this.setPosition(position);
		this.color = buttonColor;
		this.setScale(0.312f);
		this.padding = padding;
		/*
		try {
			this.renderComponent = new ImageRenderComponent(new Image("assets/entry.png"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderComponent.setOwnerEntity(this);
		*/
		//this.setSize(renderComponent.getSize());
		this.setSize(size);
		label = new UILabel(entityID+"Label", buttonName, labelColor, new Vector2f((this.getPosition().x-this.getSize().x/2)+padding.x, (this.getPosition().y-this.getSize().y/2)+padding.y ));
		label.setVisible(true);
	}
	
	public void setRenderComponent(ImageRenderComponent renderComponent)
	{
		this.renderComponent = renderComponent;
		renderComponent.setOwnerEntity(this);
		this.setSize(renderComponent.getSize());
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		float x,y;
		x = this.getPosition().x-this.getSize().x/2;
		y = this.getPosition().y-this.getSize().y/2;
		if(renderComponent != null)
			renderComponent.render(container, game, g);
		else 
		{
			g.setColor(color);
			g.fillRect(x, y, this.getSize().x, this.getSize().y);
		}
		label.render(container, game, g);
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
	}
}
