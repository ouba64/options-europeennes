package pricer.calculs.utilitaires;

public class ParametresCalcul {
	double prixSousJacent;
	double prixStrike;
	double maturity;
	double tauxSansRisque;
	double tauxVolatilite;
	int nbEtapes;
	int nbThreads;
	int nbSimulationsMax;
	int incrementation;

	static final boolean isDouble[] = new boolean[] { true, true, true, true, true, false, false, false, false };

	public ParametresCalcul() {
		this.prixSousJacent = 40;
		this.prixStrike = 50;
		this.maturity = 182.625;
		this.tauxSansRisque = 0.06;
		this.tauxVolatilite = 0.45;
		this.nbEtapes = 1000;
		this.nbThreads = 10;
		this.nbSimulationsMax = 100000;
		this.incrementation = 10000;
	}

	public ParametresCalcul(double prixSousJacent, double prixStrike, double maturity, double tauxSansRisque,
			double tauxVolatilite, int nbEtapes, int nbThreads, int nbSimulationsMax, int incrementation) {
		super();
		this.prixSousJacent = prixSousJacent;
		this.prixStrike = prixStrike;
		this.maturity = maturity;
		this.tauxSansRisque = tauxSansRisque;
		this.tauxVolatilite = tauxVolatilite;
		this.nbEtapes = nbEtapes;
		this.nbThreads = nbThreads;
		this.nbSimulationsMax = nbSimulationsMax;
		this.incrementation = incrementation;
	}

	public ParametresCalcul duplicate() {
		ParametresCalcul p = this;
		ParametresCalcul pc = new ParametresCalcul(p.getPrixSousJacent(), p.getPrixStrike(), p.getMaturity(),
				p.getTauxSansRisque(), p.getTauxVolatilite(), p.getNbEtapes(), p.getNbThreads(),
				Math.round(p.getNbSimulationsMax() / p.getNbThreads()), p.getIncrementation());
		return pc;
	}

	public double getPrixSousJacent() {
		return prixSousJacent;
	}

	public void setPrixSousJacent(double prixSousJacent) {
		this.prixSousJacent = prixSousJacent;
	}

	public double getPrixStrike() {
		return prixStrike;
	}

	public void setPrixStrike(double prixStrike) {
		this.prixStrike = prixStrike;
	}

	public double getMaturity() {
		return maturity;
	}

	public void setMaturity(double maturity) {
		this.maturity = maturity;
	}

	public double getTauxSansRisque() {
		return tauxSansRisque;
	}

	public void setTauxSansRisque(double tauxSansRisque) {
		this.tauxSansRisque = tauxSansRisque;
	}

	public double getTauxVolatilite() {
		return tauxVolatilite;
	}

	public void setTauxVolatilite(double tauxVolatilite) {
		this.tauxVolatilite = tauxVolatilite;
	}

	public int getNbEtapes() {
		return nbEtapes;
	}

	public void setNbEtapes(int nbEtapes) {
		this.nbEtapes = nbEtapes;
	}

	public int getNbThreads() {
		return nbThreads;
	}

	public void setNbThreads(int nbThreads) {
		this.nbThreads = nbThreads;
	}

	public int getNbSimulationsMax() {
		return nbSimulationsMax;
	}

	public void setNbSimulationsMax(int nbSimulationsMax) {
		this.nbSimulationsMax = nbSimulationsMax;
	}

	public int getIncrementation() {
		return incrementation;
	}

	public void setIncrementation(int incrementation) {
		this.incrementation = incrementation;
	}

	@Override
	public String toString() {
		return "ParametresCalcul [prixSousJacent=" + prixSousJacent + ", prixStrike=" + prixStrike + ", maturity="
				+ maturity + ", tauxSansRisque=" + tauxSansRisque + ", tauxVolatilit�=" + tauxVolatilite + ", nbEtapes="
				+ nbEtapes + ", nbThreads=" + nbThreads + ", nbSimulationsMax=" + nbSimulationsMax + ", incrementation="
				+ incrementation + "]";
	}
}
