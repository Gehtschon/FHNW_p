package channel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import band.Band;
import band.I_Band;
import logger.Logger_Singleton;
import rule.I_Rule;
import rule.Rule;
import windowtype.Hanning;
import windowtype.I_Windowtype;
import windowtype.Rectwin;

public class SignalChannel implements I_Channel, SignalSourceListener {

	// Get logger instance
	private transient Logger_Singleton logger = Logger_Singleton.getInstance();

	private String channelName;
	private String channelLocation;
	private String channelDiscription;

	private int samples;
	private int overlap;
	private boolean samplesState;
	private boolean overlapState;
	private int frequency;

	private transient I_Windowtype windowtype;
	private transient Hanning hanning = new Hanning();
	private transient double[] spectralvalues;
	transient ArrayList<Band> bandlist = new ArrayList<Band>();
	transient ArrayList<Rule> rulelist = new ArrayList<Rule>();
	private transient Signal signal;
	private int[] frequencySpectrum;
	transient double[] validsamples;

	public SignalChannel() {

	}

	public SignalChannel(String channelName, String channelLocation, String channelDiscription) {
		// Constructor just setting things from FFTSettings2
		this.channelName = channelName;
		this.channelLocation = channelLocation;
		this.channelDiscription = channelDiscription;
		logger.freereport("Channel created ChannelName: " + channelName + " ChannelLocation: " + channelLocation
				+ " ChannelDiscription: " + channelDiscription);

		samples = 1024;
		setlistener();

	}

	@Override
	public void setOverlap(int value) {
		// TODO Auto-generated method stub
		this.overlap = value;

	}

	@Override
	public int getOverlap() {
		// TODO Auto-generated method stub
		return overlap;
	}

	@Override
	public void setSamples(int value) {
		// TODO Auto-generated method stub

		this.samples = value;
	}

	@Override
	public int getSamples() {
		// TODO Auto-generated method stub
		return samples;
	}

	@Override
	public void setFrequency(int value) {
		// TODO Auto-generated method stub
		this.frequency = value;
	}

	@Override
	public int getFrequency() {
		// TODO Auto-generated method stub
		return frequency;
	}

	@Override
	public void setWindow(I_Windowtype windowtype) {
		// TODO Auto-generated method stub
		this.windowtype = windowtype;
	}

	@Override
	public I_Windowtype getWindow() {
		// TODO Auto-generated method stub
		return windowtype;
	}

	@Override
	public void setChanelname(String name) {
		// TODO Auto-generated method stub
		this.channelName = name;
	}

	@Override
	public String getChannelName() {
		// TODO Auto-generated method stub
		return channelName;
	}

	@Override
	public void setChanelLocation(String name) {
		// TODO Auto-generated method stub
		this.channelLocation = name;

	}

	@Override
	public Band[] getBands() {
		// TODO Auto-generated method stub
		// transform the list into an array
		Band[] bandArrayObject = bandlist.toArray(new Band[] {});
		return bandArrayObject;
	}

	@Override
	public void removeBand(Band band) {
		// TODO Auto-generated method stub
		/*
		 * check if the band exists band exists: --> remove band from list band DOESNT
		 * exist: --> write and error into the log
		 */
		if (bandlist.contains(band) == true) {
			bandlist.remove(band);
		} else {
			logger.freereport("Error band does not exist on the current channel");
		}
	}

	@Override
	public void addBand(Band band) {
		// TODO Auto-generated method stub
		/*
		 * check if band exists band exists: --> write and error into the log band
		 * DOESNT exist: --> add band to list
		 */
		if (bandlist.contains(band) == true) {
			logger.freereport("Error band already exists");
		} else {
			bandlist.add(band);
		}

	}

	@Override
	public void setRule(Rule rule) {
		/*
		 * check if rule exists rule exists: --> write and error into the log rule
		 * DOESNT exist: --> add rule to list
		 */
		// TODO Auto-generated method stub
		if (rulelist.contains(rule) == true) {
			logger.freereport("Error rule already exists");
		} else {
			rulelist.add(rule);
		}
	}

	@Override
	public void deleteRule(Rule rule) {
		/*
		 * check if the rule exists rule exists: --> remove rule from list rule DOESNT
		 * exist: --> write and error into the log
		 */
		// TODO Auto-generated method stub
		if (rulelist.contains(rule) == true) {
			rulelist.remove(rule);
		} else {
			logger.freereport("Error rule does not exist on the current channel");
		}
	}

	@Override
	public Boolean ruleExist(Rule rule) {
		// TODO Auto-generated method stub
		return rulelist.contains(rule);
	}

	@Override
	public Boolean ruleExist(String name) {
		// TODO Auto-generated method stub
		Iterator<Rule> iterator = rulelist.iterator();
		while (iterator.hasNext()) {
			I_Rule currentRule = iterator.next();
			if (currentRule.getRuleName().equals(name)) {
				iterator = null;
				return true;
			}
		}
		iterator = null;
		return false;
	}

	@Override
	public I_Rule[] getRules() {
		// TODO Auto-generated method stub
		Rule[] ruleArray = rulelist.toArray(new Rule[] {});
		return ruleArray;
	}

	@Override
	public String getChanelLocation() {
		// TODO Auto-generated method stub
		return channelLocation;
	}

	@Override
	public void setChannelDescription(String name) {
		// TODO Auto-generated method stub
		this.channelDiscription = name;
	}

	@Override
	public String getChannelDescription() {
		// TODO Auto-generated method stub
		return channelDiscription;
	}

	@Override
	public void setOverlapState(boolean overlapState) {
		// TODO Auto-generated method stub
		this.overlapState = overlapState;
	}

	@Override
	public boolean getOverlapState() {
		// TODO Auto-generated method stub
		return overlapState;

	}

	@Override
	public void setSamplesState(boolean samplesState) {
		// TODO Auto-generated method stub
		this.samplesState = samplesState;
	}

	@Override
	public boolean getSamplesState() {
		// TODO Auto-generated method stub
		return samplesState;
	}

	@Override
	public void setFrequencySpectrum(int[] frequencySpectrum) {
		this.frequencySpectrum = frequencySpectrum;

		// Iterate over bandList Array to pass frequency spectrum to all bands
		if (bandlist != null) {
			ListIterator<Band> iterator = bandlist.listIterator();
			while (iterator.hasNext()) {
				Band band = (Band) iterator.next();
				band.setFrequencySpectrum(frequencySpectrum);
			}
		}

	}

	@Override
	public int[] getFrequencySpectrum() {
		if (frequencySpectrum != null) {
			return frequencySpectrum;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {

		// check if ref is the same
		if (obj == this) {
			return true;
		}

		// Check if compared object is a SignalChannel-object
		if (!(obj instanceof SignalChannel)) {
			return false;
		}

		// Typecast object in Band
		SignalChannel signalobj = (SignalChannel) obj;

		// Check if channel and frequency match
		return (this.getChannelName().equals(signalobj.getChannelName()));
	}

	@Override
	public void onSignalSamples(int channel, double[] samples, int len) {
		validsamples = new double[len];
		for (int i = 0; i < validsamples.length; i++) {
			validsamples[i] = samples[i];

		}
		spectralvalues = fft(validsamples);
		// overlap noch dazu rechnen
	}

	public double[] fft(double[] tastSignal) {

		double[] fenster = windowtype.window(samples);
		double[][] fenstersig = new double[samples][2];
		for (int k = 0; k < samples; k++) {
			fenstersig[k][0] = fenster[k] * tastSignal[k];
			fenstersig[k][1] = 0.0;
		}

		double[][] trafoSignal = FFT.fft(fenstersig);

		double pw = 0;
		for (int p = 0; p < samples; p++) {
			pw += (Math.pow(fenster[p], 2) / samples);
		}

		double[] leistungsdispek = FFT.absSqr(trafoSignal);

		double[][] leistung = new double[leistungsdispek.length / 2][2];
		for (int s = 0; s < (leistung.length / 2); s++) {
			leistung[s][0] = ((1 / (samples * pw)) * 1 / samples * leistungsdispek[s]);
			leistung[s][1] = ((1 / (samples * pw)) * 1 / samples * leistungsdispek[(leistungsdispek.length / 2) + s]);
		}

		double[][] einseitig = FFT.two2oneSided(leistung);

		double[] ein = new double[einseitig.length];
		for (int j = 0; j < einseitig.length; j++)
			ein[j] = einseitig[j][0];

		return ein;
	}

	public double[] getValidsamples() {

		return validsamples;
	}

	public double[] getSpectralvalues() {
		return spectralvalues;
	}

	public void setlistener() {
		signal = new Signal();
		signal.registerListener(this);
		windowtype = hanning;

	}

}
