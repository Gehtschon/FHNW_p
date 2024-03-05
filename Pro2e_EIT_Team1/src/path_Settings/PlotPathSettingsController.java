package path_Settings;

import javax.swing.JPanel;

import config.Config_Singleton;
import config.WriteJson_Singelton;
import logger.Logger_Singleton;
import ueberwachung.Ueberwachung_Singelton;

public class PlotPathSettingsController implements I_Plot_Path_Settings_Controller {

	private PlotPathSettings view;

	public PlotPathSettingsController() {
	}	

	@Override
	public void setView(JPanel panel) {
		if(panel instanceof PlotPathSettings)
		{
			this.view = (PlotPathSettings)panel;
			update();
		}
		
	}
	
	/**
	 * Method to set config-path in model (normally called from view)
	 */	
	@Override
	public void setConfigPath(String path) {	
		WriteJson_Singelton writer = WriteJson_Singelton.getInstance();
		writer.setLogPath(path);
		view.setConfigPath(path);// Update view with current path
		Config_Singleton.getInstance().writejson();
	}
	
	/**
	 * Method to get the path from the model and set the config-path in the view
	 */
	@Override
	public void getConfigPath() {
		WriteJson_Singelton writer = WriteJson_Singelton.getInstance();
		view.setConfigPath(writer.getLogPath());

	}

	/**
	 * Method to set log-path in model (normally called from view)
	 */
	@Override
	public void setLogPath(String path) {
		Logger_Singleton logger = Logger_Singleton.getInstance();
		logger.setLogPath(path);
		view.setLogPath(path); // Update view with current path
		
	}

	/**
	 * Method to get the path from the model and set the log-path in the view
	 */
	@Override
	public void getLogPath() {
		Logger_Singleton logger = Logger_Singleton.getInstance();
		view.setLogPath(logger.getLogPath());
	}

	/*
	 * Method to set the log interval from view to model
	 */
	@Override
	public void setlogInterval(int value) {
		Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
		ueberwachung.setLogInterval(value);
		view.setlogInterval(value);
	}

	/*
	 * Method to get the log interval from the model and set them in the view
	 */
	@Override
	public void getlogInterval() {
		Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
		int interval = ueberwachung.getLogInterval();
		view.setlogInterval(interval);
	}

	/*
	 * Method to set the event interval from view to model
	 */
	@Override
	public void setEventInterval(int value) {
		Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
		ueberwachung.setEventExportTime(value);
		view.setEventInterval(value);
	}

	/*
	 * Method to get the event interval from the model and set them in the view
	 */
	@Override
	public void getEventInterval() {
		Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
		int interval = ueberwachung.getEventExportTime();
		view.setEventInterval(interval);
	}

	@Override
	public void setModel(Object model) {
		
	}

	/*
	 * update view
	 */
	@Override
	public void update() {
		getEventInterval();
		getlogInterval();
		getLogPath();
		getConfigPath();		
	}
}
