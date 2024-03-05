package band;

import config.Config_Singleton;
import rule.I_Rule;
import rule.Rule;

public class BandRuleAtrributes {
	private int[] numberExceedances;
	private int alertLevel;
	private transient I_Rule rule;
	private String rulename;
	
	
	

	public BandRuleAtrributes(int[] numberExceedances, int alertLevel, I_Rule rule) {
		this.numberExceedances = numberExceedances;
		this.alertLevel = alertLevel;
		this.rule = rule;
		if (rule != null) {
			Rule crule = (Rule) rule;
			this.rulename = crule.getRuleName();
		}
		Config_Singleton.getInstance().writejson();

	}

	public int[] getNumberExceedances() {
		return numberExceedances;
	}

	public void setNumberExceedances(int[] numberExceedances) {
		this.numberExceedances = numberExceedances;
		Config_Singleton.getInstance().writejson();
	}

	public int getAlertLevel() {
		return alertLevel;
	}

	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
		Config_Singleton.getInstance().writejson();
	}

	public I_Rule getRule() {
		return rule;
	}

	public void setRule(I_Rule rule) {
		this.rule = rule;
		this.rulename = rule.getRuleName();
		Config_Singleton.getInstance().writejson();
	}

	public void setRuleByName(Rule[] rulearray) {
		for (int i = 0; i < rulearray.length; i++) {
			if (rulename.equals(rulearray[i].getRuleName())) {
				this.rule = rulearray[i];
				break;
			}
		}
	}

}
