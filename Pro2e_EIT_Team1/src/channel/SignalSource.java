package channel;

public interface SignalSource {
	/**
	 * startet die SignalSource
	 */
	void start();

    /**
    * registriert einen Listener
    */
    void registerListener(SignalSourceListener listener);

}
