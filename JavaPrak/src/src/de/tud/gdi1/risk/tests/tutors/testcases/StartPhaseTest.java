package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterExtended1;

public class StartPhaseTest {

	
	RiskTestAdapterExtended1 adapter;
	@Before
	public void setUp()
	{
		adapter = new RiskTestAdapterExtended1();
	}
	
	@After
	public void finish(){
		adapter.stopGame();
	}
	@Test
	public void startPhaseTest() {
		
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int playerCount = adapter.getPlayerCount();
		
		assertTrue("Der Beginnende Spieler ist nicht Spieler 0", adapter.getCurrentPlayerNumber() == 0);
		assertTrue("Anzahl Spieler ist kleiner 2", playerCount > 1);
		assertTrue("Spiel beginnt nicht in der richtigen Phase", adapter.getPhase() == adapter.STARTING_PHASE);
		
		int reinforcements = (adapter.getNumberOfCountries()*2) / playerCount;
		
		assertTrue("Der Beginnende Spieler erh�lt keine oder nicht die richtige Anzahl an Verst�rkungen f�r die Startphase", adapter.getReinforcementsForPlayer(0) == reinforcements);
		
		String country = adapter.getCountryNameOwnedByPlayer(0);
		adapter.reinforceCountry(country, 1);
		
		assertTrue("Die Verst�rkungen in der Startphase des ersten Spielers werden nicht reduziert", adapter.getReinforcementsForPlayer(0) == reinforcements-1);
		assertTrue("Der Spieler wechselt in der Startphase nicht nach Setzen einer Einheit", adapter.getCurrentPlayerNumber() == 1 );
		assertTrue("Die Truppen eines Landes werden in der Startphase nicht erh�ht", adapter.getTroopsForCountry(country) == 2);
		
		assertTrue("Der Zweite Spieler erh�lt keine oder nicht die richtige Anzahl an Verst�rkungen f�r die Startphase", adapter.getReinforcementsForPlayer(1) == reinforcements);
		
		
		adapter.reinforceCountry(country, 1);
		
		assertTrue("Es sollte nicht m�glich sein ein gegnerisches Land zu verst�rken| Verst�rkungen f�r den Spieler sollten unver�ndert bleiben", adapter.getReinforcementsForPlayer(1) == reinforcements);		
		assertTrue("Es sollte nicht m�glich sein ein gegnerisches Land zu verst�rken",  adapter.getTroopsForCountry(country) == 2);
	}
}
