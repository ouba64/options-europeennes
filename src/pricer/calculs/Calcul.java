package pricer.calculs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pricer.calculs.utilitaires.ParametresCalcul;

public abstract class Calcul extends Resultat1Calcul implements Callable<Calcul> {

	ParametresCalcul parametresCalcul;

	public Calcul() {

	}

	public Calcul(ParametresCalcul parametresCalcul) {
		this.parametresCalcul = parametresCalcul;
	}

	public abstract void calcul();

	public abstract String getName();

	@Override
	public Calcul call() throws Exception {
		calcul();
		return this;
	}

	public ParametresCalcul getParametresCalcul() {
		return parametresCalcul;
	}

	public void setParametresCalcul(ParametresCalcul parametresCalcul) {
		this.parametresCalcul = parametresCalcul;
	}
}
