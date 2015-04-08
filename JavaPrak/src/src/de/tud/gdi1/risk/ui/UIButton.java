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
	private int padding = 10;
	
	public UIButton(String entityID, Vector2f position, Color color) {
		super(entityID);
		this.setPosition(position);
		this.color = color;
		this.setScale(0.312f);
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
		
		this.setSize(new Vector2f(128, 32));
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
			g.setColor(Color.gray);
			g.fillRect(x, y, this.getSize().x, this.getSize().y);
		}
		g.setColor(color);
		g.drawString(this.getID(), x+padding, y+padding);
	}

}
