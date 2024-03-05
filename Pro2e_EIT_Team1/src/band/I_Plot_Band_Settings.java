package band;

import java.util.ArrayList;

import channel.SignalChannel;

public interface I_Plot_Band_Settings {

	/**
	 * Opens constructor of band with 2 int and create a new band in the current
	// channel
	 * @param channel Channel for how the band is created
	 * @param minFreq
	 * @param maxFreq
	 */
	void createBand(SignalChannel channel, int minFreq, int maxFreq);
	

	/**
	 * Update the view. Has to be called every time somethings changed
	 *  method to update values on screen.
	 * @param channel Array with all Channel who exists
	 */
	void update(ArrayList<SignalChannel> channels);
}


