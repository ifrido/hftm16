package ch.hftso.uhrensystem.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import ch.hftso.uhrensystem.interfaces.Beobachter;
import ch.hftso.uhrensystem.interfaces.Uhr;



/**
 * Die von "Uhr" implementierte Klasse "UhrImpl" erfasst
 * über einen Timer die aktuelle Zeit und meldet
 * die Wertänderung den angemeldeten Beobachtern.
 * 
 * @author Bau / letzte Änderung: 14.10.2009
 */
public class UhrImpl implements Uhr {
  private List<Beobachter> beobachter = new ArrayList<Beobachter>();

  private Timer timer;
  private int sekunden;
  private int minuten;
  private int stunden;

   /**
    * Der Konstruktor erzeugt den Timer zur Erfassung der Zeit
    * und initialisiert und startet ihn.
    */
  public UhrImpl() {
    super();
    timer = new Timer(1000,new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        zeitGeber();
      }
    });
    timer.start();
  }

  /**
   * Ermöglicht die Aufnahme eines Beobachters.
   * @param observer: Beobachter-Objekt
   */
  public void attach(Beobachter b) {
    beobachter.add(b);
  }

  /**
   * Eliminiert einen bestimmten Beobachter
   * aus der Liste der aktuellen Beobachter.
   * @param observer: Beobachter-Objekt
   */
  public void detach(Beobachter b) {
    beobachter.remove(b);
  }

  /**
   * Meldet den angemeldeten Beobachtern,
   * dass sich der Status des Subjekts verändert hat.
   */
  public void inform() {
    Iterator<Beobachter> iter = beobachter.iterator();
    try {
      while (iter.hasNext()) {
        iter.next().update();
      }
    }
    catch(Exception e) {
      System.out.println("Fehler: " + e.toString());
    }
  }

  /**
   * Ermittelt die Stunden.
   * @return Anzahl Stunden
   */
  public int getSekunden() {
    return sekunden;
  }

  /**
   * Ermittelt die Minuten.
   * @return Anzahl Minuten
   */
  public int getMinuten() {
    return minuten;
  }

  /**
   * Ermittelt die Sekunden.
   * @return Anzahl Sekunden
   */
  public int getStunden() {
    return stunden;
  }

  /**
   * Ermittelt die aktuelle Zeit.
   */
  private void zeitGeber() {
    GregorianCalendar datum = new GregorianCalendar();
    stunden = datum.get(Calendar.HOUR_OF_DAY);
    minuten = datum.get(Calendar.MINUTE);
    sekunden = datum.get(Calendar.SECOND);
    inform();
  }
}
