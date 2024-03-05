package channel;

import band.Band;
import rule.I_Rule;
import rule.Rule;
import windowtype.I_Windowtype;

public interface I_Channel {

	void setOverlap(int value);
	// set value

	int getOverlap();

	void setSamples(int value);

	int getSamples();

	void setFrequency(int value);

	int getFrequency();

	void setWindow(I_Windowtype windowtype);

	I_Windowtype getWindow();

	void setChanelname(String name);

	String getChannelName();

	void setChanelLocation(String name);

	String getChanelLocation();
	
	void setChannelDescription(String name);
	
	String getChannelDescription();
	
	// Channel contains a list with all Bands who will be filled from the PlotBandSettings
	// or from the config-file
	Band[] getBands();
	
	void addBand(Band band);
	
	void removeBand(Band band);
	
	// Rules
	void setRule(Rule rule);
	void deleteRule(Rule rule);
	
	Boolean ruleExist(Rule rule);
	Boolean ruleExist(String name);
	
	I_Rule[] getRules();
	
	void setOverlapState(boolean overlapState);
	
	boolean getOverlapState();
	
	void setSamplesState(boolean samplesState);
	
	boolean getSamplesState();
	
	/**
	 * Set the current frequency spectrum of a Channel from class fft 
	 */
	void setFrequencySpectrum(int[] frequencySpectrum);
	
	
	/**
	 * gets the current frequency spectrum from th channel.
	 */
	int[] getFrequencySpectrum();


}
