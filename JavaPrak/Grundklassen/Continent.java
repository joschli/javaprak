import java.util.ArrayList;


public class Continent {
	private ArrayList<Country> countries;
	private int bonusTroops;
	
	public Continent(ArrayList<Country> countries, int bonusTroops){
		this.countries = countries;
		this.bonusTroops = bonusTroops;
	}
}
