package controllers;

import javax.swing.JPanel;

public interface I_Controller {

	void setView(JPanel panel);
	void setModel(Object model);
	void update();

}