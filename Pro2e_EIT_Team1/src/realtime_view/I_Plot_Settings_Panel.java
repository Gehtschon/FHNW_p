package realtime_view;

import band.I_Band;
import rule.I_Rule;

public interface I_Plot_Settings_Panel {

	// View if button Setting in RealtimeMonitor is pressed

	int[] getNumberExceedances();
	/*
	 * place of array: 1 number of exceedances 2 /seconds filed
	 */

	void setNumberExceedances(int[] array);
	// set the textfileds

	int getAlertLevel();
	// get alertValue

	void setAlertLevel(int levelvalue);
	// set the value


	// Value of the current band/rule
	void update(I_Band band, I_Rule rule);
}
