import java.util.ArrayList;


public class Country {

	private ArrayList<Country> neighbors;
	private String name;
	private int troops;
	private Player owner;
	
	//owner bei initialisierung wird als Spieler "neutral" mitgegeben?
	public Country(String name, ArrayList<Country> neighbors, Player owner){
		troops = 0;
		this.owner = owner;
		this.name = name;
		this.neighbors = neighbors;
	}
}
