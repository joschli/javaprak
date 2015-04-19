package src.de.tud.gdi1.risk.controller;

public class ErrorPrinter {

	//Reinforcement Error
	public static final String CANTREINFORCEENEMYCOUNTRIESERROR = "Fehler: Gegnerische Länder können nicht verstärkt werden";

	//Move Troops Error
	public final String ZEROTROOPSLEFTERROR = "Fehler: Es können nicht 0 Einheiten zurückgelassen werden";
	public final String NOTENOUGHTROOPSMOVEERROR  = "Fehler: Zu wenig Truppen zum Verschieben";
	public final String OWNERMOVEERROR ="Fehler: Truppenbewegungen nicht möglich, da die Besitzer der Länder unterschiedlich sind";
	
	//Not Neighbor Error
	public final String NOTNEIGHBORERROR = "Fehler: Die ausgewählten Länder sind nicht benachbart";
	
	//General Wrong Phase Error
	public final String PHASEERROR = "Fehler: Aktion in der momentanen Phase nicht möglich";
	
	//attack Errors
	public final String OWNERATTACKERROR = "Fehler: Eigene Länder können nicht angegriffen werden";
	public final String NOTENOUGHTROOPSATTACKERROR = "Fehler: Zu wenige Truppen, um soviele Würfel zum Angreifen zu verwenden";
	public final String NOTENOUGHTROOPSDEFENDERROR = " Fehler: Länder mit 0 Einheiten können nicht angegriffen werden";
	
	//Card Errors
	public final String OWNERCARDERROR = "Fehler: Besitzer der einzutauschenden Karten ist nicht der momentane Spieler";
	public final String CARDERROR = "Fehler: Mehr oder weniger als 3 Karten können nicht eingetauscht werden";
	public final String CARDVALUEERROR = "Fehler: Kartenwert nicht zwischen 1 und 3";
	public static final String INCORRECTCARDSETERROR = "Fehler: Es können nur drei gleiche oder drei unterschiedliche Karten eingetauscht werden";
	
	public ErrorPrinter()
	{
		
	}
	
	public void printError(String s)
	{
		System.out.println(s);
	}
}
