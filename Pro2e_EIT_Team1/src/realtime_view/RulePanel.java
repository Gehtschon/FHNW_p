package realtime_view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import channel.I_Channel;

public class RulePanel extends JPanel {

	private ArrayList<BandPanel> bandPanels;
	private static Insets insets = new Insets(0, 0, 0, 0);

	/*
	 * COnstructor of RulePanel
	 * RulePanel takes all BandPanels an put them into on Panel (RulePanel)
	 */
	public RulePanel(ArrayList<BandPanel> bandPanels) {
		super();
		this.bandPanels = bandPanels;
		initPanel();

	}

	/*
	 * Initialisation of RulePanel
	 */
	private void initPanel() {
		setLayout(new BorderLayout());
		JPanel rulePanel = new JPanel();
		rulePanel.setLayout(new GridBagLayout());
		I_Channel currentChannelType = null; //Used for checking from witch channel the bandpanel is
		int col = 0;
		int row = 0;
		for (int i = 0; i < bandPanels.size(); i++) {
			if (currentChannelType != null) {
				if (currentChannelType != bandPanels.get(i).getBand().getChannel()) {
					// Fill line with empty fields, to start new line for an other channel 
					// (by a new channel the bandpanels start in a new row; the bands are sorted by channel in the rule)
					for (; col < 5; col++) {
						rulePanel.add(new JLabel(), new GridBagConstraints(col, row, 1, 1, 1, 1,
								GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets, 0, 0));
					}
				}
			}
			//Create new Line
			if (col == 5) {
				row++;
				col = 0;
			}
			//Add band to rulePanel
			rulePanel.add(bandPanels.get(i), new GridBagConstraints(col++, row, 1, 1, 1, 1,
					GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, insets, 0, 0));
			currentChannelType = bandPanels.get(i).getBand().getChannel();
		}
		// Fill up at least one line with empty cells and if only one row add a second
		// empty row (looks better)
		do {
			for (; col < 5; col++) {
				rulePanel.add(new JLabel(), new GridBagConstraints(col, row, 1, 1, 1, 1,
						GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, insets, 0, 0));
			}
			row++;
			col = 0;
		} while (row < 2);

		//Add rulePanel to a scrollPane
		JScrollPane scrollPane = new JScrollPane(rulePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rulePanel.setSize(scrollPane.getSize());
		add(scrollPane);
	}

	/*
	 * Update methode for rulePanel
	 */
	public void update(ArrayList<BandPanel> bandPanels) {
		if (this.bandPanels != bandPanels) {
			this.bandPanels = bandPanels;
			initPanel();
		}
	}
}
