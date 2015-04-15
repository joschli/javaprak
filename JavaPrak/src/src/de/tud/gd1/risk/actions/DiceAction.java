package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.ui.GameplayState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;

public class DiceAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof GameplayState)
		{
			GameplayState state = (GameplayState) sb.getCurrentState();
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				state.rollDices();
			}
		}
	}

}
