package pricer.vue;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class PanelResultats extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel[] values = new JLabel[4];

	public PanelResultats() {
		buildPanel();
	}

	private void buildPanel() {
		String[] labels = { "Call Black-Scholes: ", "Call Montecarlo: ", "Put Black-Scholes: ", "Put Monte-Carlo: ", };
		int numPairs = labels.length;

		// Create and populate the panel.
		setLayout(new SpringLayout());
		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			add(l);

			JLabel v = new JLabel("", JLabel.TRAILING);
			values[i] = v;
			v.setPreferredSize(new Dimension(100, 10));
			add(v);
			/*
			 * JTextField textField = new JTextField(10); l.setLabelFor(textField);
			 * add(textField);
			 */
		}

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(this, numPairs, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad
	}

	public JLabel[] getValues() {
		return values;
	}

}
