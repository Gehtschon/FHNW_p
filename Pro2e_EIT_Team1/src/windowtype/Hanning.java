package windowtype;

public class Hanning implements I_Windowtype {
	/**
	 * Berechnet ein Hanning Fenster gegebener Laenge.
	 * 
	 * @param length
	 *            Fensterlaenge.
	 * @return double[] der Laenge length mit Werten der Fensterfunktion.
	 *         Matlab-Test i.O.
	 */
	private String windowType = "Hanning";

	
	public String getWindowTypeName() {
		return windowType;
	}

	@Override
	public double[] window(int length) {
		double[] w = new double[length];
		for (int i = 0; i < length; i++) {
			w[i] = 0.5 * (1.0 - Math.cos((2.0 * Math.PI * (i + 1))
					/ (length + 1)));
		}
		
		
		return w;
	}

}
