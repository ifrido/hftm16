package ch.hftso.chat.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote-Interface für das Remote-Objekt des Servers.
 * Es besitzt zwei Methoden, die von aussen aufgerufen werden können
 * und zwei Konstanten, die angeben, ob sich ein Client erfolgreich
 * registrieren konnte.
 * @author Bau 30.11.2012
 */
public interface Chat extends Remote {
  public static final String SERVER_NAME = "Message_Server";
  public static final int FAILURE = -1;
  public static final int SUCCESS = 0;

  /**
   * Übernimmt den Namen des Clients und die Meldung, die an
   * alle registrierten Clients gesendet wird.
   * @param  name Name des Clients
   * @param  message Meldung an die übrigen Clients
   * @exception RemoteException falls Fehler auftreten
   */
  public void send(String name,String message) throws RemoteException;

  /**
   * Übernimmt den Namen des Clients und dessen Remote-Objekt
   * und registriert den Client.
   * @param  name Name des Clients
   * @param  mObject Remote-Objekt für die Meldung
   * @exception RemoteException falls Fehler auftreten
   */
  public int register(String name, ChatClient client) throws RemoteException;
}
