package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import src.de.tud.gdi1.risk.ui.SuperBasicGameState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * Action that is used for canceling the current state / window
 * It only works if the button is enabled.
 */
public class CancelAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof SuperBasicGameState)
		{
			SuperBasicGameState state = (SuperBasicGameState) sb.getCurrentState();
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				state.cancelAction(sb);
			}
		}
	}

}
