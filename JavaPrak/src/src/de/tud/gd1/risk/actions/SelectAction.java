package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import src.de.tud.gdi1.risk.ui.SuperBasicGameState;

import eea.engine.action.Action;
import eea.engine.component.Component;

public class SelectAction implements Action {

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof SuperBasicGameState)
		{
			SuperBasicGameState state = (SuperBasicGameState) sb.getCurrentState();
			state.selectAction(event.getOwnerEntity());
		}
		System.out.println("SelectAction");
	}

}
