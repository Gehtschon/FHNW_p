package realtime_view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import band.Band;
import band.I_Band;
import rule.I_Rule;
import ueberwachung.Ueberwachung_Singelton;

public class PlotPanel extends JPanel implements I_PlotPanel, ActionListener {

	// Array with all Buttons on the right side of the frame
	private static JButton[] buttonArray = new JButton[] { new JButton("Spectrum View"), new JButton("new Rule"),
			new JButton("duplicate Rule"), new JButton("edit Rule"), new JButton("delete Rule") };

	// GridBagLayout
	private static Insets insets = new Insets(5, 5, 5, 5);
	private static GridBagConstraints buttonPanelConstraints = new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.NORTHEAST, GridBagConstraints.VERTICAL, insets, 5, 5);
	private static GridBagConstraints ruleTabLayout = new GridBagConstraints(0, 1, 4, 1, 1.0, 1.0,
			GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, insets, 5, 5);
	
	private List<I_Rule> ruleList = new ArrayList<I_Rule>();
	private I_Rule currentRule;
	
	private PlotPanelController plotPanelController;
	private JTabbedPane ruleTab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	
	
//	// Local "Instance of" Ueberwachung_Singelton
//	//HAS TO BE REMOVED IF STILL HERE, just for testing purpose
//	private static Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
//	

	public PlotPanel(PlotPanelController plotPanelController) {
		this.plotPanelController = plotPanelController;
		setPreferredSize(new Dimension(702, 400));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();

		setLayout(new GridBagLayout());
		buttonPanelBuilder();
		tabRuleBuilder();	
		ruleTab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(ruleList.size() > 0 && ruleTab.getSelectedIndex() > 0) {
					currentRule = ruleList.get(ruleTab.getSelectedIndex());
				}
			}
		});
	}

	private void buttonPanelBuilder() {
		// BoxLayout for the Buttons on the right side of the frame

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

		// Instantiate Buttons and add ActionListener
		for (int i = 0; i < buttonArray.length; i++) {
			if (i == 1) {
				buttonPanel.add(Box.createVerticalGlue());
			}
			buttonArray[i].setAlignmentX(LEFT_ALIGNMENT);
			buttonArray[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonArray[i].getMinimumSize().height));
			buttonArray[i].addActionListener(this);
			buttonPanel.add(buttonArray[i]);
			System.out.println("generated Button: " + buttonArray[i]);
		}

		// add buttonPanel to the PlotPanel
		add(buttonPanel, buttonPanelConstraints);

	}

	/*
	 * Creates the tab with the rules
	 */
	@Override
	public void tabRuleBuilder() {
		int index = ruleTab.getSelectedIndex();
		
		ruleTab.removeAll();
		//When not rule is created, then show a tab with a label "no rules created"
		if(ruleList.isEmpty()) {
			ruleTab.addTab("no rules created",new JPanel());
			add(ruleTab, ruleTabLayout);
		}
		ArrayList<ArrayList<BandPanel>> BandPanelperRule = new ArrayList<ArrayList<BandPanel>>(ruleList.size());
		for(int i = 0; i < ruleList.size(); i++) {
			JPanel panel = new JPanel();
			panel.setOpaque(true);
			//Go trough all band of the rule and add them to the panel
			ArrayList<BandPanel> bandPanels = new ArrayList<BandPanel>();
			ArrayList<Band>[] band = ruleList.get(i).getRuleBandTable();
			//Got through all band in this rule an create a bandPanel for it
			// (two for-loop needed because bands are sorted by channels)
			for(int j = 0; j < band.length; j++) {
				for(int k = 0; k < band[j].size() ; k++) {
					bandPanels.add(new BandPanel(band[j].get(k),ruleList.get(i),plotPanelController));
				}
			}
			BandPanelperRule.add(bandPanels);
			//Create new ruleTab with all the BandPanels of the current rule in it
			ruleTab.addTab(ruleList.get(i).getRuleName(),new RulePanel(bandPanels));
		}
		plotPanelController.setBandPanelList(BandPanelperRule);
		add(ruleTab, ruleTabLayout);
		
		//Select first tab when new created
		if(index == -1 && ruleTab.getTabCount() > 0) {
			index = 0;
		}
		//Index can be bigger than size of list, when deleting one rule!
		while(ruleList.size() <= index && index > 0) {
			index--;
		}
		//Select current rule to show
		if (index != -1 && ruleList.size() > 0) {
			currentRule = ruleList.get(index);
		}
		ruleTab.setSelectedIndex(index);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		System.out.println("Action Performd from Button: " + button.getText());
		if (button.getText().equals(buttonArray[0].getText())) { // Button Spectrum View
			plotPanelController.showSpectrum();
		}if (button.getText().equals(buttonArray[1].getText())) { // Button new rule
			plotPanelController.newRule();
		}if (button.getText().equals(buttonArray[2].getText())) { // Button duplicate rule
			plotPanelController.duplicateRule(currentRule);
		}if (button.getText().equals(buttonArray[3].getText())) { // Button edit rule
			plotPanelController.editRule(currentRule);
		}if (button.getText().equals(buttonArray[4].getText())) { // Button delete rule
			plotPanelController.deleteRule(currentRule);
		}
	}
	
	/**
	 * Update method for refreshing the view
	 * @param rules Array with the rules who should be displayed
	 */
	public void update(I_Rule[] rules) {
		ruleList =Arrays.asList(rules);
		tabRuleBuilder();
	}

}
