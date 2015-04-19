package src.de.tud.gdi1.risk.ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class UILabel extends UIElement{

	public Color color;
	private String labelName;
	private boolean setPosition = false;
	private boolean lineBreak = false;
	
	public UILabel(String entityID, String labelName, Color color, Vector2f position) {
		super(entityID);
		this.labelName = labelName;
		this.color = color;
		this.setPosition(position);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.isVisible()){
			if(!lineBreak || !setPosition)
			{
				checkName(g);
			}
			g.setColor(color);
			g.drawString(labelName, this.getPosition().x, this.getPosition().y);
			
		}
	}
	
	private void checkName(Graphics g) {
		String text = labelName;
		if(text.contains(" ") && (this.getSize().x != 0 || this.getSize().y != 0 && !lineBreak))
		{
		
			if(g.getFont().getWidth(labelName) > this.getSize().x)
			{
				ArrayList<String> words = new ArrayList<String>();
				/*
				float letterWidth = g.getFont().getWidth("a");
				int letterPerLine = (int) ((int) this.getSize().x / letterWidth);
				int lineSegments = labelName.length()/letterPerLine;
				for(int i = 0; i < lineSegments; i++)
				{
					text += labelName.substring(i*letterPerLine, i*letterPerLine+letterPerLine);
					text += "\n";
					
				}
				if(letterPerLine*lineSegments+letterPerLine < labelName.length()){
					text += "\n";
					text += labelName.substring(letterPerLine*lineSegments+letterPerLine, labelName.length());
				}
				*/
				int index = -1;
				while(text.indexOf(" ", index+1) != -1)
				{
					int newIndex = text.indexOf(" ", index+1);
					words.add(text.substring(index+1, newIndex+1));
					index = newIndex;
				}
				words.add(text.substring(index+1, text.length()));
				String test = "";
				for(String s : words)
					test += s;
				//System.out.println(test);
				ArrayList<String> lines = new ArrayList<String>();
				int wordIndex = 0;
				String line = "";
				while(wordIndex < words.size()){
					float lineLength = g.getFont().getWidth(line);
					float wordLength = g.getFont().getWidth(words.get(wordIndex));
					if(lineLength + wordLength < this.getSize().x)
						line += words.get(wordIndex);
					else
					{
						lines.add(line);
						line = words.get(wordIndex);
					}
					wordIndex++;
				}
				lines.add(line);
				test = "";
				for(String s : lines)
					test += s + "\n";
				//System.out.println(test);
				this.labelName = test;
				lineBreak = true;
				text = test;
			}
		}
		if(!setPosition)
		{
			float textWidth = g.getFont().getWidth(text);
			float textHeight = g.getFont().getHeight(text);
			if(text.contains("\n"))
			{
				textWidth = g.getFont().getWidth(text.substring(0, text.indexOf("\n")));
			}
			this.setPosition(new Vector2f(this.getPosition().x-textWidth/2, this.getPosition().y-textHeight/2));
			this.setPosition = true;
		}
	}

	public void setLabelName(String labelName)
	{
		this.labelName = labelName;
		this.lineBreak = false;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public String getContent() {
		return labelName;
	}
	
	public Color getColor()
	{
		return this.color;
	}

}
