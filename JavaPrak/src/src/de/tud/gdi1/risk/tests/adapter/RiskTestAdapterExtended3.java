package src.de.tud.gdi1.risk.tests.adapter;

import src.de.tud.gdi1.risk.model.Mission;
import src.de.tud.gdi1.risk.model.Options;
import src.de.tud.gdi1.risk.model.entities.Country;

public class RiskTestAdapterExtended3 extends RiskTestAdapterExtended2 {

	
	public final int DOMINATION_MODE = 0;
	public final int MISSION_MODE = 1;

	/**
	 * returns the missiontext for Player p
	 * @param p index of the player
	 * @return
	 */
	public String getMissionForPlayer(int p) {
		return risk.getMap().getPlayer(p).getMissionText();
	}

	/**
	 * gets the number of missions generated at the beginning of the game
	 * @return
	 */
	public int getNumberOfMissions() {
		return risk.getMap().getNumberOfMissions();
	}

	/**
	 * Sets the mission of player p
	 * to conquer i countries 
	 * @param i
	 * @param p
	 */
	public void setCountryMissionForPlayer(int i, int p) {
		risk.getMap().getPlayer(p).assignMission(new Mission("",24));
	}

	/**
	 * this should simulate player p having i countries
	 * by changing the owner
	 * @param i
	 * @param p
	 */
	public void fulfillCountryMissionForPlayer(int i, int p) {
		int countryCount =risk.getMap().getOwnedCountriesForPlayer(p);
		for(Country c: risk.getMap().getCountries())
		{
			if(!c.isOwner(risk.getMap().getPlayer(p)))
			{
				c.setOwner(risk.getMap().getPlayer(p));
				countryCount++;
			}
			if(countryCount == i)
				break;
		}
	}


/**
 * sets the mode of the game to mission mode if b = true
 * @param b
 */
	public void setMissionsMode(boolean b) {
		Options.getInstance().setMissions(b);
	}
	
	/**
	 * returns the mode of the game. 
	 * @return
	 */
	public int getMode() {
		if(Options.getInstance().getMissions())
		{
			return MISSION_MODE;
		}
		else
		{
			return DOMINATION_MODE;
		}
	}

	/**
	 * sets a defeat mission for player p2 to defeat p1
	 * @param p1 player to be defeated
	 * @param p2 player who gets the mission
	 */
	public void setDefeatMissionForPlayer(int p1, int p2) {
		risk.getMap().getPlayer(p2).assignMission(new Mission("",risk.getMap().getPlayer(p1),24));
	}

	

}
