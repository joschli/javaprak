package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi.risk.tests.adapter.RiskTestAdapterExtended3;

public class OptionsTest {

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
	public void test() {
	
		boolean missions = true;
		adapter.setMissionsMode(missions);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue("Modus nicht gesetzt", adapter.getMode() == adapter.MISSION_MODE);
		adapter.stopGame();
		
		adapter.setMissionsMode(false);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Modus nicht richtig gesetzt", adapter.getMode() == adapter.DOMINATION_MODE);
		
		
	}

}
