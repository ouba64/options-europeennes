package pricer.vue;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBoutons extends JPanel {
	JButton btnEffacer;
	JButton btnLancer;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelBoutons() {
		super(new FlowLayout());
		btnEffacer = new JButton("Effacer");
		btnLancer = new JButton("Lancer");
		add(btnLancer);
		add(btnEffacer);
	}

	public JButton getBtnEffacer() {
		return btnEffacer;
	}

	public void setBtnEffacer(JButton btnEffacer) {
		this.btnEffacer = btnEffacer;
	}

	public JButton getBtnLancer() {
		return btnLancer;
	}

	public void setBtnLancer(JButton btnLancer) {
		this.btnLancer = btnLancer;
	}

}
