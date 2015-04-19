package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.ui.CardState;
import src.de.tud.gdi1.risk.ui.GameplayState;
import src.de.tud.gdi1.risk.ui.Launch;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class TradeInCardsAction implements Action{

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof CardState)
		{
			System.out.println("Trade In");
			CardState state = (CardState) sb.getCurrentState();
			boolean usable = true;
			if(event.getOwnerEntity() instanceof UIButton)
			{
				UIButton button = (UIButton) event.getOwnerEntity();
				usable = button.getUsability();
			}
			if(event.getOwnerEntity().isVisible() && usable){
				GameplayState gamePlayState = (GameplayState) sb.getState(Launch.GAMEPLAY_STATE);
				gamePlayState.setTradeIn(state.getTradeIn());
				sb.enterState(Launch.GAMEPLAY_STATE);
			}
		}
	}

}
