package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import src.de.tud.gdi1.risk.ui.OptionState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * This action starts a new game when the newGameButton is pressed.
 * It only works if the button is enabled.
 *
 */
public class StartGameAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof OptionState)
		{
			System.out.println("Start Game");
			OptionState state = (OptionState) sb.getCurrentState();
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				state.startNewGame();
			}
		}
	}

}
