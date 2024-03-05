package controllers;

import java.util.ArrayList;

public class Controller_Singleton {

	private static Controller_Singleton INSTANCE_OF_CONTROLLER; // Only instance of type Controller_Singleton

	private ArrayList<I_Controller> controllerList = new ArrayList<I_Controller>();

	// Private Constructor for Singleton Class Ueberwachung_Singelton
	private Controller_Singleton() {

	}

	/**
	 * Create Instance when not already exist
	 */
	public synchronized static Controller_Singleton getInstance() {

		if (INSTANCE_OF_CONTROLLER == null) {
			INSTANCE_OF_CONTROLLER = new Controller_Singleton();
		}
		return INSTANCE_OF_CONTROLLER;
	}

	public void addController(I_Controller controller) {
		controllerList.add(controller);
	}

	public void removeController(I_Controller controller) {
		if (controllerList.contains(controller)) {
			controllerList.remove(controller);
		}
	}
	
	public void updateControllers() {
		if(controllerList.size() > 0) {
			for(int i= 0; i < controllerList.size(); i++) {
				controllerList.get(i).update();
			}
		}
	}

}
