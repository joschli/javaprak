package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.ui.GameplayState;
import src.de.tud.gdi1.risk.ui.Launch;
import src.de.tud.gdi1.risk.ui.OptionState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class ShowCardAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof GameplayState)
		{
			System.out.println("Start Game");
			GameplayState state = (GameplayState) sb.getCurrentState();
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				state.showCards();
				//sb.enterState(Launch.CARD_STATE);
			}
		}
	}

}