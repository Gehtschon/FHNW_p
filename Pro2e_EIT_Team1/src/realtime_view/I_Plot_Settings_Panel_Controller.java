package realtime_view;

import controllers.I_Controller;

public interface I_Plot_Settings_Panel_Controller extends I_Controller {


	//Called from View when OK Button clicked
	void applySettings(int[] numberExceedances, int level);
	
	
	// set the textfileds in the view
	void setNumberExceedances(int[] array);


	// set the levelvalue in the view
	void setAlertLevel(int levelvalue);
	

}
