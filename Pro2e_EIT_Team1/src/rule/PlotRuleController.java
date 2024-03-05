package rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import band.Band;
import band.I_Band;
import controllers.Controller_Singleton;
import ueberwachung.Ueberwachung_Singelton;

public class PlotRuleController implements I_Plot_Rule_Controller {
	// Local "Instance of" Ueberwachung_Singelton
	private static Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();

	private PlotRuleMessage messageView; // view
	private PlotRuleConfig configView; // view
	private JFrame frame;

	private Rule rule; // model
	private ArrayList<Band>[] bandList;
	private ArrayList<Band>[] availableBands;
	private ArrayList<Band>[] selectedBands;

	private boolean editFlag = false;

	// to close the window, added a reference to the frame
	public PlotRuleController(JFrame frame, boolean editFlag) {
		this.frame = frame;
		this.editFlag = editFlag;
	}

	@Override
	public void setView(JPanel panel) {
		this.configView = (PlotRuleConfig) panel; // funktioniert das?
		// System.out.println("PlotRuleConfig config view: " + configView);
		setBandList(); // gets the Band List with all existing bands
		buildAvailableBandList();
		buildSelectedBandList();
		configView.updateTables(availableBands, selectedBands);
		System.out.println("PlotRuleConfig config view: " + configView);
	}

	@Override
	public void setView2(JPanel panel) {
		this.messageView = (PlotRuleMessage) panel; // needed cause the ruleframe has 2 panels
	}

	@Override
	public void setModel(Object model) {
		this.rule = (Rule) model;
	}

	@Override
	public void setRulePhoneTel(String name) {
		System.out.println("Rule Tel Number set to " + name + " (controller)");
		rule.setRulePhoneTel(name);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRulePhoneSMS(String name) {
		System.out.println("Rule SMS Number set to " + name + " (controller)");
		rule.setRulePhoneSMS(name);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRuleMail(String name) {
		System.out.println("Rule Mail set to " + name + " (controller)");
		rule.setRuleMail(name);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRuleMessage(String name) {
		System.out.println("Rule Name set to " + name + " (controller)");
		rule.setRuleMessage(name);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRulePhoneTelState(boolean state) {
		System.out.println("Combobox Tel set to " + state + " (controller)");
		rule.setRulePhoneTelState(state);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRulePhoneSMSState(boolean state) {
		System.out.println("Combobox SMS set to " + state + " (controller)");
		rule.setRulePhoneSMSState(state);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRuleMailState(boolean state) {
		System.out.println("Combobox Mail set to " + state + " (controller)");
		rule.setRuleMailState(state);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRuleName(String name) {
		System.out.println("Rulename set to " + rule + " got selected (controller)");
		rule.setRuleName(name);
		// messageView.update(null);
		// configView.update(null);
	}

	@Override
	public void setRuleType(I_Rule rule) {
		System.out.println("Ruletype: " + rule + " got selected (controller)");
		// rule.setRuleType(rule); maybe change interface?
		// messageView.update(null);
		// configView.update(null);
	}

	public void closeWindow() {
		frame.dispose();

	}

	/**
	 * Method moves a Band from selectedBands to avialableBands
	 */
	public void switchToAvailableBands(Band band) {
		System.out.println("Button to available bands pressed (controller)");
		System.out.println("Band Object(Controller): " + band);

		// Itereate over selectedBands of all Channels
		for (int i = 0; i < selectedBands.length; i++) {
			ArrayList<Band> list = selectedBands[i];
			ListIterator<Band> iterator = list.listIterator();
			System.out.println("set iterator");

			while (iterator.hasNext()) {
				System.out.println("iterate trough selectedBandList");
				int currentIndex = iterator.nextIndex(); // Index uf current Object
				I_Band currentBand = iterator.next(); // current Object

				// Search the object in the selectedBands List. If found, add to availableBands
				// List and remove from selectedBands List
				if (currentBand.getChannel().getChannelName().equals(band.getChannel().getChannelName())) {
					if (currentBand.getMaxFreq() == band.getMaxFreq()
							&& currentBand.getMinFreq() == band.getMinFreq()) {
						System.out.println("found the correct band");
						availableBands[i].add(band); // add Band to availableBands
						selectedBands[i].remove(currentIndex); // remove Band from selectdBands
						System.out.println("updateTables");
						configView.updateTables(availableBands, selectedBands); // Display the modified Lists on Table
						return;
					} else {
						System.out.println("found no matching band"); // If no Band maches, nothing happens
					}

				}
			}
			iterator = null;

		}

	}

	// messageView.update(null);
	// configView.update(null);

	/**
	 * Method moves a Band from avialableBands to selectedBands
	 */
	public void switchToSelectedBands(Band band) {
		System.out.println("Button to selcted bands pressed (controller)");
		System.out.println("Band Object (Controller): " + band);

		// Itereate over availableBands of all Channels
		for (int i = 0; i < availableBands.length; i++) {
			ArrayList<Band> list = availableBands[i];
			ListIterator<Band> iterator = list.listIterator();
			System.out.println("set iterator");

			// Iterate over the list with the selected Bands of a Channel
			while (iterator.hasNext()) {
				System.out.println("iterate trough aviableBandList");
				int currentIndex = iterator.nextIndex(); // Index uf current Object
				I_Band currentBand = iterator.next(); // current Object

				// Search the object in the availableBands List. If found, add to selectedBands
				// List and remove from availableBands List
				if (currentBand.getChannel().getChannelName().equals(band.getChannel().getChannelName())) {
					if (currentBand.getMaxFreq() == band.getMaxFreq()
							&& currentBand.getMinFreq() == band.getMinFreq()) {
						System.out.println("found the correct band");
						selectedBands[i].add(band); // add Band to selectedBands
						availableBands[i].remove(currentIndex); // remove Band from availableBands
						System.out.println("updateTables");
						configView.updateTables(availableBands, selectedBands); // Display the modified Lists on Table
						return;
					} else {
						System.out.println("found no matching band"); // If no Band maches, nothing happens
					}

				}
			}
			iterator = null;

		}
	}

	/**
	 * returns if availableList has elements or not.
	 * 
	 * @return true if List has Elements
	 */
	public boolean testIfAvailableListHasElements() {
		for (int i = 0; i < availableBands.length; i++) {
			if (!(availableBands[i].isEmpty())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * returns if availableList has elements or not.
	 * 
	 * @return true if List has Elements
	 */
	public boolean testIfSelectedListHasElements() {
		for (int i = 0; i < selectedBands.length; i++) {
			if (!(selectedBands[i].isEmpty())) {
				return true;
			}
		}
		return false;
	}

	private void setBandList() {
		bandList = ueberwachung.getBandList();
	}

	/**
	 * Method used to initially build the List with the Available Bands
	 */
	private void buildAvailableBandList() {
		
		//If existing rule gets edited, just unselected Bands are listed in availableBands.
		//To build List availableBands, every Band on ueberwachung.bandList gets tested if contained in rule.ruleBandTable
		if (editFlag) {
			
			//Local copies of needed Rule Lists
			availableBands = ueberwachung.buildEmptyBandListArray();			
			ArrayList<Band>[] ruleBandTable = rule.getRuleBandTable();		//All Bands selected on the edited rule
			ArrayList<Band>[] bandList = ueberwachung.getBandList();			//All existing Bands

			for (int i = 0; i < bandList.length; i++) {
				ArrayList<Band> list = bandList[i];
				ListIterator<Band> listIterator = list.listIterator();
				while (listIterator.hasNext()) {
					Band comparedBand = (Band) listIterator.next();
					if (!(ruleBandTable[i].contains(comparedBand))) {
						availableBands[i].add(comparedBand);
					}
				}
			}

		} 
		//If new rule gets created all Bands are selected --> therefore the availableBands List is empty
		else {
			availableBands = ueberwachung.buildEmptyBandListArray();
		}

		// TODO Implement something to get not selected Bands
		// --> remove selectedList from band List
	}

	/**
	 * Method used to initially build the List with the selected Bands In a new
	 * Rule, all Bands are selected
	 */
	private void buildSelectedBandList() {
		if (editFlag) {
			selectedBands = ueberwachung.buildEmptyBandListArray();
			selectedBands = rule.getRuleBandTable();
		} else {
			selectedBands = ueberwachung.buildEmptyBandListArray();
			selectedBands = bandList;
		}

	}

	/**
	 * Use for test (to create a rule when OK button is clicked)
	 */
	public void createRule() {
		Rule_Creator_Singelton ruleCreator = Rule_Creator_Singelton.GetInstance();
		rule.setRuleBandTable(selectedBands);
		ruleCreator.createRule(rule);


	}
	
	public void updateView()	{
		if (editFlag) {
			configView.update(rule);
			messageView.update(rule);
		}
	}


	@Override
	public void update() {
		messageView.update(rule);
		configView.update(rule);
		// TODO Auto-generated method stub
		
	}

}
