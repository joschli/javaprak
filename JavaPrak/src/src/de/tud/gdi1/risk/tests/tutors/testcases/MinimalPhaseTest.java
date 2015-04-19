package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi.risk.tests.adapter.RiskTestAdapterMinimal;
import src.de.tud.gdi1.risk.ui.Launch;

public class MinimalPhaseTest {
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
	public void nextPhaseTest()
	{
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int playerCount = adapter.getPlayerCount();
		
		assertTrue("Der Beginnende Spieler ist nicht Spieler 0", adapter.getCurrentPlayerNumber() == 0);
		assertTrue("Anzahl Spieler ist kleiner 2", playerCount > 1);
		assertTrue("Spiel beginnt nicht in der richtigen Phase", adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		
		for(int i = 0; i < playerCount; i++)
		{
			assertTrue("Spieler sollten in der Reinforcement-Phase Truppen haben, die sie setzen können", adapter.getReinforcementsForPlayer(adapter.getCurrentPlayerNumber())> 0);
			adapter.nextPhase();
			assertTrue("In der Reinforcement-Phase sollte es nicht möglich sein die Phase zu wechseln bevor nicht alle Einheiten gesetzt sind", adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
			adapter.setAllReinforcements();
			System.out.println(adapter.getPhase());
			if(adapter.getPhase() == adapter.REINFORCEMENT_PHASE)
			{
				adapter.nextPhase();
			}
			
			assertTrue("Übergang von Reinforcement-Phase zu Attack-Phase gescheitert", adapter.getPhase() == adapter.ATTACKING_PHASE);
			assertTrue("Spieler sollten in der Attack-Phase keine Truppen mehr zum Verstärken haben", adapter.getReinforcementsForPlayer(adapter.getCurrentPlayerNumber())== 0);
			assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == i);
			adapter.nextPhase();
			assertTrue("Übergang von Attack-Phase zu Fortifying-Phase gescheitert", adapter.getPhase() == adapter.FORTIFYING_PHASE);
			assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == i);
			adapter.endTurn();
			if(i != playerCount-1)
			{
				assertTrue("Der Beginnende Spieler wechselt nicht nach seinem Zug", adapter.getCurrentPlayerNumber() == i+1);
			}
			else
			{
				assertTrue("Der momentane Spieler wird nach einer kompletten Runde nicht auf Spieler 0 gesetzt", adapter.getCurrentPlayerNumber() == 0);
			}
			assertTrue("Der Übergang der Züge von Spielern funktioniert nicht", adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		}
		
		adapter.stopGame();
	}
	
	@Test
	public void reinforceTest()
	{
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Spiel beginnt nicht in der richtigen Phase", adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
		assertTrue("Der Beginnende Spieler ist nicht Spieler 0", adapter.getCurrentPlayerNumber() == 0);
		
		String country = adapter.getCountryNameOwnedByPlayer(adapter.getCurrentPlayerNumber());
		int troops = adapter.getTroopsForCountry(country);
		adapter.reinforceCountry(country, 1);
		assertTrue("Verstärkungen werden nicht dazu gerechnet", adapter.getTroopsForCountry(country) == troops+1);
		
		String invalidCountry = adapter.getCountryNameOwnedByPlayer((adapter.getCurrentPlayerNumber()+1));
		troops = adapter.getTroopsForCountry(invalidCountry);
		adapter.reinforceCountry(invalidCountry, 1);
		assertTrue("Es sollte nicht möglich sein gegnerische Länder zu verstärken", adapter.getTroopsForCountry(invalidCountry) == troops);
		
		country = adapter.getCountryNameOwnedByPlayer(adapter.getCurrentPlayerNumber());
		troops = adapter.getTroopsForCountry(country);
		int reinforcements = adapter.getReinforcementsForPlayer(adapter.getCurrentPlayerNumber());
		adapter.reinforceCountry(country, reinforcements+1 );
		assertTrue("Es sollte nicht möglich sein, um mehr als die verfügbaren Verstärkungen zu verstärken", adapter.getTroopsForCountry(country) == troops+reinforcements);

	}
	
	@Test
	public void attackTest()
	{
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.setAllReinforcements();
		
		if(adapter.getPhase() == adapter.REINFORCEMENT_PHASE)
		{
			adapter.nextPhase();
		}
		
		assertTrue("Übergang von Reinforcement-Phase zu Attack-Phase gescheitert", adapter.getPhase() == adapter.ATTACKING_PHASE);
		assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == 0);
		String attCountry = adapter.getBorderCountryOwnedByPlayer(adapter.getCurrentPlayerNumber());
		String defCountry = adapter.getNeighborCountryNotOwnedByPlayer(attCountry, adapter.getCurrentPlayerNumber());
		
		adapter.setTroopsForCountry(attCountry,1);
		adapter.setTroopsForCountry(defCountry,2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(attCountry) == 1);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(defCountry) == 2);
		adapter.attack(attCountry,defCountry,1);
		assertTrue("Es sollte nicht möglich sein mit einer Einheit anzugreifen!", adapter.getTroopsForCountry(attCountry) == 1);
		assertTrue("Es sollte nicht möglich sein mit einer Einheit anzugreifen!", adapter.getTroopsForCountry(defCountry) == 2);
		
		String[] invalidCountries = adapter.getNeighboredCountries(adapter.getCurrentPlayerNumber(), adapter.getCurrentPlayerNumber());
		attCountry = invalidCountries[0];
		defCountry = invalidCountries[1];
		adapter.setTroopsForCountry(attCountry,2);
		adapter.setTroopsForCountry(defCountry,2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(attCountry) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(defCountry) == 2);
		adapter.attack(attCountry, defCountry, 1);
		assertTrue("Es sollte nicht möglich sein eigene Länder anzugreifen!", adapter.getTroopsForCountry(attCountry) == 2);
		assertTrue("Es sollte nicht möglich sein eigene Länder anzugreifen!", adapter.getTroopsForCountry(defCountry) == 2);
		
	}
	
	@Test
	public void fortifyTest()
	{
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.setAllReinforcements();
		adapter.nextPhase();
		adapter.nextPhase();
		assertTrue("Übergang von Attack-Phase zu Fortifying-Phase gescheitert", adapter.getPhase() == adapter.FORTIFYING_PHASE);
		assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == 0);
		String[] countries = adapter.getNeighboredCountries(adapter.getCurrentPlayerNumber(), adapter.getCurrentPlayerNumber());
		//If there should be an assignment where this is not possible restart the test with another initialization
		if(countries == null)
		{
			fortifyTest();
			return;
		}
		assertTrue("Fehler im Adapter", adapter.getOwnerNameForCountry(countries[0]) == adapter.getOwnerNameForCountry(countries[1])
										&& adapter.getOwnerOfCountry(countries[0]) == adapter.getCurrentPlayerNumber());
		adapter.setTroopsForCountry(countries[0],2);
		adapter.setTroopsForCountry(countries[1],2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(countries[0]) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(countries[1]) == 2);
		
		adapter.moveTroops(countries[0],countries[1],1);
		
		assertTrue("Bewegen von Truppen funktioniert nicht. Ausgangsland hat nach dem Bewegen eine falsche Anzahl",adapter.getTroopsForCountry(countries[0]) == 1);
		assertTrue("Bewegen von Truppen funktioniert nicht. Zielland hat nach dem Bewegen eine falsche Anzahl", adapter.getTroopsForCountry(countries[1]) == 3);
		
		adapter.setTroopsForCountry(countries[0],2);
		adapter.setTroopsForCountry(countries[1],2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(countries[0]) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(countries[1]) == 2);
		
		adapter.moveTroops(countries[0], countries[1], 2);
		assertTrue("Bewegungen mit mehr als möglichen Einheiten sollten nicht möglich sein|Eine Einheit muss zurückbleiben", adapter.getTroopsForCountry(countries[0]) == 2) ;
		assertTrue("Bewegungen mit mehr als möglichen Einheiten sollten nicht möglich sein|Eine Einheit muss zurückbleiben", adapter.getTroopsForCountry(countries[1]) == 2);
		
		String invalid1 = adapter.getBorderCountryOwnedByPlayer(adapter.getCurrentPlayerNumber());
		String invalid2 = adapter.getNeighborCountryNotOwnedByPlayer(invalid1, adapter.getCurrentPlayerNumber());
		
		adapter.setTroopsForCountry(invalid1,2);
		adapter.setTroopsForCountry(invalid2,2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(countries[0]) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(countries[1]) == 2);
		
		adapter.moveTroops(invalid1, invalid2, 1);
		assertTrue("Bewegungen in gegnerische Länder sollten nicht möglich sein", adapter.getTroopsForCountry(invalid1) == 2) ;
		
		
		
	}
}
