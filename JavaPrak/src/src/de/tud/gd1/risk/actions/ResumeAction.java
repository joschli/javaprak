package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;


import src.de.tud.gdi1.risk.ui.Launch;
import src.de.tud.gdi1.risk.ui.MainMenuState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * This action gets called when the resumeButton is pressed for entering the GamePlayState.
 * It only works if the button is enabled.
 */
public class ResumeAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof MainMenuState)
		{
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				sb.enterState(Launch.GAMEPLAY_STATE);
			}
		}
	}

}
