package band;

import java.util.ArrayList;

import channel.I_Channel;
import channel.SignalChannel;
import rule.I_Rule;

public interface I_Band {

	void setMaxFreq(int freq);

	void setMinFreq(int freq);
	
	int getMinFreq();
	
	int getMaxFreq();
	
	I_Channel getChannel();
	
	public void addRuleSettingsToBand(BandRuleAtrributes settings);

	public void removeRuleSettingsFromBand(I_Rule rule);
	
	public ArrayList<BandRuleAtrributes> getBandRuleAttributesList();
	
	public void setChannel(SignalChannel channel);
	
	public void setFrequencySpectrum(int[] frequencySpectrum);
	
	public int[] getBandSpectrum();
	
	public void setbandPower (double[] signal);

}
