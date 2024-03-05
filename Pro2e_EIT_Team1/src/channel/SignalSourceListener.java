package channel;

public interface SignalSourceListener {
	
	/**
	 * Wird periodisch aufgerufen.
	 * 
	 * @param channel die Kanalnummer
	 * @param samples Array von Samples
	 * @param len Länge der samples, welche valid sind (max samples.length)
	 */

	
	public void onSignalSamples(int channel, double[] samples, int len);
}
