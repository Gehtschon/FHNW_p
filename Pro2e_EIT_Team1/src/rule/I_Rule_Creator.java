package rule;

import java.util.ArrayList;

import band.Band;

public interface I_Rule_Creator {

	/**
	 * @param RuleName
	 * @param ruletype
	 * @param phoneTel
	 * @param phoneSMS
	 * @param mail
	 * @param message
	 * @param phoneTelState
	 * @param phoneSMSState
	 * @param mailState
	 * @param channel1Bands List with all the selected Bands of Channel 1
	 * @param channel2Bands List with all the selected Bands of Channel 2
	 */
	void createRule(String RuleName, Ruletype ruletype, String phoneTel, String phoneSMS, String mail, String message,
			boolean phoneTelState, boolean phoneSMSState, boolean mailState, ArrayList<Band>[] ruleBandTable);
	
	/**
	 * @param rule
	 * Removes an existing Rule from ruleList in Ueberwachung_Singelton
	 */
	void removeRule(I_Rule rule);
}
