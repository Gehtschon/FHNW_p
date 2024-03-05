package channel;

public class Spectral_density {
	/**
	 * Berechnet aus dem zweiseitigen Spektrum das einseitige Spektrum.
	 * gebraucht beim Leistungsdichte spektrum
	 * @param in double[][]
	 * @return double[][]
	 */
	public static double[][] two2oneSided(double[][] in) {
		double[][] out = new double[(in.length / 2) + 1][2];
		int i = 0;
		out[i][0] = in[i][0];
		out[i][1] = in[i][1];
		for (i++; i < out.length - 1; i++) {
			out[i][0] = in[i][0] * 2.0;
			out[i][1] = in[i][1] * 2.0;
		}
		out[i][0] = in[i][0];
		out[i][1] = in[i][1];
		return out;
	}
}
