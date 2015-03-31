package src.de.tud.gdi1.risk.model;

import org.newdawn.slick.Color;


public class Options {

	private static Options options = new Options();
	private int playerCount;
	private Color[] colors = {Color.red, Color.black, Color.blue, Color.yellow};
	
	private Options()
	{
		this.playerCount = 2;
	}
	
	public int getPlayerCount()
	{
		return playerCount;
	}
	
	public static Options getInstance() {
		return options;
	}

	public Color getColor(int i) {
		return colors[i];
	}
	
}
