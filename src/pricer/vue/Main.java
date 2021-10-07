package pricer.vue;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Main {
	private static void createAndShowGUI() {
		// Create and set up the window.
		FenetrePrincipale vue = new FenetrePrincipale();

		vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menu = vue.buildMenu();
		vue.setJMenuBar(menu);
		vue.setContentPane(vue.buildFenetre());
		vue.pack();
		vue.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
