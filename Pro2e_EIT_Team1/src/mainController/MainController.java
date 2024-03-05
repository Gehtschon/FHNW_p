package mainController;

import java.io.IOException;

import channel.SignalChannel;
import config.Config_Singleton;
import config.JsonConfig;
import config.WriteJson_Singelton;
import logger.Logger_Singleton;
import output.Output_Singleton;
import rule.I_Rule;
import ueberwachung.Ueberwachung_Singelton;

public class MainController implements I_MainController {

	private Logger_Singleton logger = Logger_Singleton.getInstance();
	private Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
	private Output_Singleton output = Output_Singleton.getInstance();
	private Config_Singleton config = Config_Singleton.getInstance();

	// Test from Fabian Glutz will be removed in later version
	//JsonConfig testconfig = new JsonConfig();
	//SignalChannel channel = new SignalChannel("Channeltestgson", "Villmergen","TestChannel");
	private WriteJson_Singelton jsonWriter = WriteJson_Singelton.getInstance();

	public MainController() {
	ueberwachung.setConfig(config);

		
	}

	@Override
	public void rulebroken(I_Rule rule) {
		// TODO
	}

}
