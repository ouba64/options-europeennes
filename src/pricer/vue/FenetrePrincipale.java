package pricer.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import pricer.calculs.LanceCalculs;
import pricer.calculs.Resultat1Calcul;
import pricer.calculs.utilitaires.ParametresCalcul;
import pricer.calculs.utilitaires.ResultatCalcul;
import pricer.calculs.utilitaires.ResultatCalculListener;

public class FenetrePrincipale extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelGraphique panelGraphique;
	PanelParametres panelParametres;
	private JButton btnLancer;
	private JButton btnEffacer;
	private FenetrePrincipale.ControleurFenetrePrincipale controleur;
	private PanelResultats panelResultats;
	PanelChargement panelChargement;

	LanceCalculs lanceCalculs;

	class ControleurFenetrePrincipale implements ActionListener {

		public ControleurFenetrePrincipale() {
			lanceCalculs = new LanceCalculs();
		}

		public void handleActionQuitter(ActionEvent e) {
			System.exit(0);
		}

		public void handleActionEffacer(ActionEvent e) {
			panelGraphique.effacer();
			for (JLabel label : panelResultats.getValues()) {
				label.setText("");
			}
		}

		public void handleActionLancer(ActionEvent e) {
			CalculWorker worker = new CalculWorker();
			worker.execute();
			lanceCalculs.addResultatCalculListener(worker);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == btnEffacer) {
				handleActionEffacer(e);
			}
			else if (source == btnLancer) {
				handleActionLancer(e);
			}
		}
	}

	public class CalculWorker extends SwingWorker<Void, ResultatCalcul> implements ResultatCalculListener {

		@Override
		protected Void doInBackground() throws Exception {
			double prixSousJacent = getDouble(0);
			double prixStrike = getDouble(1);
			double maturity = getDouble(2);
			double tauxSansRisque = getDouble(3);
			double tauxVolatilite = getDouble(4);

			int nbEtapes = getInt(5);
			int nbThreads = getInt(6);
			int nbSimulationsMax = getInt(7);
			int incrementation = getInt(8);
			ParametresCalcul parametresCalcul = new ParametresCalcul(prixSousJacent, prixStrike, maturity,
					tauxSansRisque, tauxVolatilite, nbEtapes, nbThreads, nbSimulationsMax, incrementation);
			lanceCalculs.setParametresCalcul(parametresCalcul);
			travailEnCours(true);
			lanceCalculs.lanceCalculs();
			return null;
		}

		@Override
		protected void process(List<ResultatCalcul> chunks) {
			ResultatCalcul e = chunks.get(chunks.size() - 1);
			int nSimul = e.getParametresCalcul().getNbSimulationsMax();
			double callBS = e.getResultats()[0].getValeurCall();
			double putBS = e.getResultats()[0].getValeurPut();
			double callMC;
			double putMC;

			if (e.getResultats()[1] != null) {
				callMC = e.getResultats()[1].getValeurCall();
				putMC = e.getResultats()[1].getValeurPut();
			}
			else {
				Random random = new Random();
				callMC = random.nextDouble();
				putMC = random.nextDouble();
			}
			afficherResultats(new double[] { callBS, putBS, callMC, putMC });
			panelGraphique.ajouterPoint(nSimul, callBS, putBS, callMC, putMC);
		}
		
		@Override
		public void handleResultatCalcul(ResultatCalcul e) {
			publish(e);
		}

		@Override
		protected void done() {
			travailEnCours(false);
		}
		
		private void travailEnCours(boolean enCours) {
			panelChargement.showChargement(enCours);
			btnLancer.setEnabled(!enCours);
		}

		private double getDouble(int i) {
			double v = (double) getNumber(i, true);
			return v;
		}

		private int getInt(int i) {
			int v = (int) getNumber(i, false);
			return v;
		}

		private Object getNumber(int i, boolean isDouble) {
			String text = panelParametres.getTextFields()[i].getText();
			try {

				if (isDouble) {
					if (text != null && text.equals("")) {
						return (double) 0;
					}
					double v = Double.valueOf(text);
					return v;
				}
				else {
					if (text != null && text.equals("")) {
						return (int) 0;
					}
					int v = Integer.valueOf(text);
					return v;
				}
			}
			catch (NumberFormatException e) {
				// custom title, error icon
				JOptionPane.showMessageDialog(FenetrePrincipale.this,
						"Votre entr�e n'est pas un nombre, Veuillez recommencer", "Erreur de saisie",
						JOptionPane.ERROR_MESSAGE);
			}
			return 0;
		}


	}

	public FenetrePrincipale() {
		super("Calcul Option europ�enne");
		Container panel = buildFenetre();
		setContentPane(panel);
	}

	public void buildPanelGraphique() {
		panelGraphique = new PanelGraphique();
	}

	public PanelParametres buildPanelParametres() {
		PanelParametres p = new PanelParametres();
		return p;
	}

	public PanelResultats buildPanelResultats() {
		panelResultats = new PanelResultats();
		return panelResultats;
	}

	public JPanel buildPanelBoutons() {
		PanelBoutons bp = new PanelBoutons();
		btnEffacer = bp.getBtnEffacer();
		btnLancer = bp.getBtnLancer();
		btnEffacer.addActionListener(controleur);
		btnLancer.addActionListener(controleur);
		return bp;
	}

	public JPanel buildPanelChargement() {
		panelChargement = new PanelChargement();
		return panelChargement;
	}

	public void buildPanelCentre() {
		buildPanelGraphique();
	}

	private void ajouterBordure(JPanel jComp8, String stitle) {
		TitledBorder title;
		title = BorderFactory.createTitledBorder(stitle);
		jComp8.setBorder(title);
	}

	public JPanel buildPanelGauche() {
		JPanel panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());
		panelParametres = buildPanelParametres();
		JPanel panelResultats = buildPanelResultats();
		JPanel panelDesBoutons = buildPanelBoutons();
		JPanel panelChargement = buildPanelChargement();

		panelGauche.add(panelParametres, BorderLayout.NORTH);
		panelGauche.add(panelResultats, BorderLayout.CENTER);

		ajouterBordure(panelParametres, "Param�tres");
		ajouterBordure(panelResultats, "R�sultats");

		/*
		 * JPanel panelBoutonsEtChargement = new JPanel(new GridBagLayout());
		 * panelBoutonsEtChargement.add(panelDesBoutons, new GridBagConstraints());
		 * panelBoutonsEtChargement.add(panelChargement, new GridBagConstraints());
		 */

		JPanel panelBoutonsEtChargement = new JPanel();
		panelBoutonsEtChargement.setLayout(new BoxLayout(panelBoutonsEtChargement, BoxLayout.PAGE_AXIS));
		panelBoutonsEtChargement.add(panelDesBoutons);
		panelBoutonsEtChargement.add(panelChargement);
		panelGauche.add(panelBoutonsEtChargement, BorderLayout.SOUTH);
		return panelGauche;
	}

	public JMenuBar buildMenu() {
		return null;
	}

	public Container buildFenetre() {
		controleur = this.new ControleurFenetrePrincipale();

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		buildPanelCentre();
		JPanel panelGauche = buildPanelGauche();
		jPanel.add(panelGraphique, java.awt.BorderLayout.CENTER);
		jPanel.add(panelGauche, BorderLayout.WEST);
		return jPanel;
	}

	private void afficherResultats(double[] vs) {
		panelResultats.getValues()[0].setText(String.valueOf(vs[0]));
		panelResultats.getValues()[1].setText(String.valueOf(vs[2]));

		panelResultats.getValues()[2].setText(String.valueOf(vs[1]));
		panelResultats.getValues()[3].setText(String.valueOf(vs[3]));
	}
}
