package src.de.tud.gdi1.risk.controller;

public class ErrorPrinter {

	//Reinforcement Error
	public static final String CANTREINFORCEENEMYCOUNTRIESERROR = "Fehler: Gegnerische L�nder k�nnen nicht verst�rkt werden";

	//Move Troops Error
	public final String ZEROTROOPSLEFTERROR = "Fehler: Es k�nnen nicht 0 Einheiten zur�ckgelassen werden";
	public final String NOTENOUGHTROOPSMOVEERROR  = "Fehler: Zu wenig Truppen zum Verschieben";
	public final String OWNERMOVEERROR ="Fehler: Truppenbewegungen nicht m�glich, da die Besitzer der L�nder unterschiedlich sind";
	
	//Not Neighbor Error
	public final String NOTNEIGHBORERROR = "Fehler: Die ausgew�hlten L�nder sind nicht benachbart";
	
	//General Wrong Phase Error
	public final String PHASEERROR = "Fehler: Aktion in der momentanen Phase nicht m�glich";
	
	//attack Errors
	public final String OWNERATTACKERROR = "Fehler: Eigene L�nder k�nnen nicht angegriffen werden";
	public final String NOTENOUGHTROOPSATTACKERROR = "Fehler: Zu wenige Truppen, um soviele W�rfel zum Angreifen zu verwenden";
	public final String NOTENOUGHTROOPSDEFENDERROR = " Fehler: L�nder mit 0 Einheiten k�nnen nicht angegriffen werden";
	
	//Card Errors
	public final String OWNERCARDERROR = "Fehler: Besitzer der einzutauschenden Karten ist nicht der momentane Spieler";
	public final String CARDERROR = "Fehler: Mehr oder weniger als 3 Karten k�nnen nicht eingetauscht werden";
	public final String CARDVALUEERROR = "Fehler: Kartenwert nicht zwischen 1 und 3";
	public static final String INCORRECTCARDSETERROR = "Fehler: Es k�nnen nur drei gleiche oder drei unterschiedliche Karten eingetauscht werden";
	
	public ErrorPrinter()
	{
		
	}
	
	public void printError(String s)
	{
		System.out.println(s);
	}
}
