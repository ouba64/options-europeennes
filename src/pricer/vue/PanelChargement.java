package pricer.vue;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelChargement extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PANEL_CHARGEMENT = "c";
	private static final String PANEL_VIDE = "v";

	CardLayout cl;

	public PanelChargement() {

		JPanel panel1 = new JPanel(new GridBagLayout());
		URL imgURL = getClass().getResource("ajax_loader_gray_32.gif");

		ImageIcon icon = new ImageIcon(imgURL);
		JLabel label1 = new JLabel("Simulation en cours", icon, JLabel.CENTER);
		// Set the position of the text, relative to the icon:
		label1.setVerticalTextPosition(JLabel.BOTTOM);
		label1.setHorizontalTextPosition(JLabel.CENTER);

		panel1.add(label1);

		JPanel panel2 = new JPanel();

		cl = new CardLayout();
		setLayout(cl);
		add(panel1, PANEL_CHARGEMENT);
		add(panel2, PANEL_VIDE);
		showChargement(false);
	}

	public void showChargement(boolean show) {
		String name = show ? PANEL_CHARGEMENT : PANEL_VIDE;
		cl.show(this, name);
	}
}
