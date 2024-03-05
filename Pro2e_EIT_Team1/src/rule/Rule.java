package rule;

import java.util.ArrayList;
import java.util.ListIterator;

import band.Band;
import band.I_Band;
import channel.I_Channel;
import channel.SignalChannel;
import ueberwachung.Ueberwachung_Singelton;

public class Rule implements I_Rule {
	private String ruleName;
	private String phoneTel;
	private String phoneSMS;
	private String mail;
	private String message;

	private Ruletype ruletype;
	private boolean phoneTelState;
	private boolean phoneSMSState;
	private boolean mailState;

	private  ArrayList<Band>[] ruleBandTable;
	private transient I_Channel channel;

	private transient Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();

	/**
	 * Detects which Channels are affected by the rule
	 */
	@Override
	public void detectChannels() {
		int numOfChannels = ueberwachung.getChannelList().size();
		for (int i = 0; i < numOfChannels; i++) {
			channel = ueberwachung.getChannelList().get(i);
			if (ruleBandTable[i].isEmpty() != true) {
				channel.setRule(this);
				System.out.printf("Added rule %s to channel %s \n", this.ruleName, channel.getChannelName());
			}
			else {
				channel.deleteRule(this);
			}
		}
	}

	@Override
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;

	}

	@Override
	public String getRuleName() {
		return ruleName;
	}

	@Override
	public void setRuleType(Ruletype ruletype) {
		this.ruletype = ruletype;

	}

	@Override
	public Ruletype getRuleType() {
		return ruletype;
	}

	@Override
	public void setRulePhoneTel(String phoneTel) {
		this.phoneTel = phoneTel;

	}

	@Override
	public String getRulePhoneTel() {
		return phoneTel;
	}

	@Override
	public void setRulePhoneSMS(String phoneSMS) {
		this.phoneSMS = phoneSMS;

	}

	@Override
	public String getRulePhoneSMS() {
		return phoneSMS;
	}

	@Override
	public void setRuleMail(String mail) {
		this.mail = mail;

	}

	@Override
	public String getRuleMail() {
		return mail;
	}

	@Override
	public void setRuleMessage(String message) {
		this.message = message;

	}

	@Override
	public String getRuleMessage() {
		return message;
	}

	@Override
	public void setRulePhoneTelState(boolean phoneTelState) {
		this.phoneTelState = phoneTelState;

	}

	@Override
	public boolean getRulePhoneTelState() {
		return phoneTelState;
	}

	@Override
	public void setRulePhoneSMSState(boolean phoneSMSState) {
		this.phoneSMSState = phoneSMSState;

	}

	@Override
	public boolean getRulePhoneSMSState() {
		return phoneSMSState;
	}

	@Override
	public void setRuleMailState(boolean mailState) {
		this.mailState = mailState;

	}

	@Override
	public boolean getRuleMailState() {
		return mailState;
	}

	@Override
	public void setRuleBandTable(ArrayList<Band>[] ruleBandTable) {
		this.ruleBandTable = ruleBandTable;

	}
	@Override
	public void removeChannelFromRuleBandTable(I_Channel channel)	{
		boolean shiftArray = false;
		for (int i = 0; i < ruleBandTable.length; i++) {
			//Check if ArrayList contains a band from deleted channels
			if (ruleBandTable[i].get(0).getChannel().equals(channel) ) {
				ruleBandTable[i] = null;
				shiftArray = true;
			}
			//When ArreayList is deleted, shift following ArrayLists, if last index delete ArrayList
			if (shiftArray) {
				try {
					ruleBandTable[i+1] = ruleBandTable[i];
				} catch (Exception e) {
					ruleBandTable[i] = null;
				}

			}
		}
	}

	@Override
	public ArrayList<Band>[] getRuleBandTable() {
		return ruleBandTable;
	}
	


}
