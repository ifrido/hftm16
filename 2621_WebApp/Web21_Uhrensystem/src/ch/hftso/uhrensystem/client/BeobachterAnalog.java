package ch.hftso.uhrensystem.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.hftso.uhrensystem.interfaces.Beobachter;
import ch.hftso.uhrensystem.interfaces.Uhr;
import ch.hftso.uhrensystem.server.UhrServer;

/**
 * Die von "Beobachter" implementierte Klasse "BeobachterAnalog" merkt sich den
 * Status, der mit jenem des assoziierten Subjekts konsistent sein soll.
 * 
 * @author Bau / letzte Änderung: 14.10.2009
 */
public class BeobachterAnalog extends JFrame implements Beobachter {
	private static final long serialVersionUID = -8209864561223418070L;
	private Beobachter stub = null;
	private Uhr lnkUhr = null;

	private BorderLayout layout = new BorderLayout();
	AnalogDigitalUhr content;

	/** Creates new form JFrame */
	public BeobachterAnalog() {
		try {
			// Lokalisieren des Remote-Objekts
			ResourceBundle bundle = ResourceBundle.getBundle("host");
			Registry registry = LocateRegistry.getRegistry(bundle.getString("HOSTNAME"), 1099);
			lnkUhr = (Uhr) registry.lookup(Uhr.NAME);
		} catch (Exception e) {
			System.out.println("Das Objekt konnte nicht erzeugt werden");
			e.printStackTrace(System.err);
		}
		// lnkUhr = server;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		frameSize.height = ((frameSize.height > screenSize.height) ? screenSize.height : frameSize.height);
		frameSize.width = ((frameSize.width > screenSize.width) ? screenSize.width : frameSize.width);
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
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
		this.getContentPane().setLayout(layout);
		content = new AnalogDigitalUhr();
		content.setPreferredSize(new Dimension(212, 250));
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.setTitle("Analoguhr");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});
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

	/**
	 * Time! Copyright (c) 1997, 1998 Sun Microsystems, Inc. All Rights
	 * Reserved.
	 * 
	 * @author Rachel Gollub
	 */
	class AnalogDigitalUhr extends JPanel {
		private static final long serialVersionUID = 9183601760193597475L;
		// Konstanten
		static final int UHRRADIUS = 100;
		static final int MITTELPUNKTX = 106;
		static final int MITTELPUNKTY = 110;
		static final int ZEIGERSEKUNDEN = UHRRADIUS - 5;
		static final int ZEIGERMINUTEN = UHRRADIUS - 10;
		static final int ZEIGERSTUNDEN = UHRRADIUS - 20;
		// Dimensions used to draw hands
		int lastxs, lastys, lastxm, lastym, lastxh, lastyh;
		String lastdate; // String to hold date displayed
		Font clockFaceFont; // Font for number display on clock
		Color handColor; // Color of main hands and dial
		Color numberColor; // Color of second hand and numbers

		public AnalogDigitalUhr() {
			lastxs = lastys = lastxm = lastym = lastxh = lastyh = 0;
			handColor = Color.blue;
			numberColor = Color.darkGray;
			try {
				this.setBackground(new Color(Integer.parseInt("ffffff")));
			} catch (Exception e) {
			}
			try {
				handColor = new Color(Integer.parseInt("ff0000"));
			} catch (Exception e) {
			}
			try {
				numberColor = new Color(Integer.parseInt("ff00ff"));
			} catch (Exception e) {
			}
		}

		// Plotpoints allows calculation to only cover ZEIGERSEKUNDEN
		// degrees of the circle, and then mirror
		public void plotpoints(int x0, int y0, int x, int y, Graphics g) {
			g.drawLine(x0 + x, y0 + y, x0 + x, y0 + y);
			g.drawLine(x0 + y, y0 + x, x0 + y, y0 + x);
			g.drawLine(x0 + y, y0 - x, x0 + y, y0 - x);
			g.drawLine(x0 + x, y0 - y, x0 + x, y0 - y);
			g.drawLine(x0 - x, y0 - y, x0 - x, y0 - y);
			g.drawLine(x0 - y, y0 - x, x0 - y, y0 - x);
			g.drawLine(x0 - y, y0 + x, x0 - y, y0 + x);
			g.drawLine(x0 - x, y0 + y, x0 - x, y0 + y);
		}

		// Circle is just Bresenham's algorithm for
		// a scan converted circle
		public void circle(int x0, int y0, int r, Graphics g) {
			int x, y;
			float d;
			x = 0;
			y = r;
			d = 5 / 4 - r;
			plotpoints(x0, y0, x, y, g);
			while (y > x) {
				if (d < 0) {
					d = d + 2 * x + 3;
					x++;
				} else {
					d = d + 2 * (x - y) + 5;
					x++;
					y--;
				}
				plotpoints(x0, y0, x, y, g);
			}
		}

		// Paint is the main part of the program
		public void paint(Graphics g) {
			int xh, yh, xm, ym, xs, ys, s = 0, m = 10, h = 10, xcenter, ycenter;
			try {
				if (lnkUhr != null) {
					s = lnkUhr.getSekunden();
					m = lnkUhr.getMinuten();
					h = lnkUhr.getStunden();
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
			// l�schen
			g.clearRect(0, 0, getWidth(), getHeight());

			xcenter = MITTELPUNKTX;
			ycenter = MITTELPUNKTY;
			xs = (int) (Math.cos(s * 3.14f / 30 - 3.14f / 2) * ZEIGERSEKUNDEN + xcenter);
			ys = (int) (Math.sin(s * 3.14f / 30 - 3.14f / 2) * ZEIGERSEKUNDEN + ycenter);
			xm = (int) (Math.cos(m * 3.14f / 30 - 3.14f / 2) * ZEIGERMINUTEN + xcenter);
			ym = (int) (Math.sin(m * 3.14f / 30 - 3.14f / 2) * ZEIGERMINUTEN + ycenter);
			xh = (int) (Math.cos((h * 30 + m / 2) * 3.14f / 180 - 3.14f / 2) * ZEIGERSTUNDEN + xcenter);
			yh = (int) (Math.sin((h * 30 + m / 2) * 3.14f / 180 - 3.14f / 2) * ZEIGERSTUNDEN + ycenter);
			// Draw the circle and numbers
			g.setFont(clockFaceFont);
			g.setColor(handColor);
			circle(xcenter, ycenter, UHRRADIUS, g);
			g.setColor(numberColor);
			g.drawString("9", xcenter - ZEIGERSEKUNDEN, ycenter + 3);
			g.drawString("3", xcenter + ZEIGERMINUTEN, ycenter + 3);
			g.drawString("12", xcenter - 5, ycenter - ZEIGERSEKUNDEN + 10);
			g.drawString("6", xcenter - 3, ycenter + ZEIGERSEKUNDEN);
			// Erase if necessary, and redraw
			g.setColor(this.getBackground());
			if (xs != lastxs || ys != lastys) {
				g.drawLine(xcenter, ycenter, lastxs, lastys);
			}
			if (xm != lastxm || ym != lastym) {
				g.drawLine(xcenter, ycenter - 1, lastxm, lastym);
				g.drawLine(xcenter - 1, ycenter, lastxm, lastym);
			}
			if (xh != lastxh || yh != lastyh) {
				g.drawLine(xcenter, ycenter - 1, lastxh, lastyh);
				g.drawLine(xcenter - 1, ycenter, lastxh, lastyh);
			}
			g.setColor(numberColor);
			g.drawLine(xcenter, ycenter, xs, ys);
			g.setColor(handColor);
			g.drawLine(xcenter, ycenter - 1, xm, ym);
			g.drawLine(xcenter - 1, ycenter, xm, ym);
			g.drawLine(xcenter, ycenter - 1, xh, yh);
			g.drawLine(xcenter - 1, ycenter, xh, yh);
			lastxs = xs;
			lastys = ys;
			lastxm = xm;
			lastym = ym;
			lastxh = xh;
			lastyh = yh;
		}

		public void run() {
			this.repaint();
		}

		public void update(Graphics g) {
			this.repaint();
		}
	}

	public void update() {
		content.run();
	}

	public static void main(String[] argv) throws Exception {
		new BeobachterAnalog();
	}
}
