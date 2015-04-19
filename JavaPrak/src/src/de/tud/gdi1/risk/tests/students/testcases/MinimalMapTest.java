package src.de.tud.gdi1.risk.tests.students.testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi.risk.tests.adapter.RiskTestAdapterMinimal;


public class MinimalMapTest {
	
	RiskTestAdapterMinimal adapter;
	
	@Before
	public void setUp()
	{
		adapter = new RiskTestAdapterMinimal();
	}
	
	@After
	public void finish(){
		adapter.stopGame();
	}
	
	
	@Test
	public void initializeNormalMapTest() {
		 
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Not Enough Countries", 9 < adapter.getNumberOfCountries());
		
		ArrayList<String> continents = new ArrayList<String>();
		ArrayList<String> existingContinents = adapter.getContinentNames();
		
		for(String s  : adapter.getContinentNamesForCountries())
		{
	
			assertTrue("Continent " + s + "only belongs to a Country but doesn't exist",existingContinents.contains(s));
			if(!continents.contains(s))
				continents.add(s);
		}
		
		assertTrue("Not Enough Continents", 1 < continents.size());
		
		ArrayList<String> players = adapter.getPlayerNames();
		ArrayList<String> countryNames = adapter.getCountryNames();
		
		int playerCount = adapter.getPlayerCount();
		assertTrue("Player Count and actual Player Number don't match", playerCount == players.size());
		assertTrue("Not Enough Players", 1 < playerCount);
		
		int[] countriesForPlayer = new int[playerCount];
		
		for(String s : countryNames)
		{
			assertTrue("Country " + s + " has no Owner assigned", players.contains(adapter.getOwnerNameForCountry(s)));
			countriesForPlayer[players.indexOf(adapter.getOwnerNameForCountry(s))]++;
			assertEquals("Country " + s + " has no Troops at the beginning", adapter.getTroopsForCountry(s), 1);
		}
		

		
	}
	
	

}
