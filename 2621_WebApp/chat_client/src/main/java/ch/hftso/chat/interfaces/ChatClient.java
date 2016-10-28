package ch.hftso.chat.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote-Interface für das Remote-Objekt des Client.
 * Es besitzt eine Methode, die von aussen aufgerufen werden kann.
 * @author Bau 30.11.2012
 */
public interface ChatClient extends Remote {

  /**
   * Übergibt die Meldung an den Client.
   * @param  message Meldung an den Client
   * @exception RemoteException falls Fehler auftreten
   */
  public void print(String message) throws RemoteException;
}
