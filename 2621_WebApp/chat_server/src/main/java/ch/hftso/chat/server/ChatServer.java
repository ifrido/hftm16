package ch.hftso.chat.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import ch.hftso.chat.interfaces.Chat;

/**
 * Main ServerKlasse. Diese erzeugt ein Remote-Objekt und registriert es mit dem
 * lokalen Registry Service.
 * 
 * @author Bau 30.11.2012
 */
public class ChatServer {
	/**
	 * Startet den Server als stand-alone Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new ChatServer();
	}

	/**
	 * Erzeugt ein Remote-Objekt und registriert es in der lokalen RMI-Registry.
	 */
	public ChatServer() {
		try {
			Chat server = new ChatImpl();
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			props.put(Context.PROVIDER_URL, "rmi://localhost:1099");
			Context context = new InitialContext(props);
			Chat stub = (Chat) UnicastRemoteObject.exportObject(server, 0);
			LocateRegistry.createRegistry(1099);
			context.rebind(Chat.SERVER_NAME, stub);
			//Chat stub = (Chat) context.lookup(Chat.SERVER_NAME);
			// Erzeugt das Remote-Objekt
			
			// Registriert das Remote-Objekt
			// Anstelle von bind wird rebind benutzt.
			// Dies ist nützlich bei einem Restart des Servers,
			// er überschreibt damit die alte Bindung.
			// Die Registry muss gestartet sein!
			//Chat stub = (Chat) UnicastRemoteObject.exportObject(server, 0);
			//Registry registry = LocateRegistry.getRegistry(1099);
			//registry.rebind(Chat.SERVER_NAME, stub);

			System.out.println("Der Server ist gestartet und registriert");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
