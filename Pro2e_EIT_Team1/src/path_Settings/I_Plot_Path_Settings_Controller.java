package path_Settings;

import controllers.I_Controller;

public interface I_Plot_Path_Settings_Controller extends I_Controller{
	
	void setConfigPath(String path);
	// set path

	void getConfigPath();
	// get path

	void setLogPath(String path);
	// set path

	void getLogPath();
	// get path

	void setlogInterval(int value);
	// set value

	void getlogInterval();
	// get value

	void setEventInterval(int value);
	// set value

	void getEventInterval();
	// get value

}
