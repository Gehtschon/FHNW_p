package rule;

import java.util.ArrayList;

import javax.swing.JFrame;

import band.Band;
import band.BandRuleAtrributes;
import band.I_Band;
import controllers.Controller_Singleton;
import ueberwachung.Ueberwachung_Singelton;

public class Rule_Creator_Singelton extends JFrame implements I_Rule_Creator {
	// Einzige Instanz der Klasse Rule_Creator
	private static Rule_Creator_Singelton INSTANCE_OF_RULE_CREATOR;

	// Private Constructor for Singelton Class Rule_Creator_Singelton
	private Rule_Creator_Singelton() {
	}

	/**
	 * Instanziert die Instanz der Singelton Klasse Ueberwachung_Singelton
	 */
	public synchronized static Rule_Creator_Singelton GetInstance() {
		if (INSTANCE_OF_RULE_CREATOR == null) {
			INSTANCE_OF_RULE_CREATOR = new Rule_Creator_Singelton();
		}
		return INSTANCE_OF_RULE_CREATOR;
	}

	// Lokale "Instanz" der Singelton Klasse Ueberwachung_Singelton
	private Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();

	/** 
	 * creates a new Rule with the recommended attributes. Adds created rule to the
	 * ruleList in class Ueberwachung_Singelton
	 */
	@Override
	public void createRule(String RuleName, Ruletype ruletype, String phoneTel, String phoneSMS, String mail,
			String message, boolean phoneTelState, boolean phoneSMSState, boolean mailState,
			ArrayList<Band>[] ruleBandTable) {
		Rule rule = new Rule();
		rule.setRuleName(RuleName);
		rule.setRuleType(ruletype);
		rule.setRulePhoneTel(phoneTel);
		rule.setRulePhoneSMS(phoneSMS);
		rule.setRuleMail(mail);
		rule.setRulePhoneTelState(phoneTelState);
		rule.setRulePhoneSMSState(phoneSMSState);
		rule.setRuleMailState(mailState);
		rule.setRuleMessage(message);
		rule.setRuleBandTable(ruleBandTable);
		rule.detectChannels();
		ueberwachung.addRuleToList(rule);
		System.out.println("added Rule to ruleList");
		
		for(int i = 0; i < ruleBandTable.length; i++) {
			for(int j = 0; j < ruleBandTable[i].size(); j++){
				int[] defaultExceedances = {50,50};
				ruleBandTable[i].get(j).addRuleSettingsToBand(new BandRuleAtrributes(defaultExceedances,500,rule));
			}
		}
		Controller_Singleton.getInstance().updateControllers();
		
	}
	public void createRule(I_Rule rule)	{
		//Editing rule add the rule with the new settings
		if(ueberwachung.getRuleList().contains(rule)) {
			ueberwachung.getRuleList().remove(rule);
		}
		ueberwachung.addRuleToList(rule);
		System.out.println("added Rule to ruleList");
		for(int i = 0; i < rule.getRuleBandTable().length; i++) {
			for(int j = 0; j < rule.getRuleBandTable()[i].size(); j++){
				int[] defaultExceedances = {50,50};
				rule.getRuleBandTable()[i].get(j).addRuleSettingsToBand(new BandRuleAtrributes(defaultExceedances,500,rule));
			}
		}

		Controller_Singleton.getInstance().updateControllers();
		

	}

	@Override
	public void removeRule(I_Rule rule) {
		if(ueberwachung.getRuleList().contains(rule)) {
			ueberwachung.getRuleList().remove(rule);
			Controller_Singleton.getInstance().updateControllers();
		}
	}

}