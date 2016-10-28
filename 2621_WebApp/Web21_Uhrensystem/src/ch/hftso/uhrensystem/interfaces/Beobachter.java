package ch.hftso.uhrensystem.interfaces;

import java.rmi.Remote;

/**
 * Schnittstelle (Vetrag) für Server und Client.
 * Sie dient für konkrete Beobachter.
 * 
 * @author Bau / letzte Änderung: 14.10.2009
 */
public interface Beobachter extends Remote {

  /**
   * Veranlasst von einem Beobachter, seinen Status
   * vom Subjekt zu aktualisieren.
   */
  public void update();
}
