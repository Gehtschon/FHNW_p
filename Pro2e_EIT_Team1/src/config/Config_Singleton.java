package config;

import java.util.ArrayList;
import java.util.Iterator;

import band.Band;
import band.BandRuleAtrributes;
import channel.I_Channel;
import channel.SignalChannel;
import controllers.Controller_Singleton;
import logger.Logger_Singleton;
import rule.I_Rule;
import rule.Rule;
import ueberwachung.Ueberwachung_Singelton;

public class Config_Singleton implements I_Config {

	private SignalChannel[] channelarray;
	private Rule[] rulearray;
	private Band[] bandarray;
	private boolean gui = true;
	private int logInterval;
	private int eventExportTime;
	private String logPath;

	transient boolean startup = true;
	transient boolean readjson = false;

	// Only for test
	transient ArrayList<Band>[] bandList = new ArrayList[10];

	/**
	 * How to use Config_Singleton tmp = Config_Singleton.getInstance();
	 */
	private static transient Config_Singleton Config_Singleton; // only instance of Config
	private transient JsonConfig jsonConfig;
	private transient WriteJson_Singelton jsonWriter = WriteJson_Singelton.getInstance();
	private transient Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();

	// Get logger instance
	private transient Logger_Singleton logger = Logger_Singleton.getInstance();

	public synchronized static Config_Singleton getInstance() {

		if (Config_Singleton == null) {
			Config_Singleton = new Config_Singleton();
		}
		return Config_Singleton;
	}

	@Override
	public void readjson() {

		readjson = true;
		// TODO Auto-generated method stub

		jsonConfig = jsonWriter.getObject();
		System.out.println("readjson " + jsonConfig);
		if (jsonConfig != null) {

			this.channelarray = jsonConfig.getChannels();
			this.rulearray = jsonConfig.getRules();
			this.bandarray = jsonConfig.getBands();

			this.gui = jsonConfig.getguistatus();

			this.eventExportTime = jsonConfig.getEventExportTime();
			this.logInterval = jsonConfig.getlogInterval();
			this.logPath = jsonConfig.getLogPath();

			if (logPath != null) {
				if (logPath.isEmpty()) {
					logPath = logger.getLogPath();
				}
			} else {
				logPath = logger.getLogPath();
				startup = false;
			}

			if (logInterval == 0) {
				logInterval = ueberwachung.getLogInterval();
			}

			if (eventExportTime == 0) {
				eventExportTime = ueberwachung.getEventExportTime();
			}

			// check if soemthing is null if yes JSON File is Damaged

			if (channelarray != null && rulearray != null && bandarray != null) {
				Logger_Singleton.getInstance().setLogPath(logPath);

				ueberwachung.setEventExportTime(eventExportTime);
				ueberwachung.setLogInterval(logInterval);
				
				setListenerToChannels();

				setChannelsToBandarray();
				setBandsToChannel();

				setbandsToRule();

				setrulesToChannel();

				setRulesToBandruleAttributes();

				for (int i = 0; i < channelarray.length; i++) {
					ueberwachung.addChanelToList(channelarray[i]);

				}

				for (int i = 0; i < rulearray.length; i++) {
					ueberwachung.addRuleToList(rulearray[i]);

				}

				Controller_Singleton.getInstance().updateControllers();

			}

		} else {
			startup = false;
		}
		readjson = false;
	}

	private void setChannelsToBandarray() {
		// Read Bands from JSON and Set the channels to it
		// Set Channels to the band
		for (int i = 0; i < bandarray.length; i++) {
			// Set the Channelname from Band to a variable
			String Channelname = bandarray[i].getChannel().getChannelName();
			for (int j = 0; j < channelarray.length; j++) {
				// check if the Name from the band Channel equals a name from the Channelsarray
				if (Channelname.equals(channelarray[j].getChannelName())) {
					// the names are the same; Override the channel in the band and delete the old
					// band.
					bandarray[i].setChannel(channelarray[j]);
				} else {
					logger.freereport("Error Channel doesnt exist");
				}

			}
		}

	}

	private void setBandsToChannel() {
		// set bands from bandarray to channel
		for (int i = 0; i < bandarray.length; i++) {
			for (int j = 0; j < channelarray.length; j++) {
				// check if the name from bandarrayChannel equals a Channel from the
				// channelarray
				if (bandarray[i].getChannel().getChannelName().equals(channelarray[j].getChannelName())) {
					// its the same
					// set the band to the channel
					channelarray[j].addBand(bandarray[i]);
				}
			}
		}
	}

	// Check if soemthings is = null if true skip next things
	private void setbandsToRule() {
		// set bands from bandarray to rule
		for (int i = 0; i < rulearray.length; i++) {
			// get the rulebandtable from the current Rule
			ArrayList<Band>[] rulebandtable = rulearray[i].getRuleBandTable();
			for (int j = 0; j < rulebandtable.length; j++) {
				// get the size of the list
				int listsize = rulebandtable[j].size();
				for (int k = 0; k < listsize; k++) {
					// get the channel name from the band
					String channelname = rulebandtable[j].get(k).getChannel().getChannelName();
					// check if the channel exist (created new method for that)
					// search right band by channelname min and max freq ans set it to the right
					// place
					for (int p = 0; p < channelarray.length; p++) {
						if (channelname.equals(channelarray[p].getChannelName())) {
							// Search in the Channel the right band to replace it in the rulebandtable
							Band[] tband = channelarray[p].getBands();
							for (int z = 0; z < tband.length; z++) {
								if (tband[z].equals(rulebandtable[j].get(k)) == true) {
									rulebandtable[j].set(k, tband[z]);
									rulearray[i].setRuleBandTable(rulebandtable);
								}
							}
						}
					}

				}

			}
		}

	}

	private void setrulesToChannel() {
		// set rules to channels
		for (int i = 0; i < rulearray.length; i++) {
			// get the rulebandtable from the current Rule
			ArrayList<Band>[] rulebandtable = rulearray[i].getRuleBandTable();
			for (int j = 0; j < rulebandtable.length; j++) {
				// get the size of the list
				int listsize = rulebandtable[j].size();
				for (int k = 0; k < listsize; k++) {
					// get over the channelarray
					for (int p = 0; p < channelarray.length; p++) {
						if (rulebandtable[j].get(k).getChannel().getChannelName()
								.equals(channelarray[p].getChannelName())) {
							channelarray[p].setRule(rulearray[i]);

						}
					}
				}
			}
		}

	}

	private void setRulesToBandruleAttributes() {
		for (int i = 0; i < bandarray.length; i++) {
			bandarray[i].setRuleByName(rulearray);
		}
	}

	// Testfunction will be gfone in future release
	public void createtestchannels() {
		this.channelarray = new SignalChannel[10];
		for (int i = 0; i < 10; i++) {
			this.channelarray[i] = new SignalChannel(("Channel" + i), "lul", "test");
		}
	}

	// Testfunction will be gfone in future release
	public void createtestrules() {
		this.rulearray = new Rule[10];
		for (int i = 0; i < 10; i++) {
			rulearray[i] = new Rule();
			rulearray[i].setRuleName("Name is Rule" + i);
			rulearray[i].setRulePhoneSMS("077 422 90 40");
			channelarray[0].setRule(rulearray[i]);
			// rulearray[i].

			for (int j = 0; j < 10; j++) {
				bandList[j] = new ArrayList<Band>();
				bandList[j].add(bandarray[j]);
			}
			bandList[0].add(bandarray[1]);
			rulearray[i].setRuleBandTable(bandList);

		}
	}

	// Testfunction will be gfone in future release
	public void createtestbandes() {
		this.bandarray = new Band[10];
		for (int i = 0; i < 10; i++) {
			bandarray[i] = new Band(channelarray[(i)], i + 1, 100 - i);
			bandarray[i].setChannel(channelarray[i]);
		}

	}

	@Override
	public void writejson() {
		if (startup == false) {
			if (readjson != true) {
				rulearray = ueberwachung.getRuleArray();
				bandarray = ueberwachung.getBandArray();
				channelarray = ueberwachung.getChannelArray();
				logInterval = ueberwachung.getLogInterval();
				eventExportTime = ueberwachung.getEventExportTime();
				logPath = Logger_Singleton.getInstance().getLogPath();
				jsonWriter.writeConfigToJson(this);

			}

		} else {
			startup = false;
		}

	}

	public void setChannels(SignalChannel channel) {
		this.channelarray = new SignalChannel[10];
		channelarray[0] = channel;
	}

	public boolean getguistatus() {
		return gui;
	}

	public void setLogInterval(int interval) {
		this.logInterval = interval;

	}

	public void setEventExportTime(int time) {
		this.eventExportTime = time;

	}

	public void setLogPath(String logPath) {
		// TODO Auto-generated method stub
		this.logPath = logPath;
	}
	
	private void setListenerToChannels(){
		for (int i = 0; i < channelarray.length; i++) {
			channelarray[i].setlistener();
		}
	}

}
