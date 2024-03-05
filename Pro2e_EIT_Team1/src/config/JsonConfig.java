package config;

import band.Band;
import channel.Signal;
import channel.SignalChannel;
import rule.Rule;

public class JsonConfig implements I_JsonConfig {

	private SignalChannel[] channelarray;
	private Rule[] rulearray;
	private Band[] bandarray;
	private boolean gui = true;
	private int logInterval;
	private int eventExportTime;
	private String logPath;
	

	




	@Override
	public void setChannels(SignalChannel[] signalChannelsArray) {
		// TODO Auto-generated method stub
		this.channelarray = signalChannelsArray;
		System.out.println("Here ch3: "+ channelarray[3].getChannelName());
	}


	@Override
	public void setRules(Rule[] rulesArray) {
		// TODO Auto-generated method stub
		this.rulearray = rulesArray;
	}


	@Override
	public void setBands(Band[] bandsArray) {
		// TODO Auto-generated method stub
		this.bandarray = bandsArray;
	}


	@Override
	public SignalChannel[] getChannels() {
		// TODO Auto-generated method stub
		return channelarray;
	}


	@Override
	public Rule[] getRules() {
		// TODO Auto-generated method stub
		return rulearray;
	}


	@Override
	public Band[] getBands() {
		// TODO Auto-generated method stub
		return bandarray;
	}


	@Override
	public void setgui(Boolean status) {
		this.gui = status;
		
	}


	@Override
	public boolean getguistatus() {
		// TODO Auto-generated method stub
		return gui;
	}


	@Override
	public void setLogInterval(int interval) {
		this.logInterval = interval;
		
	}


	@Override
	public int getlogInterval() {
		// TODO Auto-generated method stub
		return logInterval;
	}


	@Override
	public void setEventExportTime(int time) {
		this.eventExportTime = time;
		
	}


	@Override
	public int getEventExportTime() {
		// TODO Auto-generated method stub
		return eventExportTime;
	}


	@Override
	public void setLogPath(String logPath) {
		// TODO Auto-generated method stub
		this.logPath = logPath;
	}


	@Override
	public String getLogPath() {
		// TODO Auto-generated method stub
		return logPath;
	}




}
