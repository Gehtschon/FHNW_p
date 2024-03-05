package fft_Settings;

import channel.I_Channel;
import controllers.I_Controller;
import windowtype.I_Windowtype;

public interface I_Plot_FFT_Settings_Controller extends I_Controller{

	void setOverlap(int value, I_Channel chanel);
	// set value
	// chanel.setOverlap(value);


	void setSamples(int value, I_Channel chanel);
	// set value
	// chanel.setSamples(value);



	void setFrequency(int value, I_Channel chanel);
	// set value
	// chanel.setFrequency(value);


	void setWindow(I_Windowtype windowtype);
	// chanel.setWindow(value);

	

	

	void setChannelSettings(I_Channel channel, int overlap, int samples, int frequency, I_Windowtype window);

}
