package ch.hftso.uhrensystem.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.hftso.uhrensystem.interfaces.Beobachter;
import ch.hftso.uhrensystem.interfaces.Uhr;

/**
 * Die von "Beobachter" implementierte Klasse "BeobachterDigital" merkt sich den
 * Status, der mit jenem des assoziierten Subjekts konsistent sein soll.
 * 
 * @author Bau / letzte Änderung: 14.10.2009
 */
public class BeobachterDigital extends JFrame implements Beobachter {
	private static final long serialVersionUID = -7486982314390178171L;
	private Beobachter stub = null;
	private Uhr lnkUhr;

	private BorderLayout layout = new BorderLayout();
	private JPanel jPanel1 = new JPanel();
	private JTextField jTextField1 = new JTextField();

	/**
	 * Die Oberfläche entsprechend implementieren.
	 */
	public BeobachterDigital() {
		try {
			// Lokalisieren des Remote-Objekts
			ResourceBundle bundle = ResourceBundle.getBundle("host");
			Registry registry = LocateRegistry.getRegistry(bundle.getString("HOSTNAME"), 1099);
			lnkUhr = (Uhr) registry.lookup(Uhr.NAME);
		} catch (Exception e) {
			System.out.println("Das Objekt konnte nicht erzeugt werden");
			e.printStackTrace(System.err);
		}
		//lnkUhr = server;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		frameSize.height = ((frameSize.height > screenSize.height) ? screenSize.height : frameSize.height);
		frameSize.width = ((frameSize.width > screenSize.width) ? screenSize.width : frameSize.width);
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 4);
		setVisible(true);
		initGUI();
		pack();
		try {
			stub = (Beobachter) UnicastRemoteObject.exportObject(this.stub, 0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initGUI() {
		getContentPane().setLayout(layout);
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(250, 100));
		content.setLayout(new java.awt.BorderLayout());
		content.add(jPanel1, java.awt.BorderLayout.CENTER);
		getContentPane().add(content, BorderLayout.CENTER);
		// set title
		setTitle("Digitaluhr");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});
		jPanel1.setLayout(new java.awt.BorderLayout());
		jPanel1.add(jTextField1, java.awt.BorderLayout.CENTER);
		jTextField1.setText("");
		jTextField1.setFont(new java.awt.Font("Courier New", java.awt.Font.BOLD, 48));
		jTextField1.setEditable(false);
		// anmelden
		try {
			lnkUhr.attach(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Exit the Application */
	private void exitForm(WindowEvent evt) {
		try {
			lnkUhr.detach(this);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		System.exit(0);
	}

	public void update() {
		// Write your code here
		String s, m, h;
		try {
			s = Integer.toString(lnkUhr.getSekunden());
			if (s.length() == 1)
				s = "0" + s;
			m = Integer.toString(lnkUhr.getMinuten());
			if (m.length() == 1)
				m = "0" + m;
			h = Integer.toString(lnkUhr.getStunden());
			if (h.length() == 1)
				h = "0" + h;
			jTextField1.setText(h + ":" + m + ":" + s);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public static void main(String[] argv) throws Exception {
		new BeobachterDigital();
	}
}
