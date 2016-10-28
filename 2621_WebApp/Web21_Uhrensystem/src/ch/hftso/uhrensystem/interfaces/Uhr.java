package ch.hftso.uhrensystem.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Schnittstelle (Vetrag) für Server und Client. Sie dient für konkrete
 * Subjekte.
 * 
 * @author Bau / letzte �nderung: 14.10.2009
 */
public interface Uhr extends Remote {
	
	public static String NAME = "uhr";

	/**
	 * Ermöglicht die Aufnahme eines Beobachters.
	 * 
	 * @param b:
	 *            Beobachter-Objekt
	 */
	public void attach(Beobachter b) throws RemoteException;

	/**
	 * Eliminiert einen bestimmten Beobachter aus der Liste der aktuellen
	 * Beobachter.
	 * 
	 * @param b:
	 *            Beobachter-Objekt
	 */
	public void detach(Beobachter b) throws RemoteException;

	/**
	 * Meldet den angemeldeten Beobachtern, dass sich der Status des Subjekts
	 * verändert hat.
	 */
	public void inform() throws RemoteException;

	/**
	 * Ermittelt die Stunden.
	 * 
	 * @return Anzahl Stunden
	 */
	public int getStunden() throws RemoteException;

	/**
	 * Ermittelt die Minuten.
	 * 
	 * @return Anzahl Minuten
	 */
	public int getMinuten() throws RemoteException;

	/**
	 * Ermittelt die Sekunden.
	 * 
	 * @return Anzahl Sekunden
	 */
	public int getSekunden() throws RemoteException;
}
