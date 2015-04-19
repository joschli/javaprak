package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterExtended2;

public class CardTests {
	
	RiskTestAdapterExtended2 adapter;
	
	@Before
	public void setUp()
	{
		adapter = new RiskTestAdapterExtended2();
	}
	
	@After
	public void finish(){
		adapter.stopGame();
	}
	@Test
	public void cardGenerateTest() {
		
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> cardCountries = adapter.getCardCountries();
		ArrayList<Integer> cardValues = adapter.getCardValues();
		ArrayList<String> countries = adapter.getCountryNames();
		
		assertTrue(cardValues.size() == cardCountries.size());
		assertTrue("Die Anzahl an Ländern und Karten entspricht nicht dem normalen Risiko",cardValues.size() == 42);
		assertTrue("Es wird nicht für jedes Land genau eine Karte erstellt",cardValues.size() == countries.size());
		
		for(String s : cardCountries)
		{
			assertTrue("Land " + s + " auf Karte nicht im Spiel existent!", countries.contains(s));
		}
		
		for(String s : countries)
		{
			assertTrue("Land " + s + " besitzt keine Karte", cardCountries.contains(s));
		}
		
		int infantryCount = 0;
		int cavalryCount = 0;
		int artilleryCount = 0;
		
		boolean unknownValue = false;
		
		for(int i : cardValues)
		{
			if(i == adapter.INFANTRY)
				infantryCount++;
			else if(i == adapter.CAVALRY)
				cavalryCount++;
			else if(i == adapter.ARTILLERY)
				artilleryCount++;
			else
				unknownValue = true;
		}
		assertTrue("Unbekannter Kartenwert", !unknownValue);
		assertTrue("Kartenwerte sind nicht gleichverteilt", infantryCount == cavalryCount && cavalryCount == artilleryCount);
		
		assertTrue("Spieler sollten zu Beginn keine Karten besitzen", adapter.getCardCountriesForPlayer(0).size() == 0);
		
	}
	
	@Test
	public void cardTradeInTest() {
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.skipToReinforcementPhase();
		assertTrue(adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		
		adapter.giveCardsToPlayer(42,0);
		ArrayList<Integer> values = adapter.getPlayerCardValues(0);
		assertTrue("Karten werden dem Spieler nicht richtig gegeben", values.size() == 42);
		
		int reinforcements = adapter.getReinforcementsForPlayer(0);
		
		int[] tradeIn = new int[3];
		int index = 0;
				
		for(int i = 0 ; i < values.size(); i++)
		{
			if(values.get(i) == adapter.INFANTRY)
			{
				tradeIn[index] = i;
				index++;
				if(index == 3)
					break;
			}
				
		}

		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Beim Eintauschen von Karten werden Verstärkungen nicht korrekt addiert",adapter.getReinforcementsForPlayer(0) == reinforcements+4);
		assertTrue("Karten sollten nach dem  Eintauschen nicht mehr dem Spieler gehören", adapter.getPlayerCardValues(0).size() == 39);
		values = adapter.getPlayerCardValues(0);
		tradeIn = new int[3];
		index = 0;
		
		for(int i = 0 ; i < values.size(); i++)
		{
			if(values.get(i) == adapter.CAVALRY && index < 2)
			{
				tradeIn[index] = i;
				index++;
				
			}
			if(index == 2 && adapter.INFANTRY == values.get(i))
			{
				tradeIn[index] = i;
				break;
			}			
		}
		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Beim Eintauschen von inkorrekten Kartensets sollte nichts passieren",adapter.getReinforcementsForPlayer(0) == reinforcements+4);

		reinforcements = adapter.getReinforcementsForPlayer(0);
		values = adapter.getPlayerCardValues(0);
		tradeIn = new int[3];
		index = 0;
		
		for(int i = 0 ; i < values.size(); i++)
		{
			if(values.get(i) == adapter.CAVALRY)
			{
				tradeIn[0] = i;	
			}
			if(adapter.INFANTRY == values.get(i))
			{
				tradeIn[1] = i;
			}		
			if(adapter.ARTILLERY == values.get(i))
			{
				tradeIn[2] = i;
			}
		}
		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Beim Eintauschen von einem gemischten Kartenset werden Verstärkungen falsch berechnet",adapter.getReinforcementsForPlayer(0) == reinforcements + 10);

		reinforcements = adapter.getReinforcementsForPlayer(0);
		values = adapter.getPlayerCardValues(0);
		tradeIn = new int[3];
		index = 0;
		
		for(int i = 0 ; i < values.size(); i++)
		{
			if(values.get(i) == adapter.CAVALRY)
			{
				tradeIn[index] = i;
				index++;
				if(index == 3)
					break;
			}
				
		}

		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Beim Eintauschen von einem Kavallerie Kartenset werden Verstärkungen falsch berechnet",adapter.getReinforcementsForPlayer(0) == reinforcements + 6);

		reinforcements = adapter.getReinforcementsForPlayer(0);
		values = adapter.getPlayerCardValues(0);
		tradeIn = new int[3];
		index = 0;
		
		for(int i = 0 ; i < values.size(); i++)
		{
			if(values.get(i) == adapter.ARTILLERY)
			{
				tradeIn[index] = i;
				index++;
				if(index == 3)
					break;
			}
				
		}

		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Beim Eintauschen von einem Artillerie Kartenset werden Verstärkungen falsch berechnet",adapter.getReinforcementsForPlayer(0) == reinforcements + 8);
	}
	
	@Test
	public void cardAddedTest() {
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.skipToReinforcementPhase();
		assertTrue(adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		
		adapter.giveCardsToPlayer(1, adapter.getCurrentPlayerNumber());
		assertTrue("Karten werden einem Spieler nicht gegeben!", adapter.getPlayerCardValues(adapter.getCurrentPlayerNumber()).size() == 1);
		
	}
	
	@Test
	public void bonusUnitsAtTradeInTest() {
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.skipToReinforcementPhase();
		assertTrue(adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		
		adapter.giveCardsToPlayer(42,0);
		ArrayList<Integer> values = adapter.getPlayerCardValues(0);
		ArrayList<String> countries = adapter.getCardCountriesForPlayer(0);
		
		assertTrue("Karten werden dem Spieler nicht richtig gegeben", values.size() == 42);
		
	
		int[] tradeIn = new int[3];
		int index = 0;
				
		for(int i = 0; i < countries.size(); i++)
		{
			if(adapter.getOwnerOfCountry(countries.get(i)) == 0)
				tradeIn[0] = i;
		}
		
		index = 1;
		int cardValue = values.get(tradeIn[0]);
		
		for(int i = 0 ; i < values.size(); i++)
		{
			if(i != tradeIn[0] && values.get(i) == cardValue && adapter.getOwnerOfCountry(countries.get(i))== 0)
			{
				tradeIn[index] = i;
				index++;
				if(index == 3)
					break;
			}
				
		}
		
		int countryTroops = adapter.getTroopsForCountry(countries.get(tradeIn[0]));
		int countryTroops2 = adapter.getTroopsForCountry(countries.get(tradeIn[1]));
		int countryTroops3 = adapter.getTroopsForCountry(countries.get(tradeIn[2]));
		adapter.tradeInCardsForPlayer(tradeIn,0);
		assertTrue("Sondereinheiten werden nicht richtig platziert",adapter.getTroopsForCountry(countries.get(tradeIn[0])) == countryTroops+2);
		assertTrue("Sondereinheiten sollten nur auf dem ersten Land platziert werden",adapter.getTroopsForCountry(countries.get(tradeIn[1])) == countryTroops2);
		assertTrue("Sondereinheiten sollten nur auf dem ersten Land platziert werdent",adapter.getTroopsForCountry(countries.get(tradeIn[2])) == countryTroops3);
		
		
	}

}
