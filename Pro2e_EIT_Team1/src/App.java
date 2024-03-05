import config.Config_Singleton;
import controllers.Controller_Singleton;
import frameGenerator.FrameGenerator;
import mainController.MainController;

public class App {

	static fabian_testclass ftest = new fabian_testclass();

	public static void main(String[] args) {
		
		MainController controller = new MainController();
		Controller_Singleton.getInstance(); // Create controller
		
		Config_Singleton.getInstance().readjson();
		if (Config_Singleton.getInstance().getguistatus() == true) {
			frameGenerator.FrameGenerator frameGenerator = new FrameGenerator();
			frameGenerator.buildPlotPanelFrame();
		}

		// frameGenerator.buildBandSettingsFrame();
		// frameGenerator.buildPlotSettingsFrame();
		// frameGenerator.buildFftSettingsFrame();
		// frameGenerator.buildFftSettings2Frame();
		// frameGenerator.buildPathSettingsFrame();
		// frameGenerator.buildRuleFrame();
		// frameGenerator.buildSpectrumFrame();
		// frameGenerator.buildBandSettingsFrame();
		// frameGenerator.buildPlotSettingsFrame();
		// frameGenerator.buildFftSettingsFrame();
		// frameGenerator.buildFftSettings2Frame();
		// frameGenerator.buildPathSettingsFrame();
		// frameGenerator.buildRuleFrame();
		// frameGenerator.buildSpectrumFrame();
		// frameGenerator.buildRealTimeViewFrame(band);
		


		


		// ------------------------------------------------------------------
		// Test methods
		// ------------------------------------------------------------------

//		 Test.testBuildBandList();
		// Test.testcreateRule();

//		ftest.testjson();

//		Test.createTestChannel("RomanChannel1", "Home", "firstChannel");
//		Test.createTestChannel("RomanChannel2", "Bern", "seccondChannel");
//		
//		Test.createTestBands(0, 100, 200, 3);
//		Test.createTestBands(1, 500, 600, 2);
//		ftest.testjson();
	}

	/**
	 * This Method is used to test the process of creating a rule and submitting the
	 * affected channels to class channel.
	 */

}
