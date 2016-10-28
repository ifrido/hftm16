package ch.hftso.chat.server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ch.hftso.chat.interfaces.Chat;
import ch.hftso.chat.interfaces.ChatClient;

/**
 * Remote-Objekt Implementation.
 * @author Bau 30.11.2012
 */
public class ChatImpl implements Chat {

  /**
   * Container zum Registrieren der Clients
   */
  private Map<String,ChatClient> receivers = new HashMap<String,ChatClient>();

  public ChatImpl() {
  }

  /**
   * Übernimmt den Namen des Clients und die Meldung, die an
   * alle registrierten Clients gesendet wird.
   * @param  name Name des Clients
   * @param  message Meldung an die übrigen Clients
   * @exception RemoteException falls Fehler auftreten
   */
  public void send(String name, String message) throws RemoteException {
    String key = null;
    // An alle registrierten Clients die Meldung schicken
    Iterator<String> keys = receivers.keySet().iterator();
    while(keys.hasNext()) {
      key = (String)keys.next();
      ChatClient client = receivers.get(key);
      try {
        client.print(name + ": " + message);
      }
      catch(Exception e) {
        System.out.println(key + " hat sich abgemeldet!");
        keys.remove();
      }
    }
  }

  /**
   * Übernimmt den Namen des Clients und dessen Remote-Objekt
   * und registriert den Client.
   * @param  name Name des Clients
   * @param  client Remote-Objekt für die Meldung
   * @exception RemoteException falls Fehler auftreten
   */
  public int register(String name, ChatClient client)
    throws RemoteException {
    int retval = 0;
    // Der Client darf nicht null sein
    if((name != null) && (client != null)) {
      // Der Name des Clients muss unique sein
      if(receivers.get(name) == null) {
        receivers.put(name, client);
        System.out.println(name + " registriert");
        retval = Chat.SUCCESS;
      }
      else {
        System.out.println("Der Client konnte nicht registriert " +
          "werden, weil der Name '" + name + "' bereits registriert ist!");
        retval = Chat.FAILURE;
      }
    }
    return retval;
  }
}
