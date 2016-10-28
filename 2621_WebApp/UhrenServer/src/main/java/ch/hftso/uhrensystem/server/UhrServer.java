package ch.hftso.uhrensystem.server;



import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hftso.uhrensystem.interfaces.Uhr;

/**
 * Server Klasse.
 * 
 * @author Bau / letzte Änderung: 14.10.2009
 */
public class UhrServer {
	/**
	 * Erzeugt das Remote-Objekt und registriert es in der RMI-Registry.
	 */
	public UhrServer() {
		try {
			Uhr server = new UhrImpl();
			Uhr stub = (Uhr) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(Uhr.NAME, stub);
			System.out.println("Server started and registered");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Startet den Server als stand-alone Applikation.
	 * 
	 * @param args:
	 *            Array der Übergabe-Parameter
	 */
	public static void main(String[] argv) throws Exception {
		new UhrServer();
	}
}
