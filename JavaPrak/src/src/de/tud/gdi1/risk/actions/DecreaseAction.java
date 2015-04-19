package src.de.tud.gdi1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;


import src.de.tud.gdi1.risk.ui.UIButton;
import src.de.tud.gdi1.risk.ui.UICounter;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * Action called by the decreaseButton of a UICounter to decrease the Counters value.
 * @see UICounter.decreaseCount()
 *
 */

public class DecreaseAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(event.getOwnerEntity() instanceof UIButton)
		{
			UIButton counterButton = (UIButton) event.getOwnerEntity();
			if(counterButton.getOwner() instanceof UICounter)
			{
				UICounter counter = (UICounter) counterButton.getOwner();
				counter.decreaseCount();
			}
			
		}
	}

}
