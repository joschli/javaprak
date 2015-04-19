package src.de.tud.gdi1.risk.model.entities;
import java.util.ArrayList;

import org.newdawn.slick.Color;

import src.de.tud.gdi1.risk.model.Player;
import eea.engine.entity.Entity;


public class Country extends Entity {

	private ArrayList<Country> neighbors;
	private String name;
	private int troops;
	private Player owner;
	private Color color;
	
	public Country(String name){
		super(name);
		troops = 0;
		this.name = name;
		owner = null;
		this.color = null;
	}
	
	public void setNeighbors(ArrayList<Country> neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public void setOwner(Player newOwner)
	{
		this.owner = newOwner;
	}
	
	/**
	 * checks if the country is the neighbor of a given country
	 * @param neighbor country to check
	 * @return true if the country is a neighbor, else false
	 */
	public boolean isNeighbor(Country neighbor)
	{
		if(neighbors == null)
			throw new NullPointerException();
		return neighbors.contains(neighbor);
	}
	
	/**
	 * checks if the given player owns this country
	 * @param player to check
	 * @return true if the player owns this country, else false
	 */
	public boolean isOwner(Player player)
	{
		if(player == null)
			throw new NullPointerException();
		return owner.getName().equals(player.getName());
	}
	
	public String getName()
	{
		return name;
	}

	public int getTroops() {
		return troops;
	}

	/**
	 * moves Troops from this country
	 * @param force to be moved
	 * @return true if there are enough Troops on the country to move (this.troops < force), else false
	 */
	public boolean moveTroops(int force) {
		if(this.troops < force)
			return false;
		this.troops -= force;
		return true;
	}

	/**
	 * adds Troops to this country
	 * @param reinforcement, Troops to be added
	 */
	public void addTroops(int force) {
		this.troops += force;
	}

	/**
	 * Checks if the country is owned by a player
	 * @return true if it is owned, else false
	 */
	public boolean hasOwner() {
		return owner != null;
	}

	public Player getOwner() {
		return owner;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
