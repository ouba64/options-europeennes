package pricer.calculs;

import org.apache.commons.math3.distribution.NormalDistribution;

import pricer.calculs.utilitaires.ParametresCalcul;

public class CalculBlackScholes extends Calcul {

	public CalculBlackScholes(ParametresCalcul parametresCalcul) {
		super(parametresCalcul);
	}

	@Override
	public void calcul() {
		double d1, d2;
		double S = parametresCalcul.getPrixSousJacent();
		double T = parametresCalcul.getMaturity();
		T = T/365;
		double K = parametresCalcul.getPrixStrike();
		double r = parametresCalcul.getTauxSansRisque();
		double sigma = parametresCalcul.getTauxVolatilité();
		d1 = (1/(sigma*Math.sqrt(T))) * (Math.log(S/K) + (r + (1/2)*Math.pow(sigma, 2))*T); 
		d2 = d1 - sigma * Math.sqrt(T);
		
		NormalDistribution ND = new NormalDistribution();
		double C =  S*ND.cumulativeProbability( d1) - K*Math.exp(-r*T) * ND.cumulativeProbability( d2);
		double P = -S*ND.cumulativeProbability(-d1) + K*Math.exp(-r*T) * ND.cumulativeProbability(-d2);
		
		valeurCall = C;
		valeurPut = P;
	}

	@Override
	public String getName() {
		return "Black scholes";
	}

}
