package pricer.calculs;

import java.util.Random;
import java.util.concurrent.Callable;

import pricer.calculs.utilitaires.ParametresCalcul;

public class CalculMonteCarlo extends Calcul {

	private static final long SEED = 0;
	Random random;

	public CalculMonteCarlo(ParametresCalcul parametresCalcul) {
		super(parametresCalcul);
		random = new Random(SEED);
	}

	public ParametresCalcul getParametresCalcul() {
		return parametresCalcul;
	}

	public void setParametresCalcul(ParametresCalcul parametresCalcul) {
		this.parametresCalcul = parametresCalcul;
	}

	@Override
	public void calcul() {
		double S = parametresCalcul.getPrixSousJacent();
		double T = parametresCalcul.getMaturity();
		T = T/365;
		double K = parametresCalcul.getPrixStrike();
		double r = parametresCalcul.getTauxSansRisque();
		double sigma = parametresCalcul.getTauxVolatilite();
		int nSimulations = parametresCalcul.getNbSimulationsMax();
		int nSteps = parametresCalcul.getNbEtapes();

		double dT = T / nSteps;
		double sT;
		double epsilon;

		double call = 0;
		double put = 0;

		for (int i = 0; i < nSimulations; i++) {
			sT = S;	
			for (int j = 0; j < nSteps; j++) {
				epsilon = random.nextGaussian();
				sT = sT * Math.exp((r - (Math.pow(sigma, 2) / 2)) * dT + epsilon * sigma * Math.sqrt(dT));
			}
			call += Math.max(sT - K, 0);
			put += Math.max(K - sT, 0);
		}
		valeurCall = Math.exp(-r * T) * call / nSimulations;
		valeurPut = Math.exp(-r * T) * put / nSimulations;
	}

	@Override
	public String getName() {
		return "Calcul Monte Carlo";
	}
}
