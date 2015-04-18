package src.de.tud.gdi1.risk.ui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import src.de.tud.gdi1.risk.model.Player;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Timo Bähr
 *
 * Diese Klasse startet das Spiel "Drop of Water". Es enthaelt
 * zwei State's für das Menue und das eigentliche Spiel.
 */
public class Launch extends StateBasedGame {
	
	// Jeder State wird durch einen Integer-Wert gekennzeichnet
    public static final int MAINMENU_STATE = 0;
    public static final int GAMEPLAY_STATE = 1;
    public static final int WIN_STATE = 2;
    public static final int OPTIONS_STATE = 3;
    public static final int PAUSE_STATE = 4;
    public static final int CARD_STATE = 5;
    
    public Launch()
    {
        super("Risk");

    }
 
    public static void main(String[] args) throws SlickException
    {
    	// Setze den library Pfad abhaengig vom Betriebssystem
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        AppGameContainer app = new AppGameContainer(new Launch());
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        // (nicht aber im Vollbildmodus)
        app.setDisplayMode(1028, 600, false);
        app.setShowFPS(false);
        app.setIcon("assets/icon.png");
        app.setTargetFrameRate(200);
        app.start();
    }

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// Fuege dem StateBasedGame die States hinzu 
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
		addState(new MainMenuState(MAINMENU_STATE));
        addState(new GameplayState(GAMEPLAY_STATE));
        addState(new WinState(WIN_STATE));
        addState(new OptionState(OPTIONS_STATE));
        addState(new PauseState(PAUSE_STATE));
        addState(new CardState(CARD_STATE));
        
        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEPLAY_STATE);
        StateBasedEntityManager.getInstance().addState(WIN_STATE);
        StateBasedEntityManager.getInstance().addState(OPTIONS_STATE);
        StateBasedEntityManager.getInstance().addState(PAUSE_STATE);
        StateBasedEntityManager.getInstance().addState(CARD_STATE);
		
	}
}
