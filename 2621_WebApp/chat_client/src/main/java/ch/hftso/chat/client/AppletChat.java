package ch.hftso.chat.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.InitialContext;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ch.hftso.chat.interfaces.ChatClient;
import ch.hftso.chat.interfaces.Chat;

/**
 * Oberfläche des Clients.
 * Der SecurityManager wird installiert.
 * Der Client exportiert ein Remote-Objekt, damit er vom
 * Server angesprochen werden kann.
 * @author Bau 30.11.2012
 */
public class AppletChat extends JApplet implements ChatClient {
  private static final long serialVersionUID = -981011262048596283L;
  private BorderLayout layout = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JPanel jPanel3 = new JPanel();
  private JButton jBtnRegistrieren = new JButton();
  private JTextField jTFRegistrieren = new JTextField();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea jTAMessages = new JTextArea();
  private JPanel jPanel4 = new JPanel();
  private JButton jBtnSenden = new JButton();
  private JTextField jTFSenden = new JTextField();
  // Verbindung zum Server
  private Chat server;
  private ChatClient stub;
  // Client registriert
  private boolean register = false;

  /** Creates new form JApplet */
  public AppletChat() {
  }

  /** This method is called from Browser. */
  public void init() {
    jBtnSenden.setText("Senden");
    getContentPane().setLayout(layout);
    JPanel content = new JPanel();
    content.setPreferredSize(new Dimension(300, 200));
    content.setLayout(new java.awt.BorderLayout());
    content.add(jPanel1, java.awt.BorderLayout.CENTER);
    getContentPane().add(content, BorderLayout.CENTER);
    // set title
    // setTitle("Chat");
    // add menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("Datei");
    menuFile.setMnemonic('D');
    // create Exit menu item
    JMenuItem fileExit = new JMenuItem("Ende");
    fileExit.setMnemonic('E');
    fileExit.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
    menuFile.add(fileExit);
    menuBar.add(menuFile);
    // sets menu bar
    setJMenuBar(menuBar);
    /*addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent evt) {
            exitForm(evt);
          }
        });*/
    jPanel1.setLayout(new java.awt.BorderLayout());
    jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
    jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel2.setPreferredSize(new java.awt.Dimension(10, 40));
    jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel2.add(jBtnRegistrieren);
    jPanel2.add(jTFRegistrieren);
    jPanel3.setLayout(new java.awt.BorderLayout());
    jPanel3.add(jPanel4, java.awt.BorderLayout.SOUTH);
    jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jBtnRegistrieren.setText("Registrieren");
    jTFRegistrieren.setText("");
    jTFRegistrieren.setPreferredSize(new java.awt.Dimension(93, 27));
    jTAMessages.setText("");
    jTAMessages.setLineWrap(true);
    jTAMessages.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jTAMessages.setEditable(false);
    jScrollPane1.getViewport().add(jTAMessages);
    jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel4.setPreferredSize(new java.awt.Dimension(14, 40));
    jPanel4.add(jBtnSenden);
    jPanel4.add(jTFSenden);
    jTFSenden.setText("");
    jTFSenden.setPreferredSize(new java.awt.Dimension(200, 27));
    jBtnRegistrieren.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            jBtnRegistrierenActionPerformed(e);
          }
        });
    jBtnSenden.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            jBtnSendenActionPerformed(e);
          }
        });
    // Remote-Objekt exportieren
    try {
      stub = (ChatClient) UnicastRemoteObject.exportObject(this, 0);
    }
    catch(Exception ex) {
      JOptionPane.showMessageDialog(this,ex.toString());
    }
  }

  /** Exit the Application */
  //private void exitForm(WindowEvent evt) {
  //  System.exit(0);
  //}

  /**
   * Registrierungs-Button wird angeklickt, die Registrierung
   * wird an den Server geschickt.
   */
  public void jBtnRegistrierenActionPerformed(ActionEvent e) {
    String name = jTFRegistrieren.getText();
    if(name.equals("")) {
      JOptionPane.showMessageDialog(this,
        "Geben Sie einen eindeutigen Namen ein!");
      return;
    }
  // RMI-Teil
    // URL auf den Remote-Server erstellen
    String url = "rmi://" + getCodeBase().getHost() + ":1099" + "/"
    + Chat.SERVER_NAME;
    try {
      // Das Remote-Objekt lokalisieren
      server = (Chat)new InitialContext().lookup(url);
      int err = server.register(name,stub);
      if(err == Chat.FAILURE) {
        throw new Exception("Dieser Name ist bereits registriert, " +
          "wählen Sie einen anderen Namen!");
      }
      // ein zweites Registrieren verhindern
      jBtnRegistrieren.setEnabled(false);
      register = true;
    }
    catch(Exception ex) {
      JOptionPane.showMessageDialog(this,ex.toString());
    }
  }

  /**
   * Senden-Button wird angeklickt, die Message
   * wird an den Server geschickt.
   */
  public void jBtnSendenActionPerformed(ActionEvent e) {
    if(!register) {
      JOptionPane.showMessageDialog(this,
        "Sie müssen sich zuerst registrieren!");
      return;
    }
    String name = jTFRegistrieren.getText();
    try {
      server.send(name,jTFSenden.getText());
      jTFSenden.setText("");
    }
    catch(Exception ex) {
      JOptionPane.showMessageDialog(this,ex.toString());
    }
  }

  /**
   * Übergibt die Meldung an den Client.
   * @param  message Meldung an den Client
   * @exception RemoteException falls Fehler auftreten
   */
  public void print(String message) throws RemoteException {
    jTAMessages.append(message + "\n");
  }
}
