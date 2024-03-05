
/**
 * $Author: richard.gut $<br>
 */

public class DSP {

	/**
	 * Berechnet den quadrierten Betrag des komplexwertigen Arrays.
	 * 
	 * inputArray[i][0] -> Realteil, inputArray[i][1] -> Imaginaerteil.
	 * 
	 * @param in double[][]
	 * @return double[]
	 */
	public static double[] absSqr(double[][] in) {
		double[] out = new double[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = in[i][0] * in[i][0] + in[i][1] * in[i][1];
		}
		return out;
	}

	/**
	 * Berechnet <sl>in-place</sl> die FFT von inputArray.
	 * 
	 * @param inputArray double[][2] mit [][0] -> Realteil, [][1] -> Imaginaerteil.
	 * @return double[][] mit FFT von inpuArray.
	 */
	public static double[][] fft(double[][] inputArray) {
		double u_r, u_i, w_r, w_i, t_r, t_i;
		int ln, nv2, k, l, le, le1, j, ip, i, n;
		n = inputArray.length;
		ln = (int) (Math.log(n) / Math.log(2) + 0.5);
		nv2 = n / 2;
		j = 1;
		for (i = 1; i < n; i++) {
			if (i < j) {
				t_r = inputArray[i - 1][0];
				t_i = inputArray[i - 1][1];
				inputArray[i - 1][0] = inputArray[j - 1][0];
				inputArray[i - 1][1] = inputArray[j - 1][1];
				inputArray[j - 1][0] = t_r;
				inputArray[j - 1][1] = t_i;
			}
			k = nv2;
			while (k < j) {
				j = j - k;
				k = k / 2;
			}
			j = j + k;
		}
		for (l = 1; l <= ln; l++) /* loops thru stages */ {
			le = (int) (Math.exp(l * Math.log(2)) + 0.5);
			le1 = le / 2;
			u_r = 1.0;
			u_i = 0.0;
			w_r = Math.cos(Math.PI / le1);
			w_i = -Math.sin(Math.PI / le1);
			for (j = 1; j <= le1; j++) /*
										 * loops thru 1/2 twiddle values per stage
										 */ {
				for (i = j; i <= n; i += le) /* loops thru points per 1/2 twiddle */ {
					ip = i + le1;
					t_r = inputArray[ip - 1][0] * u_r - u_i * inputArray[ip - 1][1];
					t_i = inputArray[ip - 1][1] * u_r + u_i * inputArray[ip - 1][0];
					inputArray[ip - 1][0] = inputArray[i - 1][0] - t_r;
					inputArray[ip - 1][1] = inputArray[i - 1][1] - t_i;
					inputArray[i - 1][0] = inputArray[i - 1][0] + t_r;
					inputArray[i - 1][1] = inputArray[i - 1][1] + t_i;
				}
				t_r = u_r * w_r - w_i * u_i;
				u_i = w_r * u_i + w_i * u_r;
				u_r = t_r;
			}
		}
		return inputArray;
	}

	/**
	 * Berechnet ein Hanning Fenster gegebener Laenge.
	 * 
	 * @param length Fensterlaenge.
	 * @return double[] der Laenge length mit Werten der Fensterfunktion.
	 *         Matlab-Test i.O.
	 */
	/*
	 * public static double[] hanning(int length) { double[] w = new double[length];
	 * for (int i = 0; i < length; i++) { w[i] = 0.5 * (1.0 - Math.cos((2.0 *
	 * Math.PI * (i + 1)) / (length + 1)));// already implemented return w; }
	 */

	/**
	 * Rechteckfenster gegebener Laenge.
	 * 
	 * @param length Fensterlaenge.
	 * @return Fensterfunktion.
	 */
	/*
	 * public static double[] rectwin(int length) { double[] w = new double[length];
	 * for (int n = 0; n < length; n++) {//already implemented w[n] = 1.0; } return
	 * w; }
	 */

	/**
	 * Berechnet aus dem zweiseitigen Spektrum das einseitige Spektrum. gebraucht
	 * beim Leistungsdichte spektrum
	 * 
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

	public static double[] linspace(double begin, double end, int n) {
		double[] res = new double[n];
		double step = (end - begin) / (n - 1);

		for (int i = 0; i < res.length; i++) {
			res[i] = begin + i * step;
		}
		return res;
	}

}
