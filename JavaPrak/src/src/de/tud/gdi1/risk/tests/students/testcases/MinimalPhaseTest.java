package src.de.tud.gdi1.risk.tests.students.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterMinimal;

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
		assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == 0);
		adapter.nextPhase();
		assertTrue("Übergang von Attack-Phase zu Fortifying-Phase gescheitert", adapter.getPhase() == adapter.FORTIFYING_PHASE);
		assertTrue("Der Beginnende Spieler wechselt in seinem Zug", adapter.getCurrentPlayerNumber() == 0);
		adapter.endTurn();
		assertTrue("Der Beginnende Spieler wechselt nicht nach seinem Zug", adapter.getCurrentPlayerNumber() == 1);
		assertTrue("Der Übergang der Züge von Spielern funktioniert nicht", adapter.getPhase() == adapter.REINFORCEMENT_PHASE);
	
		
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
		
		
		adapter.setTroopsForCountry(attCountry,2);
		adapter.setTroopsForCountry(defCountry,2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(attCountry) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(defCountry) == 2);
		int attackerLoss = adapter.attack(attCountry,defCountry,1);
		System.out.println(attackerLoss);
		assertTrue("Truppen nach Angriff für Angreifer nicht richtig geändert", adapter.getTroopsForCountry(attCountry) == 2 - attackerLoss);
		assertTrue("Truppen nach Angriff für Verteidiger nicht richtig geändert", adapter.getTroopsForCountry(defCountry) == 2 - (1-attackerLoss));
		
		adapter.setTroopsForCountry(attCountry,2);
		adapter.setTroopsForCountry(defCountry,2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt",adapter.getTroopsForCountry(attCountry) == 2);
		assertTrue("Truppen im Adapter nicht richtig gesetzt", adapter.getTroopsForCountry(defCountry) == 2);
		attackerLoss = adapter.attack(attCountry,defCountry,2);
		assertTrue("Attacken mit mehr als möglichen Einheiten sollten nicht möglich sein|Eine Einheit muss zurückbleiben", adapter.getTroopsForCountry(attCountry) == 2) ;
		assertTrue("Attacken mit mehr als möglichen Einheiten sollten nicht möglich sein|Eine Einheit muss zurückbleiben", adapter.getTroopsForCountry(attCountry) == 2);

		
		
		
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
		
	}
}
