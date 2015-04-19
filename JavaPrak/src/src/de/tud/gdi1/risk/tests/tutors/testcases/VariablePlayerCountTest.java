package src.de.tud.gdi1.risk.tests.tutors.testcases;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.de.tud.gdi1.risk.tests.adapter.RiskTestAdapterExtended1;

public class VariablePlayerCountTest {

	
	
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
	public void variablePlayerCountTest() {
		adapter.setPlayerCount(2);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue("Spiel wird nicht richtig mit zwei Spielern initialisiert",adapter.getPlayerCount() == 2);
		adapter.stopGame();
		adapter.setPlayerCount(4);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		assertTrue("Spiel wird nicht richtig mit vier Spielern initialisiert",adapter.getPlayerCount() == 4);
		assertTrue("Anzahl Spieler entspricht nicht den erstellten Spielern",adapter.getPlayerCount() == adapter.getPlayerNames().size());
		adapter.stopGame();
		adapter.setPlayerCount(6);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue("Spiel wird nicht richtig mit sechs Spielern initialisiert",adapter.getPlayerCount() == 6);
		assertTrue("Anzahl Spieler entspricht nicht den erstellten Spielern",adapter.getPlayerCount() == adapter.getPlayerNames().size());
		
		adapter.stopGame();
		
		adapter.setPlayerCount(3);
		try {
			adapter.initializeGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("Spiel wird nicht richtig mit vier Spielern initialisiert bei Verringern der Spieleranzahl",adapter.getPlayerCount() == 3);
		assertTrue("Anzahl Spieler entspricht nicht den erstellten Spielern bei Verringern ",adapter.getPlayerCount() == adapter.getPlayerNames().size());
		
	}
}
