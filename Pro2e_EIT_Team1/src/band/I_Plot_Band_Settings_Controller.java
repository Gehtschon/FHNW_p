package band;

import channel.I_Channel;
import channel.SignalChannel;
import controllers.I_Controller;

public interface I_Plot_Band_Settings_Controller extends I_Controller {

	void createBand(SignalChannel channel, int minFreq, int maxFreq);
	
	void addBand(SignalChannel channel, Band band);
	
	void removeBand(Band band, SignalChannel Channel);
	
}
