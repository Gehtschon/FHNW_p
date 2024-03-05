package testing;

import logger.Logger_Singleton;
import rule.Rule;
import rule.Ruletype;

public class LoggerTest {
	public static void main(String[] args) {
		String txtTestPath = System.getProperty("user.home");
		boolean testfailure = false; // for checking if test has errors
		
		Logger_Singleton logger = Logger_Singleton.getInstance();
		
		// Testing setting negative frequency:
		String path = "\\user";
		logger.setLogPath(path);; // Set to a valid value
		//Read back the path and check if its equal to the set path
		if(logger.getLogPath() != path) {
			System.out.printf("Error by setting the path. Expected path, Actual path: %s, %s \n", path, logger.getLogPath());
			testfailure = true;
		}
		
		// Checking in txt-file if output is correct
		logger.setLogPath(txtTestPath);
		logger.rulebroken(new Rule());
		logger.freereport("Test message");
		logger.rulebroken(new Rule(), Ruletype.DANGER);
		
		
		// Give out if test was successful or not
		if(!testfailure) {
			System.out.printf("Logger successfully tested! Check txt-file!\n");
		}else {
			System.out.printf("Logger has errors!Check txt-file! \n");
		}
	}
}
