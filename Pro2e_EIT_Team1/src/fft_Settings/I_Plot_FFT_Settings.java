package fft_Settings;

import java.util.ArrayList;

import channel.SignalChannel;

public interface I_Plot_FFT_Settings{



	
	// chanel.setOverlap(value);
	// chanel.setSamples(value);
	// chanel.setFrequency(value);
	// chanel.setWindow(value);

	void update(ArrayList<SignalChannel> channels);
	

}
