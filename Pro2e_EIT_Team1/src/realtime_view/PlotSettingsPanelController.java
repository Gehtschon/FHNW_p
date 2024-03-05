package realtime_view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import band.BandRuleAtrributes;
import band.I_Band;
import controllers.Controller_Singleton;
import rule.I_Rule;

public class PlotSettingsPanelController implements I_Plot_Settings_Panel_Controller{

	private PlotSettingsPanel configView;
	private JFrame frame;
	private I_Band band;
	private I_Rule rule;
	private BandRuleAtrributes bra = new BandRuleAtrributes(null, 0, rule);
	
	public PlotSettingsPanelController(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void setView(JPanel panel) {
		// TODO Auto-generated method stub
		this.configView = (PlotSettingsPanel) panel;
	}

	@Override
	public void setModel(Object model) {
		if(model instanceof I_Band) {
			this.band = (I_Band)model;
		}else if(model instanceof I_Rule) {
			this.rule = (I_Rule)model;
			bra.setRule(rule);
		}
		update();
	}

	@Override
	public void applySettings(int[] numberExceedances, int level) {
		for(int i = 0; i < band.getBandRuleAttributesList().size(); i++) {
			if(band.getBandRuleAttributesList().get(i).getRule().equals(rule)) {
				band.getBandRuleAttributesList().get(i).setAlertLevel(level);
				band.getBandRuleAttributesList().get(i).setNumberExceedances(numberExceedances);
			}
		}
		
		Controller_Singleton.getInstance().updateControllers();
	}

	@Override
	public void setNumberExceedances(int[] numberExceedances) {
		bra.setNumberExceedances(numberExceedances);
	}

	@Override
	public void setAlertLevel(int level) { 
		bra.setAlertLevel(level);
		configView.update(band, rule);
	}
		
	public void closeWindow() {
		frame.dispose();

	}

	@Override
	public void update() {
		configView.update(band,rule);
	}

	

}
