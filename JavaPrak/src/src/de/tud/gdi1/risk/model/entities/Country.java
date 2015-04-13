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
	private int cardValue;
	private Color color;
	
	//owner bei initialisierung wird als Spieler "neutral" mitgegeben?
	public Country(String name, int cardValue){
		super(name);
		troops = 0;
		this.name = name;
		this.cardValue = cardValue;
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
	
	public boolean isNeighbor(Country neighbor)
	{
		if(neighbors == null)
			throw new NullPointerException();
		return neighbors.contains(neighbor);
	}
	
	public boolean isOwner(Player player)
	{
		if(player == null)
			throw new NullPointerException();
		return owner.getName() == player.getName();
	}
	
	public String getName()
	{
		return name;
	}

	public int getCardValue() {
		return cardValue;
	}

	public void addForce(int reinforcement) {
		troops += reinforcement;
	}

	public int getTroops() {
		return troops;
	}

	public void moveTroops(int force) {
		this.troops -= force;
	}

	public void addTroops(int force) {
		this.troops += force;
	}

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
