package config;

import band.Band;
import channel.SignalChannel;
import rule.Rule;

//protected SignalChannel[] channelarray;
//protected Rule[] rulearray;
//protected Band[] bandarray;
//private boolean gui = true;
//private int logInterval;
//private int eventExportTime;
//private String logPath;
public interface I_JsonConfig {

	void setChannels(SignalChannel[] signalChannelsArray);
	
	void setRules(Rule[] rulesArray);
	
	void setBands(Band[] bandsArray);
	
	SignalChannel[] getChannels();
	
	Rule[] getRules();
	
	Band[] getBands();
	
	void setgui(Boolean status);
	boolean getguistatus();
	
	
	void setLogInterval(int interval);
	int getlogInterval();
	
	void setEventExportTime(int time);
	int getEventExportTime();
	
	void setLogPath(String logPath);
	String getLogPath();
}
