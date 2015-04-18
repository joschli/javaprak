package src.de.tud.gd1.risk.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.model.entities.Card;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.CardState;
import src.de.tud.gdi1.risk.ui.GameplayState;
import src.de.tud.gdi1.risk.ui.UIButton;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class SelectAction implements Action {

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		if(sb.getCurrentState() instanceof GameplayState || sb.getCurrentState() instanceof CardState)
		{
			if(sb.getCurrentState() instanceof GameplayState){
				GameplayState state = ((GameplayState) sb.getCurrentState());
				if(event.getOwnerEntity() instanceof Country)
					state.selectCountry((Country) event.getOwnerEntity());
			}
			else {
				CardState state = (CardState) sb.getCurrentState();
				if(event.getOwnerEntity() instanceof Card)
					state.selectCard((Card) event.getOwnerEntity());
			}
		}
		System.out.println("SelectAction");
	}

}
