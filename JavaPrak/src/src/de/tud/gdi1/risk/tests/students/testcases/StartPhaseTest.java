package src.de.tud.gdi1.risk.tests.students.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterExtended1;
import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterMinimal;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int playerCount = adapter.getPlayerCount();
		
		assertTrue("Der Beginnende Spieler ist nicht Spieler 0", adapter.getCurrentPlayerNumber() == 0);
		assertTrue("Anzahl Spieler ist kleiner 2", playerCount > 1);
		assertTrue("Spiel beginnt nicht in der richtigen Phase", adapter.getPhase() == adapter.STARTING_PHASE);
		
		int reinforcements = (adapter.getNumberOfCountries()*2) / playerCount;
		
		assertTrue("Der Beginnende Spieler erhält keine oder nicht die richtige Anzahl an Verstärkungen für die Startphase", adapter.getReinforcementsForPlayer(0) == reinforcements);
		adapter.reinforceCountry(adapter.getCountryNameOwnedByPlayer(0), 1);
		assertTrue("Die Verstärkungen in der Startphase des ersten Spielers werden nicht reduziert", adapter.getReinforcementsForPlayer(0) == reinforcements-1);
		assertTrue("Der Spieler wechselt in der Startphase nicht nach Setzen einer Einheit", adapter.getCurrentPlayerNumber() == 1 );
		assertTrue("Der Zweite Spieler erhält keine oder nicht die richtige Anzahl an Verstärkungen für die Startphase", adapter.getReinforcementsForPlayer(1) == reinforcements);
		adapter.reinforceCountry(adapter.getCountryNameOwnedByPlayer(1), 1);
		assertTrue("Die Verstärkungen des zweiten Spielers in der Startphase werden nicht reduziert", adapter.getReinforcementsForPlayer(1) == reinforcements-1);		

	}
}
