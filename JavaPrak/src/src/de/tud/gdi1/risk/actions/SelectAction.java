package src.de.tud.gdi1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import src.de.tud.gdi1.risk.ui.SuperBasicGameState;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * This action is called when an Entity that can be selected is clicked. 
 * It calls the selectAction() function of the current state.
 * 
 */
public class SelectAction implements Action {

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof SuperBasicGameState)
		{
			SuperBasicGameState state = (SuperBasicGameState) sb.getCurrentState();
			state.selectAction(event.getOwnerEntity());
		}
	}

}
