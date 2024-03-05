package frameGenerator;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import band.I_Band;
import band.PlotBandSettings;
import band.PlotBandSettingsController;
import channel.SignalChannel;
import controllers.Controller_Singleton;
import fft_Settings.PlotFftSettings;
import fft_Settings.PlotFftSettings2;
import fft_Settings.PlotFftSettings2Controller;
import fft_Settings.PlotFftSettingsController;
import path_Settings.PlotPathSettings;
import path_Settings.PlotPathSettingsController;
import realtime_view.Menu;
import realtime_view.PlotPanel;
import realtime_view.PlotPanelController;
import realtime_view.PlotSettingsPanel;
import realtime_view.PlotSettingsPanelController;
import realtime_view.RealTimeView;
import rule.I_Rule;
import rule.PlotRuleConfig;
import rule.PlotRuleController;
import rule.PlotRuleMessage;
import rule.Rule;
import spectrum.PlotSpectrumView;

public class FrameGenerator implements I_FrameGenerator {

	Controller_Singleton controller = Controller_Singleton.getInstance();

	public FrameGenerator() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void buildPlotPanelFrame() {
		final Frame plotPanelFrame = new Frame("Realtime Monitor");
		PlotPanelController plotPanelController = new PlotPanelController(this);
		controller.addController(plotPanelController);
		final realtime_view.PlotPanel plotPanelPanel = new PlotPanel(plotPanelController);
		plotPanelController.setView(plotPanelPanel);
		final Menu menu = new Menu();
		plotPanelFrame.setJMenuBar(menu.getMenuBar());
		plotPanelFrame.add(plotPanelPanel);
		plotPanelFrame.configPlotPanelFrame();

		// Just for debugging
		plotPanelPanel.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Main Panel lost Focus");

			}

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Main Panel gained Focus");

			}
		});
		plotPanelFrame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				System.out.println("Main Window lost Focus");
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				System.out.println("Main Window gained Focus");

			}
		});
	}

	@Override
	public void buildPlotSettingsFrame(I_Band band, I_Rule rule) {
		final Frame plotSettingsFrame = new Frame("Plot Settings");
		PlotSettingsPanelController plotSettingsPanelController = new PlotSettingsPanelController(plotSettingsFrame);
		controller.addController(plotSettingsPanelController);
		final PlotSettingsPanel plotSettingsPanel = new PlotSettingsPanel(plotSettingsPanelController);
		plotSettingsPanelController.setView(plotSettingsPanel);
		plotSettingsPanelController.setModel(band);
		plotSettingsPanelController.setModel(rule);
		plotSettingsFrame.add(plotSettingsPanel);
		plotSettingsFrame.configFrame();
	}

	@Override
	public void buildBandSettingsFrame() {
		final Frame plotBandSettingsFrame = new Frame("Band Settings");
		PlotBandSettingsController plotBandSettingsController = new PlotBandSettingsController();
		controller.addController(plotBandSettingsController);
		PlotBandSettings plotBandSettings = new PlotBandSettings(plotBandSettingsController);
		plotBandSettingsController.setView(plotBandSettings);
		plotBandSettingsFrame.add(plotBandSettings);
		plotBandSettingsFrame.configFrame();

	}

	@Override
	public void buildFftSettingsFrame() {
		final Frame plotFftSettingsFrame = new Frame("FFT Settings");
		final PlotFftSettingsController PlotFftSettingsController = new PlotFftSettingsController(plotFftSettingsFrame);
		controller.addController(PlotFftSettingsController);
		final PlotFftSettings plotFftSettingsPanel = new PlotFftSettings(PlotFftSettingsController);
		PlotFftSettingsController.setView(plotFftSettingsPanel);
		plotFftSettingsFrame.add(plotFftSettingsPanel);
		plotFftSettingsFrame.configFrame();

		plotFftSettingsFrame.setAlwaysOnTop(true); // Always puts the window at the front

	}

	@Override
	public void buildFftSettings2Frame(ArrayList<SignalChannel> channel) {
		final Frame plotFftSettings2Frame = new Frame("FFT Settings");
		final PlotFftSettings2Controller PlotFftSettings2Controller = new PlotFftSettings2Controller(
				plotFftSettings2Frame);
		controller.addController(PlotFftSettings2Controller);
		final PlotFftSettings2 plotFftSettings2Panel = new PlotFftSettings2(PlotFftSettings2Controller, channel);
		plotFftSettings2Frame.add(plotFftSettings2Panel);
		plotFftSettings2Frame.configFrame();
	}

	@Override
	public void buildPathSettingsFrame() {
		final Frame plotPathSettingsFrame = new Frame("Path Settings");
		PlotPathSettingsController pathSettingcontroller = new PlotPathSettingsController();
		controller.addController(pathSettingcontroller);
		PlotPathSettings plotPathSettingsPanel = new PlotPathSettings(pathSettingcontroller, plotPathSettingsFrame);
		pathSettingcontroller.setView(plotPathSettingsPanel);
		plotPathSettingsFrame.add(plotPathSettingsPanel);
		plotPathSettingsFrame.configFrame();

	}

	public void buildRuleFrame() {
		buildRuleFrame(null);

	}

	public void buildRuleFrame(I_Rule existingRule) {
		final Frame plotRuleFrame = new Frame("Rule Configurator"); // Frame
		final Rule rule;
		boolean editFlag;

		// Edit an existing rule
		if (existingRule == null) {
			editFlag = false;
			rule = new Rule(); // Model
		} else {
			editFlag = true;
			rule = (Rule) existingRule;
		}

		final PlotRuleController plotRuleController = new PlotRuleController(plotRuleFrame, editFlag); // Controller
		controller.addController(plotRuleController);
		final PlotRuleMessage plotRuleMessagePanel = new PlotRuleMessage(plotRuleController); // ViewPanel1
		final PlotRuleConfig plotRuleConfigPanel = new PlotRuleConfig(plotRuleController, plotRuleMessagePanel); // ViewPanel2
		plotRuleController.setModel(rule);
		plotRuleController.setView(plotRuleConfigPanel);
		plotRuleController.setView2(plotRuleMessagePanel);

		plotRuleFrame.add(plotRuleMessagePanel);
		plotRuleFrame.add(plotRuleConfigPanel);
		plotRuleFrame.configFrame();

		JTabbedPane tabPaneRule = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPaneRule.add("Rule Configuration", plotRuleConfigPanel);
		tabPaneRule.add("Message", plotRuleMessagePanel);
		plotRuleFrame.add(tabPaneRule);
		plotRuleController.updateView();
	}

	// TODO auf JFreeChart anpassen MVC aufheben
	@Override
	public void buildSpectrumFrame() {
		final Frame plotSpectrumFrame = new Frame("View Spectrum"); // Frame
		final PlotSpectrumView plotspectrumGUI = new PlotSpectrumView(); // View

		plotSpectrumFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				plotspectrumGUI.timer.stop();
			}
			});
		if (plotspectrumGUI.channelSizeViable) {
			plotSpectrumFrame.add(plotspectrumGUI);
			plotSpectrumFrame.configFrame();

		}
	}

	public void buildRealTimeViewFrame(I_Band band) {
		final Frame realTimeViewFrame = new Frame("Real Time View ");
		final RealTimeView realTimeView = new RealTimeView(band); // View

		realTimeViewFrame.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			realTimeView.timer.stop();
		}
		});
		realTimeViewFrame.add(realTimeView);
		realTimeViewFrame.configFrame();
	}

}
