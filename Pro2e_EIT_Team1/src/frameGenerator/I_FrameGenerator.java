package frameGenerator;

import java.util.ArrayList;

import band.I_Band;
import channel.SignalChannel;
import rule.I_Rule;

public interface I_FrameGenerator {
	void buildPlotPanelFrame();

	void buildPlotSettingsFrame(I_Band band, I_Rule rule);
	// build frame for PlotSettings
	// add PlotSettingsPanel
	// call configFrame()

	void buildBandSettingsFrame();
	// build frame for bandSettings
	// add bandSettingsPanel
	// call configFrame()

	void buildFftSettingsFrame();
	// build frame for FFTSettings
	// add FFTSettingsPanel
	// call configFrame()

	void buildFftSettings2Frame(ArrayList<SignalChannel> channel);
	// build frame for FFTSettings2
	// add FFTSettings2Panel
	// call configFrame()

	void buildPathSettingsFrame();
	// build frame for PathSettings
	// add PathSettingsPanel
	// call configFrame()

	void buildRuleFrame();
	// build frame for RuleConfig & RuleMessage
	// add RuleConfigPanel & RuleMessage Panel
	// call configFrame()

	void buildSpectrumFrame();
	// build frame for Spectrum
	// add SpectrumPanel
	// call configFrame()
}
