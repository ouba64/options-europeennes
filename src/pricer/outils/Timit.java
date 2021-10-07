package pricer.outils;

public class Timit {
	long start;
	long elapsed;

	public void start() {
		start = System.currentTimeMillis();
	}

	public long stop() {
		elapsed = System.currentTimeMillis() - start;
		return elapsed;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getElapsed() {
		return elapsed;
	}

	public String getElapsedString() {
		return String.format("Temps de calcul : %1$Ts.%1$TLs%n", elapsed);
	}

}
