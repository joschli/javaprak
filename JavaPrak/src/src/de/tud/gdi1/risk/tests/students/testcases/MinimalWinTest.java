package src.de.tud.gdi1.risk.tests.students.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterMinimal;

public class MinimalWinTest {
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
	public void winTest() {
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adapter.setAllReinforcements();
		adapter.conquerWorld(0);
		adapter.endTurn();
		adapter.getWinner();
		assertTrue( "Gewinner wird nach Gewinnen nicht richtig gesetzt",adapter.getWinner().equals(adapter.getNameOfPlayer(0)));
		
	}

}
