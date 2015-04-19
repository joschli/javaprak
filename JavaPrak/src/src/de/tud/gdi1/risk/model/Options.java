package src.de.tud.gdi1.risk.model;

import org.newdawn.slick.Color;

/**
 * Options for the game
 * This class saves all the information need to setup a game.
 */

public class Options {

	private static Options options = new Options();
	private int playerCount;
	private Color[] colors = {Color.red, Color.gray, new Color(0,100,0), Color.yellow, new Color(0,100,100), new Color(255,0,255)};
	private int minimalPlayerCount = 2;
	private int maximalPlayerCount = colors.length;
	private String winner = "";
	private boolean missions = false;
	
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

	public int getMinPlayerCount() {
		return this.minimalPlayerCount;
	}
	
	public int getMaxPlayerCount()
	{
		return this.maximalPlayerCount;
	}

	public void setPlayerCount(int counter) {
		this.playerCount = counter;
	}

	public void setWinner(String name) {
		this.winner = name;
	}

	public String getWinner() {
		return winner;
	}
	
	public void setMissions(boolean b)
	{
		this.missions = b;
	}
	
	public boolean getMissions()
	{
		return missions;
	}
	
}
