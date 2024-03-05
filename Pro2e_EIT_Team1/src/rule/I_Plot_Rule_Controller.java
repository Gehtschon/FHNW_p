package rule;

import javax.swing.JPanel;

import controllers.I_Controller;

public interface I_Plot_Rule_Controller extends I_Controller {

	void setView2(JPanel panel);
	// needed to reference second panel
	
	void setRulePhoneTel(String name);
	// rule.setRulePhoneTel(String name);

	void setRulePhoneSMS(String name);
	// rule.setRulePhoneSMS(String name);

	void setRuleMail(String name);
	// rule.setRuleMail(String name);

	void setRuleMessage(String name);
	// rule.setRulePhoneTel(String name);

	void setRulePhoneTelState(boolean state);

	void setRulePhoneSMSState(boolean state);

	void setRuleMailState(boolean state);

	void setRuleName(String rule);
	// rule.setRuleName(String name);

	void setRuleType(I_Rule rule);
	// rule.setRuleType(I_Ruletype ruletype);

}
