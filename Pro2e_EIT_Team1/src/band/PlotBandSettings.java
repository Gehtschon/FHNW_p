package band;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import channel.SignalChannel;

public class PlotBandSettings extends JPanel implements I_Plot_Band_Settings {

	private JComboBox<String> CmbBxChannel = new JComboBox<String>();
	private JLabel lblMinFreq = new JLabel("min. Freq:");
	private JLabel lblMaxFreq = new JLabel("max. Freq:");
	private JLabel lblMinHz = new JLabel("[Hz]");
	private JLabel lblMaxHz = new JLabel("[Hz]");
	private JTextField txtMinFreq = new JTextField();
	private JTextField txtMaxFreq = new JTextField();
	private JTable tableBand = new JTable();
	private JScrollPane scrlPaneBand = new JScrollPane(tableBand);
	private JButton btnAdd = new JButton("Add");
	private JButton btnRemove = new JButton("Remove");
	private final String[] columnName = { "Channel", "minFreq", "maxFreq" };
	private DefaultTableModel tableModel = new DefaultTableModel(0, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// make the Cells not Editable
			return false;
		};
	};

	private final Insets insets = new Insets(5, 5, 5, 5);

	private SignalChannel currentChannel = null;
	private ArrayList<Band> bandList = new ArrayList<Band>(); // List with all the current Band
	private ArrayList<SignalChannel> channelList = new ArrayList<SignalChannel>(); // List with all the current channels
	private PlotBandSettingsController controller;

	public PlotBandSettings(PlotBandSettingsController controller) {
		this.controller = controller;
		setPreferredSize(new Dimension(500, 250));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();
		initPlotBandSettings();
		initTable();
		updateTableData();
	}

	private void initPlotBandSettings() {

		txtMinFreq.setPreferredSize(new Dimension(150, 20));
		txtMinFreq.setMinimumSize(new Dimension(100, 20));
		txtMaxFreq.setPreferredSize(new Dimension(150, 20));
		txtMaxFreq.setMinimumSize(new Dimension(100, 20));
		CmbBxChannel.setPreferredSize(new Dimension(150, 20));
		CmbBxChannel.setMinimumSize(new Dimension(100, 20));

		setLayout(new GridBagLayout());
		add(CmbBxChannel, new GridBagConstraints(0, 0, 2, 1, 0, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblMinFreq, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblMaxFreq, new GridBagConstraints(0, 2, 1, 1, 0, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtMinFreq, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtMaxFreq, new GridBagConstraints(1, 2, 1, 1, 0, 1, GridBagConstraints.PAGE_START,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(lblMinHz, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets,
				0, 0));
		add(lblMaxHz, new GridBagConstraints(2, 2, 1, 1, 0, 1, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, insets, 0, 0));
		add(scrlPaneBand, new GridBagConstraints(3, 0, 2, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				insets, 0, 0));
		add(btnAdd, new GridBagConstraints(3, 3, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets,
				0, 0));
		add(btnRemove, new GridBagConstraints(4, 3, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				insets, 0, 0));

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add Button generates new band

				// Check if the the field are not empty
				if (currentChannel != null && !txtMinFreq.getText().equals("") && !txtMaxFreq.getText().equals("")) {
					try {
						// Try to convert the Input form the textfield to an integer and check if min
						// freq is bigger than max freq
						if (Integer.parseInt(txtMaxFreq.getText()) >= Integer.parseInt(txtMinFreq.getText())) {
							// Check if both frequencies are positive
							if (Integer.parseInt(txtMaxFreq.getText()) >= 0
									&& Integer.parseInt(txtMinFreq.getText()) >= 0) {
								// Create a new Band
								createBand(currentChannel, Integer.parseInt(txtMinFreq.getText()),
										Integer.parseInt(txtMaxFreq.getText()));
							} else {
								//// Inform user that frequencies must be positive
								WarningMessageBox("Negative Frequency", "Frequencies must be positive");

							}

						} else {
							//// Inform user that min freq is bigger than max freq
							WarningMessageBox("Incorrect frequency",
									"minimal frequency is bigger than the maximal frequency");
						}
					} catch (Exception ex) {
						// When the input in the boxes are incorrect inform the user
						WarningMessageBox("Incorrect input", "The value in one of the freqcuency boxes is incorrect");
					}
				}
			}
		});

		btnRemove.addActionListener(new ActionListener() {
			// When Button "Remove" clicked read the selected row in the table and remove
			// the band
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableBand.getSelectedRow(); // return -1 when nothing selected
				if (row != -1) {
					// Get values from selected Band in JTable to remove the band
					removeBand(new Band(currentChannel, (int) (tableBand.getValueAt(row, 1)),
							(int) (tableBand.getValueAt(row, 2))));
				}
			}
		});

		CmbBxChannel.addItemListener(new ItemListener() {

			// When the Channel is changed over the combobox the bands of the selected
			// channel will be load in the table
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Change of the combobox; currentChannel gets updated
				int index = CmbBxChannel.getSelectedIndex(); // Attention-> return -1 when empty
				if (index >= 0 && index < channelList.size()) {
					currentChannel = channelList.get(index);
					System.out.println(currentChannel.getChannelName());
					updateTableData();
				}
			}
		});

	}

	// Create the table for the bands
	private void initTable() {
		tableModel.setColumnIdentifiers(columnName); // add header of the table
		tableBand.setModel(tableModel);
		updateTableData();
	}

	/**
	 * Opens a WarningMessageBox for the User
	 * 
	 * @param boxtitle string with the title of the box
	 * @param message  message that should written in the box
	 */
	void WarningMessageBox(String boxtitle, String message) {
		JOptionPane.showMessageDialog(this, message, boxtitle, JOptionPane.WARNING_MESSAGE);
	}

	// Methode to update the table with the current data
	private void updateTableData() {
		tableModel.setRowCount(0); // Clear the tableModel
		for (int i = 0; i < bandList.size(); i++) {
			if (bandList.get(i).getChannel() == currentChannel) {
				// When Channel class is created the Name of the channel can get
				tableModel.addRow(new Object[] { bandList.get(i).getChannel().getChannelName(),
						bandList.get(i).getMinFreq(), bandList.get(i).getMaxFreq() });
			}
		}
	}

	// Remove a band
	private void removeBand(Band band) {
		controller.removeBand(band, currentChannel);
		int RemoveIndex = -1;
		for (int i = 0; i < bandList.size(); i++) {
			if (bandList.get(i).equals(band)) {
				RemoveIndex = i;
			}
		}
		if (RemoveIndex != -1) {
			bandList.remove(RemoveIndex);
		}
		updateTableData();
	}

	/*
	 * Method to update the combobox with the channels
	 */
	private void updateChannel() {
		int sel = CmbBxChannel.getSelectedIndex();
		CmbBxChannel.removeAllItems(); // Clear the combobox
		// Add all channel from the channellist
		if (channelList.size() > 0) {
			for (int i = 0; i < channelList.size(); i++) {
				CmbBxChannel.addItem(channelList.get(i).getChannelName());
			}
		}
		// Selct first item there are any items in the combobox
		if (sel == -1 && channelList.size() > 0) {
			sel = 0;
		}
		CmbBxChannel.setSelectedIndex(sel);

	}

	/**
	 * Methode to create a new Band
	 */
	@Override
	public void createBand(SignalChannel signalChannel, int minFreq, int maxFreq) {
		Band band = new Band(signalChannel, minFreq, maxFreq);
		// Check if Band already exists
		if (bandList.contains(band)) {
			WarningMessageBox("Duplicate", "Band exists already");
		} else {
			controller.addBand(signalChannel, band); // Create new Band and give it back to the controller
		}

	}

	/**
	 * Update the view. Has to be called every time somethings changed
	 */
	@Override
	public void update(ArrayList<SignalChannel> channels) {
		bandList.clear();
		channelList = channels;
		if (channelList != null) {
			for (int i = 0; i < channels.size(); i++) {
				bandList.addAll(Arrays.asList(channels.get(i).getBands()));
			}
			updateChannel(); // Update Checkbox with the channels
			updateTableData(); // update the table with the current model
		}
	}
}
