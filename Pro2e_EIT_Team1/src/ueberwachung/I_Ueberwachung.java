package ueberwachung;

import java.util.ArrayList;

import band.Band;
import band.I_Band;
import channel.I_Channel;
import channel.SignalChannel;
import rule.I_Rule;
import rule.Rule;

public interface I_Ueberwachung {

	public void addChanelToList(SignalChannel channel);
	public void removeChannelFromList(I_Channel channel);
	/*
	 * Create Channel Object
	 * Adds Channel to Channel list
	 */

	public void addRuleToList(I_Rule rule);
	public void removeRuleFromList(I_Rule rule);
	/*
	 * Adds Rule to rule List
	 */
	public SignalChannel[] getChannelArray();
	public ArrayList<SignalChannel> getChannelList();
	/*
	 * gets Channel Object from Channel List
	 */
	public Rule[] getRuleArray();
	public ArrayList<I_Rule> getRuleList();
	/*
	 * gets Rule Object from Rule List
	 */
	public ArrayList<Band>[] getBandList();
	public Band[] getBandArray();
	public void removeBandFromChannel(Band band);

	boolean rulebroken(I_Channel channel, I_Rule rule);
	
	public void bandDataReady(I_Band band);



}
