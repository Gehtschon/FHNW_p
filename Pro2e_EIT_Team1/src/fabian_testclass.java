import java.io.IOException;

import config.Config_Singleton;
import config.JsonConfig;
import config.WriteJson_Singelton;
import logger.Logger_Singleton;
import output.Output_Singleton;
import ueberwachung.Ueberwachung_Singelton;

public class fabian_testclass {
	
	

	// Test from Fabian Glutz will be removed in later version
	//JsonConfig testconfig = new JsonConfig("testerxyz");
	private Config_Singleton testconfig = Config_Singleton.getInstance();
	//SignalChannel channel = new SignalChannel("Channeltestgson", "Villmergen","TestChannel");
	//private WriteJson_Singelton jsonWriter = WriteJson_Singelton.getInstance();


	
	void testjson() {
//		testconfig.createtestchannels();
//		testconfig.createtestbandes();
//		testconfig.createtestrules();
//		testconfig.writejson();
		testconfig.readjson();
//		channel.setFrequency(123);
//		jsonWriter.writeConfigToJson(testconfig);
//		try {
//			jsonWriter.getObject();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		jsonWriter.getObject();
	}
}
