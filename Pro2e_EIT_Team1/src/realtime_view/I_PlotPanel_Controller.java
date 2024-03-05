package realtime_view;

import controllers.I_Controller;
import rule.I_Rule;

public interface I_PlotPanel_Controller extends I_Controller{
	
	public void setRules(I_Rule[] rules);
	
	public void showSpectrum();
	
	public void newRule();
	
	public void duplicateRule(I_Rule rule);
	
	public void editRule(I_Rule rule);
	
	public void deleteRule(I_Rule rule);	

}
