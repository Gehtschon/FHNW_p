package rule;

import java.util.ArrayList;

import band.Band;
import channel.I_Channel;
import channel.SignalChannel;

public interface I_Rule {
	public void detectChannels();

	void setRuleName(String ruleName);

	String getRuleName();

	void setRuleType(Ruletype ruletype);

	Ruletype getRuleType();

	void setRulePhoneTel(String phoneTel);

	String getRulePhoneTel();

	void setRulePhoneSMS(String phoneSMS);

	String getRulePhoneSMS();

	void setRuleMail(String mail);

	String getRuleMail();

	void setRuleMessage(String message);

	String getRuleMessage();

	void setRulePhoneTelState(boolean phoneTelState);

	boolean getRulePhoneTelState();

	// wird per Tel alarm ausgelöstt?

	void setRulePhoneSMSState(boolean phoneSMSState);

	boolean getRulePhoneSMSState();

	//

	void setRuleMailState(boolean mailState);

	boolean getRuleMailState();
	
	void setRuleBandTable(ArrayList<Band>[] ruleBandTable);

	ArrayList<Band>[] getRuleBandTable();	
	public void removeChannelFromRuleBandTable(I_Channel channel);
	
}
