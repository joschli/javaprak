package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class NextPhaseAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof GameplayState)
		{
			System.out.println("Next Phase");
			GameplayState state = (GameplayState) sb.getCurrentState();
			if(event.getOwnerEntity().isVisible())
			{
				state.gotoNextPhase();
			}
		}
	}

}
