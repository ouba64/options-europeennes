package pricer.vue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import pricer.calculs.utilitaires.ParametresCalcul;

public class PanelParametres extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField[] textFields;
	public static final String[] labels = { "Prix sous-jacent: ", "Prix strike: ", "Maturity: ", "Taux sans risque: ",
			"Taux volatilité: ", "(MC) Nombre d'étapes: ", "(MC) Nombre de threads: ",
			"(MC) Nombre de simulations max: ", "(MC) Incrémentation: " };

	public PanelParametres() {
		textFields = new JTextField[labels.length];
		buildPanel();

	}

	private void buildPanel() {

		int numPairs = labels.length;

		// Create and populate the panel.
		setLayout(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			add(l);
			JTextField textField = new JTextField(10);
			textFields[i] = textField;
			l.setLabelFor(textField);
			add(textField);
		}

		ParametresCalcul parametresCalcul = new ParametresCalcul();
		textFields[0].setText(String.valueOf(parametresCalcul.getPrixSousJacent()));
		textFields[1].setText(String.valueOf(parametresCalcul.getPrixStrike()));
		textFields[2].setText(String.valueOf(parametresCalcul.getMaturity()));
		textFields[3].setText(String.valueOf(parametresCalcul.getTauxSansRisque()));
		textFields[4].setText(String.valueOf(parametresCalcul.getTauxVolatilite()));
		textFields[5].setText(String.valueOf(parametresCalcul.getNbEtapes()));
		textFields[6].setText(String.valueOf(parametresCalcul.getNbThreads()));
		textFields[7].setText(String.valueOf(parametresCalcul.getNbSimulationsMax()));
		textFields[8].setText(String.valueOf(parametresCalcul.getIncrementation()));
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(this, numPairs, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad
	}

	public JTextField[] getTextFields() {
		return textFields;
	}

}
