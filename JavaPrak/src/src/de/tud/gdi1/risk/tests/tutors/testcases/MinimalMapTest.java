package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterMinimal;


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
		boolean allCountriesWithContinent = true;
		
		for(String s  : adapter.getContinentNamesForCountries())
		{
			if(s.equals(""))
			{
				allCountriesWithContinent = false;
			}
			assertTrue("Continent " + s + "only belongs to a Country but doesn't exist",existingContinents.contains(s));
			if(!continents.contains(s))
				continents.add(s);
		}
		
		assertTrue("Not Enough Continents", 1 < continents.size());
		assertTrue("There are Countries without Continent", allCountriesWithContinent);
		
		ArrayList<String> players = adapter.getPlayerNames();
		ArrayList<String> countryNames = adapter.getCountryNames();
		int countryCount = countryNames.size();
		
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
		
		for(int countries : countriesForPlayer)
		{
			assertTrue("Countrys don't get evenly distributed", countries == countryCount/playerCount || countries == (countryCount/playerCount) +1);
		}
			
		
	}
	
	

}
