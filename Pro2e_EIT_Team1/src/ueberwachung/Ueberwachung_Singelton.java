package ueberwachung;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import band.Band;
import band.BandRuleAtrributes;
import band.I_Band;
import channel.I_Channel;
import channel.SignalChannel;
import config.Config_Singleton;
import logger.Logger_Singleton;
import output.Output_Singleton;
import rule.I_Rule;
import rule.Rule;
import controllers.Controller_Singleton;

public class Ueberwachung_Singelton implements I_Ueberwachung {
	private static Ueberwachung_Singelton INSTANCE_OF_UEBERWACHUNG; // Einzige Instanz der Klasse Ueberwachung_Singelton

	// List's with all Channels, Rules and Bands
	private static ArrayList<SignalChannel> channelList = new ArrayList<>();
	private static ArrayList<I_Rule> ruleList = new ArrayList<>();
	private ArrayList<Band>[] bandList; // Array has to be created in method, because Array changes size
	private int logInterval = 60; // Log-Intervall in seconds (default = 60s)
	private int eventExportTime = 60; // Event-Export in seconds (default = 60 s)
	private Timer logTimer = new Timer();

	// local Instance of Output_Singelton
	private Output_Singleton outputClass = Output_Singleton.getInstance();

	// local Instance of Logger_Singleton
	private Logger_Singleton logger = Logger_Singleton.getInstance();

	// local Instance of Config_Singleton
	private Config_Singleton config;

	// Private Constructor for Singelton Class Ueberwachung_Singelton
	private Ueberwachung_Singelton() {
		startTimer();
	}

	/**
	 * Instanziert die Instanz der Singelton Klasse Ueberwachung_Singelton
	 */
	public synchronized static Ueberwachung_Singelton getInstance() {

		if (INSTANCE_OF_UEBERWACHUNG == null) {
			INSTANCE_OF_UEBERWACHUNG = new Ueberwachung_Singelton();
		}
		return INSTANCE_OF_UEBERWACHUNG;
	}

	public void setConfig(Config_Singleton config) {
		this.config = config;
	}

	/**
	 * builds the bandList with all existing Bands in all Channels The bandList is
	 * an Array with an ArrayList for each Channel The method has to be called if a
	 * new Channel is built or a Channel is deleted.
	 */
	private void buildBandList() {

		// initializing Array with ArrayLists
		/*
		 * int numberOfChannels = channelList.size(); bandList = new
		 * ArrayList[numberOfChannels]; for (int i = 0; i < numberOfChannels; i++) {
		 * bandList[i] = new ArrayList<I_Band>(); }
		 */
		bandList = buildEmptyBandListArray();
		// Fill Array with all band objects
		for (int i = 0; i < bandList.length; i++) {
			Band[] bands = channelList.get(i).getBands();
			for (int j = 0; j < bands.length; j++) {
				bandList[i].add(bands[j]);
			}
		}

	}

	@Override
	public void addChanelToList(SignalChannel channel) {
		channelList.add(channel); // Adding new channel Object to channelList
		System.out.printf("added Channel %s to channelList \n", channel.getChannelName());
		buildBandList();
		config.writejson();
	}

	@Override
	public void removeChannelFromList(I_Channel channel) {
		if (channelList.contains(channel)) {
			channelList.remove(channel);
			buildBandList(); // rebuild bandList without the bands of the deleted channel

			System.out.printf("removed Channel %s from channelList \n", channel.getChannelName());
			channel = null;
			config.writejson();
		}
		// Iterate over ruleList to remove bands from all rules
		ListIterator<I_Rule> itr = ruleList.listIterator();
		while (itr.hasNext()) {
			I_Rule rule = (I_Rule) itr.next();
			rule.removeChannelFromRuleBandTable(channel);
		}
		
		for (int i = 0; i < bandList.length; i++) {
			Iterator<Band> banditr = bandList[i].iterator();
			while (banditr.hasNext()) {
				Band band = (Band) banditr.next();
				if (band.getChannel().equals(channel)) {
					bandList[i].remove(band);
				}
			}
		}

		Controller_Singleton.getInstance().updateControllers();

	}

	@Override
	public void addRuleToList(I_Rule rule) {
		ruleList.add(rule); // Adding rule new Object to ruleList
		System.out.println("temporary test");
		config.writejson();
	}

	@Override
	public void removeRuleFromList(I_Rule rule) {
		if (ruleList.contains(rule) == true) {
			ruleList.remove(rule);

			// Iterate over channelList to remove rule from all channels
			ListIterator<SignalChannel> itr = channelList.listIterator();
			while (itr.hasNext()) {
				SignalChannel signalChannel = (SignalChannel) itr.next();
				signalChannel.deleteRule((Rule) rule);
			}

			System.out.printf("removed rule %s from ruleList \n", rule.getRuleName());
			config.writejson();
		} else {
			logger.freereport("Error rule does not exist on the current channel");
		}

	}

	@Override
	public void removeBandFromChannel(Band band) {
		I_Channel channel = band.getChannel();
		channel.removeBand(band);
		buildBandList(); // rebuild bandList without the band
		config.writejson();
	}

	@Override
	public SignalChannel[] getChannelArray() {
		Object[] channelArrayObject = channelList.toArray();
		SignalChannel[] channelArray = new SignalChannel[channelArrayObject.length];
		for (int i = 0; i < channelArray.length; i++) {
			channelArray[i] = (SignalChannel) channelArrayObject[i];
		}
		return channelArray;
	}

	@Override
	public Rule[] getRuleArray() {
		Object[] ruleArrayObjects = ruleList.toArray();
		Rule[] ruleArray = new Rule[ruleArrayObjects.length];
		for (int i = 0; i < ruleArray.length; i++) {
			ruleArray[i] = (Rule) ruleArrayObjects[i];
		}
		return ruleArray;
	}

	@Override
	public boolean rulebroken(I_Channel channel, I_Rule rule) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<SignalChannel> getChannelList() {
		return channelList;
	}

	@Override
	public ArrayList<I_Rule> getRuleList() {
		return ruleList;
	}

	@Override
	public ArrayList<Band>[] getBandList() {
		buildBandList();
		return bandList;
	}

	/**
	 * Implement method in Interface if successfully used in PlotRuleController
	 * 
	 * @return empty BandListArray with the length of the actual number of channels
	 */
	public ArrayList<Band>[] buildEmptyBandListArray() {
		int numberOfChannels = channelList.size();
		ArrayList<Band>[] bandListArray = new ArrayList[numberOfChannels];
		for (int i = 0; i < numberOfChannels; i++) {
			bandListArray[i] = new ArrayList<Band>();
		}
		return bandListArray;

	}

	@Override
	public void bandDataReady(I_Band band) {
		ruleObserver(band);
	}

	private void ruleObserver(I_Band band) {
		ListIterator<I_Rule> listIterator = ruleList.listIterator();

		// Iterates over all Rules to look if Band is on rule
		while (listIterator.hasNext()) {
			I_Rule rule = (I_Rule) listIterator.next();
			ArrayList<Band>[] ruleBandTable = rule.getRuleBandTable();

			// Iterate over ruleBandTable (All Bands on a rule sorted by channels)
			for (int i = 0; i < ruleBandTable.length; i++) {
				ArrayList<Band> bandList = ruleBandTable[i];
				ListIterator<Band> bandListIterator = bandList.listIterator();

				while (bandListIterator.hasNext()) {
					Band bandOnRule = (Band) bandListIterator.next();
					if (bandOnRule.equals(band)) {
						compareBands(band, bandOnRule, rule);
					}
				}
			}
		}
	}

	private void compareBands(I_Band band, I_Band bandOnRule, I_Rule rule) {
		int[] currentBandSpectrum = band.getBandSpectrum(); // Current Pegels from FFT in specific band

		// Get BandRuleAttributes of the Band
		ArrayList<BandRuleAtrributes> bandRuleAttributesList = band.getBandRuleAttributesList();
		ListIterator<BandRuleAtrributes> BRAIterator = bandRuleAttributesList.listIterator();

		while (BRAIterator.hasNext()) {
			BandRuleAtrributes bandRuleAtrributes = (BandRuleAtrributes) BRAIterator.next();
			I_Rule bandRule = bandRuleAtrributes.getRule();
			if (bandRule.equals(rule)) {
				int alertLevel = bandRuleAtrributes.getAlertLevel();

				// Iterate over BandSpectrum

				for (int i = 0; i < currentBandSpectrum.length; i++) {
					if (currentBandSpectrum[i] > alertLevel) {
						triggerAlert(rule);
					}
				}
			}

		}

	}

	/*
	 * Start timer to log
	 */
	private void startTimer() {
		TimerTask task = new TimerTask() {
			public void run() {
				logging();
			}
		};

		logTimer.schedule(task, (long) logInterval * 1000, (long) logInterval * 1000);
	}

	/*
	 * Create log report for each band
	 */
	private void logging() {
		Logger_Singleton logger = Logger_Singleton.getInstance();
		// Go trough all bands and create log
		for (SignalChannel channel : channelList) {
			for (Band band : channel.getBands()) {
				logger.freereport(String.format("channel name|band area|min|max|avg: %s,%d-%d [Hz],%.2f,%.2f,%.2f",
						channel.getChannelName(), band.getMinFreq(), band.getMaxFreq(), band.getMinPower(),
						band.getMaxPower(), band.getAvgPower()));
			}
		}
	}

	/*
	 * Set log interval in seconds
	 */
	public void setLogInterval(int logInterval) {
		this.logInterval = logInterval;
		Config_Singleton.getInstance().writejson();
		startTimer();
	}

	/*
	 * Get log interval in seconds
	 */
	public int getLogInterval() {
		return this.logInterval;
	}

	private void triggerAlert(I_Rule rule) {
		if (rule.getRulePhoneTelState() == true) {
			outputClass.phoneCall(rule.getRulePhoneTel(), rule.getRuleMessage());
		}
		if (rule.getRulePhoneSMSState() == true) {
			outputClass.sendSms(rule.getRulePhoneSMS(), rule.getRuleMessage());
		}
		if (rule.getRuleMailState() == true) {
			outputClass.sendEmail(rule.getRuleMail(), rule.getRuleType().toString(), rule.getRuleMessage());
		}
		logger.rulebroken(rule, rule.getRuleType());
	}

	/*
	 * Set event-export time in seconds
	 */
	public int getEventExportTime() {
		return eventExportTime;
	}

	/*
	 * Get event-export time in seconds
	 */
	public void setEventExportTime(int value) {
		this.eventExportTime = value;
		Config_Singleton.getInstance().writejson();
	}

	@Override
	public Band[] getBandArray() {
		// TODO Auto-generated method stub
		// Use local method getChannelArray
		// loop over Channelarray and add bands to list
		// list to array
		// return array
		SignalChannel[] channelArraytemp = this.getChannelArray();
		ArrayList<Band> bandList = new ArrayList<>();
		for (int i = 0; i < channelArraytemp.length; i++) {
			Band[] channelBands = channelArraytemp[i].getBands();
			for (int k = 0; k < channelBands.length; k++) {
				bandList.add(channelBands[k]);
			}
		}

		Object[] object = bandList.toArray();
		Band[] bands = new Band[object.length];
		for (int i = 0; i < bands.length; i++) {
			bands[i] = (Band) object[i];
		}

		return bands;
	}

}
