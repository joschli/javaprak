package src.de.tud.gdi.risk.tests.adapter;

import java.io.IOException;

import src.de.tud.gdi1.risk.controller.GameController;
import src.de.tud.gdi1.risk.model.Options;

public class RiskTestAdapterExtended1 extends RiskTestAdapterMinimal {

	
	public static final int STARTING_PHASE = 3;
	
	@Override
	public void initializeGame() throws IOException {
		//Options.getInstance().setMissions(true);
		view = new TestGameplayState(0);
		risk = new GameController(view);
		}

	public void setPlayerCount(int i) {
		Options.getInstance().setPlayerCount(i);
	}

}
