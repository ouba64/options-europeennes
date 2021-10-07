package pricer.calculs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pricer.calculs.utilitaires.ParametresCalcul;
import pricer.calculs.utilitaires.ResultatCalcul;
import pricer.calculs.utilitaires.ResultatCalculListener;
import pricer.outils.Timit;

public class LanceCalculs {

	List<ResultatCalculListener> listeners;

	ParametresCalcul parametresCalcul;
	private ExecutorService pool;

	public LanceCalculs() {
		listeners = new ArrayList<ResultatCalculListener>();
		pool = Executors.newCachedThreadPool();
	}

	public void lanceCalculs() {
		Resultat1Calcul rc1 = calculBS(parametresCalcul);
		int inc = parametresCalcul.getIncrementation();
		int nSimMax = parametresCalcul.getNbSimulationsMax();
		Resultat1Calcul res = null;
		Resultat1Calcul rc2 = null;
		ParametresCalcul pc2;
		int nSimulationsMax;
		// pour chaque numbre de simulatoin max
		for (int i = 1; i <= nSimMax / inc; i++) {
			pc2 = parametresCalcul.duplicate();
			nSimulationsMax = i * inc;
			pc2.setNbSimulationsMax(nSimulationsMax);
			rc2 = calculMC(pc2);
			ResultatCalcul resultats = new ResultatCalcul(new Resultat1Calcul[] { rc1, rc2 }, pc2);
			
			System.out.println(nSimulationsMax + " -- " + resultats);
			fireResultatCalcul(resultats);
		}
	}

	public Resultat1Calcul calculBS(ParametresCalcul parametresCalcul) {
		ExecutorCompletionService<Calcul> es = new ExecutorCompletionService<>(pool);
		CalculBlackScholes calculBS = new CalculBlackScholes(parametresCalcul);
		es.submit(calculBS);
		Resultat1Calcul res = agregeResultats(es, 1);
		return res;
	}

	public Resultat1Calcul calculMC(ParametresCalcul parametresCalcul) {
		ExecutorCompletionService<Calcul> es = new ExecutorCompletionService<>(pool);

		ParametresCalcul p = parametresCalcul;
		int nThreads = p.getNbThreads();
		ParametresCalcul pc = parametresCalcul.duplicate();
		pc.setNbSimulationsMax(Math.round(p.getNbSimulationsMax() / p.getNbThreads()));
		CalculMonteCarlo calculMC = new CalculMonteCarlo(pc);
		for (int t = 0; t < nThreads; t++) {
			es.submit(calculMC);
		}
		Resultat1Calcul res = agregeResultats(es, nThreads);
		return res;
	}

	public Resultat1Calcul agregeResultats(ExecutorCompletionService<Calcul> es, int nThreads) {
		Future<Calcul> f;
		Calcul result;
		double call = 0;
		double put = 0;
		double callt = 0;
		double putt = 0;
		for (int t = 0; t < nThreads; t++) {
			try {
				f = es.take();
				result = f.get();
				callt = result.getValeurCall();
				putt = result.getValeurPut();
				call += callt; 
				put += putt;
				System.out.println("			(MC) Thread " + (t+1) + " Call="+callt + "  Put=" + putt);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		call = call / nThreads;
		put = put / nThreads;
		Resultat1Calcul res = new Resultat1Calcul(call, put);
		return res;
	}

	public void addResultatCalculListener(ResultatCalculListener l) {
		listeners.add(l);
	}

	public void fireResultatCalcul(ResultatCalcul e) {
		for (ResultatCalculListener l : listeners) {
			l.handleResultatCalcul(e);
		}
	}

	public ParametresCalcul getParametresCalcul() {
		return parametresCalcul;
	}

	public void setParametresCalcul(ParametresCalcul parametresCalcul) {
		this.parametresCalcul = parametresCalcul;
	}

}
