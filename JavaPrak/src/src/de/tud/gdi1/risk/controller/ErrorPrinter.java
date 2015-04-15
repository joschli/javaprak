package src.de.tud.gdi1.risk.controller;

public class ErrorPrinter {

	public final String NOTENOUGHTROOPSMOVEERROR  = "Fehler: Zu wenig Truppen zum Verschieben";
	public final String NOTNEIGHBORERROR = "Fehler: Die ausgew�hlten L�nder sind nicht benachbart";
	public final String OWNERMOVEERROR ="Fehler: Truppenbewegungen nicht m�glich, da die Besitzer der L�nder unterschiedlich sind";
	public final String PHASEERROR = "Fehler: Aktion in der momentanen Phase nicht m�glich";
	public final String OWNERATTACKERROR = "Fehler: Eigene L�nder k�nnen nicht angegriffen werden";
	public final String NOTENOUGHTROOPSATTACKERROR = "Fehler: Zu wenige Truppen, um soviele W�rfel zum Angreifen zu verwenden";
	public final String NOTENOUGHTROOPSDEFENDERROR = " Fehler: L�nder mit 0 Einheiten k�nnen nicht angegriffen werden";
	
	public ErrorPrinter()
	{
		
	}
	
	public void printError(String s)
	{
		System.out.println(s);
	}
}
