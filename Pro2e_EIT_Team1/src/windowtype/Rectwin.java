package windowtype;

public class Rectwin implements I_Windowtype {
	/**
	 * Rechteckfenster gegebener Laenge.
	 * 
	 * @param length Fensterlaenge.
	 * @return Fensterfunktion.
	 */
	
	
	private String windowType = "Rect";

	
	public String getWindowTypeName() {
		return windowType;
	}
	
	@Override
	public double[] window(int length) {
		double[] w = new double[length];
		for (int n = 0; n < length; n++) {
			w[n] = 1.0;
		}
		return w;
	}

}
