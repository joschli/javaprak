package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterExtended3;

public class MissionTests {

	RiskTestAdapterExtended3 adapter;
	
	@Before
	public void setUp()
	{
		adapter = new RiskTestAdapterExtended3();
	}
	
	@After
	public void finish(){
		adapter.stopGame();
	}
	@Test
	public void missionCreatedTest() {
		
		adapter.setPlayerCount(2);
		adapter.setMissionsMode(true);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 String m1 = adapter.getMissionForPlayer(0);
		 String m2 = adapter.getMissionForPlayer(1);
		 assertTrue("Zwei Spieler bekommen die gleiche Mission" ,!m1.equals(m2));
		 assertTrue("Missionszahl stimmt nicht. Es sollten 9 und eine für jeden Spieler sein", adapter.getNumberOfMissions() == 9+adapter.getPlayerCount());
		 
	}
	
	@Test
	public void missionFulfilledTest() {
		
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.skipToReinforcementPhase();
		adapter.setAllReinforcements();
		
		adapter.setDefeatMissionForPlayer(0,0);
		adapter.fulfillCountryMissionForPlayer(24,0);
		
		adapter.endTurn();
		adapter.getWinner();
		assertTrue( "Gewinner wird nach Gewinnen nicht richtig gesetzt(bei einer Selbstvernichtungsmission)",adapter.getWinner().equals(adapter.getNameOfPlayer(0)));
		
	}

	
}
