package src.de.tud.gdi1.risk.controller;

public class ErrorPrinter {

	public final String OWNERCARDERROR = "Fehler: Besitzer der einzutauschenden Karten ist nicht der momentane Spieler";
	public final String NOTENOUGHTROOPSMOVEERROR  = "Fehler: Zu wenig Truppen zum Verschieben";
	public final String NOTNEIGHBORERROR = "Fehler: Die ausgewählten Länder sind nicht benachbart";
	public final String OWNERMOVEERROR ="Fehler: Truppenbewegungen nicht möglich, da die Besitzer der Länder unterschiedlich sind";
	public final String PHASEERROR = "Fehler: Aktion in der momentanen Phase nicht möglich";
	public final String OWNERATTACKERROR = "Fehler: Eigene Länder können nicht angegriffen werden";
	public final String NOTENOUGHTROOPSATTACKERROR = "Fehler: Zu wenige Truppen, um soviele Würfel zum Angreifen zu verwenden";
	public final String NOTENOUGHTROOPSDEFENDERROR = " Fehler: Länder mit 0 Einheiten können nicht angegriffen werden";
	public final String CARDERROR = "Fehler: Mehr oder weniger als 3 Karten können nicht eingetauscht werden";
	public final String CARDVALUEERROR = "Fehler: Kartenwert nicht zwischen 1 und 3";

	public ErrorPrinter()
	{
		
	}
	
	public void printError(String s)
	{
		System.out.println(s);
	}
}
