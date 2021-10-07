package pricer.calculs.utilitaires;

import pricer.calculs.Resultat1Calcul;

public class ResultatCalcul {
	Resultat1Calcul[] resultats;
	ParametresCalcul parametresCalcul;

	public ResultatCalcul(Resultat1Calcul[] resultats, ParametresCalcul parametresCalcul) {
		super();
		this.resultats = resultats;
		this.parametresCalcul = parametresCalcul;
	}

	public ParametresCalcul getParametresCalcul() {
		return parametresCalcul;
	}

	public void setParametresCalcul(ParametresCalcul parametresCalcul) {
		this.parametresCalcul = parametresCalcul;
	}

	public Resultat1Calcul[] getResultats() {
		return resultats;
	}

	public void setResultats(Resultat1Calcul[] resultats) {
		this.resultats = resultats;
	}

	@Override
	public String toString() {
		String[] techs = new String[] { "BS", " MC" };
		String res = "";
		for (int i = 0; i < 2; i++) {
			res += techs[i] + " : " + resultats[i].toString();
		}
		return res;
	}
}
