package src.de.tud.gdi.risk.tests.adapter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import src.de.tud.gdi1.risk.model.Player;
import src.de.tud.gdi1.risk.model.entities.Card;
import src.de.tud.gdi1.risk.model.entities.Country;
import src.de.tud.gdi1.risk.ui.GameplayState;

public class TestGameplayState extends GameplayState {

	public TestGameplayState(int sid) {
		super(sid);
	
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
	
	}

	@Override
	public void updateUserInterface() {
	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

	}

	@Override
	public int getID() {
		return 0;
	}


	@Override
	public void endTurnButtonPressed() {
		
	}

	@Override
	public void AttackEvent() {
		
	}

	@Override
	public void requestTroopMovement(int minCount, int maxCount) {
		
	}



	@Override
	public void selectAction(Entity entity) {
		
	}

	@Override
	public void cancelAction(StateBasedGame game) {
		
	}

	@Override
	public void rollDices() {
		
	}

	@Override
	public void showDiceResult(int[] attackDices, int[] defenseDices,
			boolean countryConquered) {
	
	}

	@Override
	public void adjustCounter() {
		
	}

	@Override
	public void updateSelection(Country country) {
		
	}

	@Override
	public Entity getFirstCountrySelected() {
		return null;
	}

	@Override
	public Country[] getSelectedCountries() {
	
		return null;
	}

	@Override
	public void showAttackWindow() {
		
	}

	@Override
	public void hideAttackWindow() {
		
	}

	@Override
	public boolean getCountriesSelected() {
		return false;
	}

	@Override
	public boolean isAttackWindowVisible() {
		return false;
	}

	@Override
	public void reset() {
	
	}

	@Override
	public int getDiceCount() {
		return 0;
	}

	@Override
	public void fortifyCountry() {

	}

	@Override
	public void gotoNextPhase() {

	}

	@Override
	public void startFortify() {

	}

	@Override
	public void showMissionText() {

	}

	@Override
	public void hideMissionText() {

	}

	@Override
	public boolean isMissionTextVisibible() {
		return false;
	}

	@Override
	public void showCards() {
	
	}

	@Override
	public void setTradeIn(Card[] tradeIn) {
		
	}

	@Override
	public void playerDefeated(Player defendingPlayer) {
		
	}

}
