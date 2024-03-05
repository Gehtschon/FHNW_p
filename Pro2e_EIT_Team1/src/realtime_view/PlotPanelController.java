package realtime_view;

import java.util.ArrayList;

import javax.swing.JPanel;

import band.BandRuleAtrributes;
import band.I_Band;
import frameGenerator.FrameGenerator;
import rule.I_Rule;
import rule.Rule_Creator_Singelton;
import ueberwachung.Ueberwachung_Singelton;


public class PlotPanelController implements I_PlotPanel_Controller {

	private PlotPanel plotPanelView;
	private FrameGenerator frameGenerator;
	private Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
	private Rule_Creator_Singelton ruleCreator = Rule_Creator_Singelton.GetInstance();
	private ArrayList<ArrayList<BandPanel>> bandPanelperRule;

	public PlotPanelController(FrameGenerator framegenerator) {
		this.frameGenerator = framegenerator;
	}

	@Override
	public void setView(JPanel panel) {
		this.plotPanelView = (PlotPanel) panel;
		plotPanelView.update(ueberwachung.getRuleArray());

	}

	@Override
	public void setModel(Object model) {
		// all needed models are singleton
		plotPanelView.update(ueberwachung.getRuleArray());

	}

	// for setting new rules in the view; has to be called when rulelist is changed
	@Override
	public void setRules(I_Rule[] rules) {
		if (rules != null) {
			plotPanelView.update(rules);
		}
	}

	@Override
	public void newRule() {
		frameGenerator.buildRuleFrame();
	}

	@Override
	public void duplicateRule(I_Rule rule) {
		if (rule != null) {
			ruleCreator.createRule(rule.getRuleName() + "(copy)", rule.getRuleType(), rule.getRulePhoneTel(),
					rule.getRulePhoneSMS(), rule.getRuleMail(), rule.getRuleMessage(), rule.getRulePhoneTelState(),
					rule.getRulePhoneSMSState(), rule.getRuleMailState(), rule.getRuleBandTable());
			plotPanelView.update(ueberwachung.getRuleArray());
		}
	}

	@Override
	public void editRule(I_Rule rule) {
		if (rule != null) {
			frameGenerator.buildRuleFrame(rule);
		}
	}

	@Override
	public void deleteRule(I_Rule rule) {
		ruleCreator.removeRule(rule);
		ueberwachung.removeRuleFromList(rule);
	}

	@Override
	public void showSpectrum() {
		frameGenerator.buildSpectrumFrame();
	}

	public void openSettings(I_Band band, I_Rule rule) {
		frameGenerator.buildPlotSettingsFrame(band, rule);
	}

	public void openView(I_Band band) {
		frameGenerator.buildRealTimeViewFrame(band);

	}

	/*
	 * Set ALertLevel 
	 */
	public void setAlertLevel(int level, I_Rule rule, I_Band band) {
		for (int i = 0; i < band.getBandRuleAttributesList().size(); i++) {
			if (band.getBandRuleAttributesList().get(i).getRule() == rule) {
				band.getBandRuleAttributesList().get(i).setAlertLevel(level);;
			}
		}
	}

	/*
	 * update view
	 */
	@Override
	public void update() {
		plotPanelView.update(ueberwachung.getRuleArray());
		updateBandPanels();
	}

	/*
	 * Update every bandPanel in the view
	 */
	private void updateBandPanels() {
		// Update each bandPanel
		if (bandPanelperRule != null) {
			for (int i = 0; i < bandPanelperRule.size(); i++) {
				for (int k = 0; k < bandPanelperRule.get(i).size(); k++) {
					bandPanelperRule.get(i).get(k).update();
				}
			}
		}
	}

	/*
	 * List with all BandPanels
	 */
	public void setBandPanelList(ArrayList<ArrayList<BandPanel>> bandPanelperRule) {
		if (bandPanelperRule != null) {
			this.bandPanelperRule = bandPanelperRule;
			updateBandPanels();
		}
	}
}
