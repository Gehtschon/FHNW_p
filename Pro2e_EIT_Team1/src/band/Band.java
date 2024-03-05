package band;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import channel.I_Channel;
import channel.SignalChannel;
import config.Config_Singleton;
import rule.I_Rule;
import rule.Rule;
import ueberwachung.Ueberwachung_Singelton;

public class Band implements I_Band {

	private SignalChannel channel;
	private int minFreq;
	private int maxFreq;
	private ArrayList<BandRuleAtrributes> bandRuleAttributesList = new ArrayList<BandRuleAtrributes>();
	private int[] frequencySpectrum;
	private int[] bandSpectrum;
	
	private double minPower;
	private double maxPower;
	private double avgPower;
	

	//local Instance of singelton class ueberwachung
	private transient Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
	
	
	private transient Config_Singleton config = Config_Singleton.getInstance();
	
	public Band() {

	}

	public Band(SignalChannel channel, int minFreq, int maxFreq) {
		this.channel = channel;
		this.minFreq = minFreq;
		this.maxFreq = maxFreq;

	}

	/*
	 * Set maximal frequency of band
	 */
	@Override
	public void setMaxFreq(int freq) {
		if (freq >= 0) {
			maxFreq = freq;
		}
	}

	/*
	 * Set minimal frequency of band
	 */
	@Override
	public void setMinFreq(int freq) {
		if (freq >= 0) {
			minFreq = freq;
		}
	}

	/*
	 * Get minimal frequency of band
	 */
	@Override
	public int getMinFreq() {
		return minFreq;
	}
	
	/*
	 * Get maximal frequency of band
	 */
	@Override
	public int getMaxFreq() {
		return maxFreq;
	}

	/*
	 * Get channel of band
	 */
	@Override
	public I_Channel getChannel() {
		return channel;
	}
	
	/*
	 * Set channel of band
	 */
	@Override
	public void setChannel(SignalChannel channel) {
		// TODO Auto-generated method stub
		this.channel = channel;
	}

	/*
	 * Compares current band with another
	 */
	@Override
	public boolean equals(Object obj) {

		// check if ref is the same
		if (obj == this) {
			return true;
		}

		// Check if compared object is a band-object
		if (!(obj instanceof Band)) {
			return false;
		}

		// Typecast object in Band
		Band bandobj = (Band) obj;

		// Check if channel and frequency match
		return (this.channel.equals(bandobj.getChannel()) && Integer.compare(bandobj.getMaxFreq(), maxFreq) == 0
				&& Integer.compare(bandobj.getMinFreq(), minFreq) == 0);
	}
	
	/*
	 * add a new RuleBandSetting of the band
	 */
	@Override
	public void addRuleSettingsToBand(BandRuleAtrributes settings) {
		bandRuleAttributesList.add(settings);
		config.writejson();
	}

	/*
	 * remove a new RuleBandSetting of the band
	 */
	@Override
	public void removeRuleSettingsFromBand(I_Rule rule) {
		ListIterator<BandRuleAtrributes> iterator = bandRuleAttributesList.listIterator();
		ArrayList<Integer> removeIndex = new ArrayList<Integer>();
		while (iterator.hasNext()) {
			int index = iterator.nextIndex();
			BandRuleAtrributes recentBRAttribute = iterator.next();
			if (recentBRAttribute.getRule().equals(rule)) {
				removeIndex.add(index); //write out all indexes how should be remove
				
			}
		}
		// Remove all Attributes
		for(Integer index : removeIndex) {
			bandRuleAttributesList.remove(index.intValue());
		}
		config.writejson();
	}
	
	
	public void setRuleByName(Rule[] rulearray) {
		ListIterator<BandRuleAtrributes> iterator = bandRuleAttributesList.listIterator();
		while (iterator.hasNext()) {
			BandRuleAtrributes currentAtrributes = iterator.next();
			currentAtrributes.setRuleByName(rulearray);
		}
	}
	

	/*
	 * returns all BandRuleAttributes of the band
	 */
	@Override
	public ArrayList<BandRuleAtrributes> getBandRuleAttributesList() {
		return bandRuleAttributesList;
	}

	/*
	 * set the FrequencySpectrum of the Band
	 */
	@Override
	public void setFrequencySpectrum(int[] frequencySpectrum) {
		this.frequencySpectrum = frequencySpectrum;
		chopFrequencySpectrum();
	}

	/*
	 * returns the Spectrum of the band
	 */
	@Override
	public int[] getBandSpectrum() {
		return bandSpectrum;
	}

	/*
	 * Chops the frequencySpetrum to get the bandspectrum
	 */
	private void chopFrequencySpectrum() {
		int minIndex = -2;
		int maxIndex = -2;
		
		//get under and upper index of section to be copied
		for (int i = 0; i < frequencySpectrum.length; i++) {
			if (frequencySpectrum[i] >= minFreq && minIndex == -2) {
				minIndex = i;
			}
			if (frequencySpectrum[i] > maxFreq && maxIndex == -2) {
				maxIndex = i-1;
			}
		}
		
		//Max is last index when spectrum does not reach max Freq.
		if(maxIndex == -2 && minIndex != -2) {
			maxIndex = frequencySpectrum.length;
		}
		
		
		//Copy section of frequency spectrum which fits to the band
		bandSpectrum = null;
		if(minIndex >= 0 && maxIndex >=  0) {
			bandSpectrum = Arrays.copyOfRange(frequencySpectrum, minIndex, maxIndex);
			System.out.println("im here");
		}
		
		
		//Tell Ueberwachung the new band data is ready
		ueberwachung.bandDataReady(this);
	}

	/*
	 * Set the power course of the band
	 * to calculate min., max. and average
	 */
	@Override
	public void setbandPower(double[] signal) {
		minPower(signal);
		maxPower(signal);
		avgPower(signal);
	}
	
	/*
	 * Get out min. power of the signal
	 */
	private void minPower(double[] signal) {
		if(signal != null) {
			double min = signal[0];
			//Go through all signal points and write out minimum
			for(int i = 0; i < signal.length; i++) {
				if(signal[i] < min) {
					min = signal[i];
				}
			}
			this.minPower = min;
		}
		else {
			this.minPower = 0;
		}
	}
	
	/*
	 * Get out max. power of the signal
	 */
	private void maxPower(double[] signal) {
		if(signal != null) {
			double max = signal[0];
			//Go through all signal points and write out maximum
			for(int i = 0; i < signal.length; i++) {
				if(signal[i] > max) {
					max = signal[i];
				}
			}
			this.maxPower = max;
		}else {
			this.maxPower = 0;
		}
	}
	
	/*
	 * Calculate average power of the signal
	 */
	private void avgPower(double[] signal) {
		if(signal != null) {
			double avg = 0;
			//Go through all signal points and add the together
			for(int i = 0; i < signal.length; i++) {
					avg += signal[i];
			}
			// Calculate average
			this.avgPower = avg/signal.length;
		}else {
			this.avgPower = 0;
		}
	}
	
	/*
	 * get min. power of band
	 */
	public double getMinPower() {
		return minPower;
	}

	/*
	 * get max. power of band
	 */
	public double getMaxPower() {
		return maxPower;
	}

	/*
	 * get average power of band
	 */
	public double getAvgPower() {
		return avgPower;
	}

}
