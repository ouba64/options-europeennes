package pricer.calculs;

public class Resultat1Calcul {
	double valeurCall;
	double valeurPut;

	public Resultat1Calcul() {
	}

	public Resultat1Calcul(double valeurCall, double valeurPut) {
		super();
		this.valeurCall = valeurCall;
		this.valeurPut = valeurPut;
	}

	public double getValeurCall() {
		return valeurCall;
	}

	public void setValeurCall(double valeurCall) {
		this.valeurCall = valeurCall;
	}

	public double getValeurPut() {
		return valeurPut;
	}

	public void setValeurPut(double valeurPut) {
		this.valeurPut = valeurPut;
	}
	
	@Override
	public String toString() {
		return "Call=" + valeurCall + ", Put=" + valeurPut;
	}

}
