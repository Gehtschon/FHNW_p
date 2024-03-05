package rule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import band.Band;
import band.I_Band;

public class PlotRuleConfig extends JPanel implements I_Plot_rule_config {

	private PlotRuleController plotRuleController; // controller
	private PlotRuleMessage plotRuleMessage; // view

	// -----------------------------
	// Gui Elements
	// -----------------------------

	// Labels
	private JLabel lblRuleName = new JLabel("Rule Name: ");
	private JLabel lblRuleType = new JLabel("Type: ");
	private JLabel lblAvailableBands = new JLabel("Available Bands: ");
	private JLabel lblSelectedBands = new JLabel("Selected Bands: ");

	// Textfields
	private JTextField txtRuleName = new JTextField();

	// ComboBox
	private JComboBox cmbBxRuleType = new JComboBox(Ruletype.values());

	// Table and related Elements
	private JTable tableAvailableBands = new JTable();
	private JTable tableSelectedBands = new JTable();
	private static Object[] columns = { "Channel", "min f", "max f", "" };
	private static Object[] row = new Object[4];
	private static DefaultTableModel modelAvailable = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			// make the Cells not Editable
			return false;
		};
	};
	private static DefaultTableModel modelSelected = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			// make the Cells not Editable
			return false;
		};
	};
	private JScrollPane scrlAvailableBands = new JScrollPane(tableAvailableBands);
	private JScrollPane scrlSelectedBands = new JScrollPane(tableSelectedBands);

	// Buttons
	private JButton btnToSelectedBands = new JButton(">>");
	private JButton btnToAvailableBands = new JButton("<<");
	private JButton btnOK = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");

	// ---------------------------

	private final Insets insets = new Insets(5, 5, 5, 5);

	public PlotRuleConfig(PlotRuleController plotRuleController, PlotRuleMessage plotRuleMessage) {
		this.plotRuleController = plotRuleController;
		this.plotRuleMessage = plotRuleMessage;
		setPreferredSize(new Dimension(500, 300));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();

		initPlotRuleConfig();

	}

	private void initPlotRuleConfig() {

		initTables();

		setLayout(new GridBagLayout());
		add(lblRuleName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtRuleName, new GridBagConstraints(1, 0, 2, 1, 1.0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(lblRuleType, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(cmbBxRuleType, new GridBagConstraints(4, 0, 1, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(lblAvailableBands, new GridBagConstraints(0, 1, 2, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(lblSelectedBands, new GridBagConstraints(3, 1, 2, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(scrlAvailableBands, new GridBagConstraints(0, 2, 2, 3, 2.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0));
		add(scrlSelectedBands, new GridBagConstraints(3, 2, 2, 3, 2.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0));
		add(btnToSelectedBands, new GridBagConstraints(2, 3, 1, 1, 0, 1.0, GridBagConstraints.SOUTH,
				GridBagConstraints.NONE, insets, 0, 0));
		add(btnToAvailableBands, new GridBagConstraints(2, 4, 1, 1, 0, 1.0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, insets, 0, 0));
		add(btnOK, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0,
				0));
		add(btnCancel, new GridBagConstraints(3, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));

		cmbBxRuleType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO implement
				System.out.println("Selcted Item is: " + cmbBxRuleType.getSelectedItem()); // Warning! gets called twice
				plotRuleController.setRuleType(null /* ? */);
			}
		});
		btnToSelectedBands.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (plotRuleController.testIfAvailableListHasElements()) {
					System.out.println("Button to selected Bands got clicked");

					int selectedRow = tableAvailableBands.getSelectedRow();
					if (selectedRow >= 0) {
						Band band = (Band) modelAvailable.getValueAt(selectedRow, 3);
						modelAvailable.removeRow(selectedRow);
						plotRuleController.switchToSelectedBands(band);

					}

				} else {
					System.out.println("No item to move in TableAvialableBands");
				}
			}

		});
		btnToAvailableBands.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO implement
				if (plotRuleController.testIfSelectedListHasElements()) {
					System.out.println("Button to available Bands pressed (View)");

					int selectedRow = tableSelectedBands.getSelectedRow();
					if (selectedRow >= 0) {
						Band band = (Band) modelSelected.getValueAt(selectedRow, 3);
						modelSelected.removeRow(selectedRow);
						plotRuleController.switchToAvailableBands(band);
					}

				} else {
					System.out.println("No item to move in TableSelectedBands");
				}

			}
		});
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button OK clicked");
				plotRuleController.setRulePhoneTel(plotRuleMessage.txtTelNumber.getText());
				plotRuleController.setRulePhoneSMS(plotRuleMessage.txtSMSNumber.getText());
				plotRuleController.setRuleMail(plotRuleMessage.txtMailAddress.getText());
				plotRuleController.setRuleMessage(plotRuleMessage.txtAlertMessage.getText()); // returns Text in the
				plotRuleController.setRuleName(txtRuleName.getText()); // same format as the
				plotRuleController.createRule();
				plotRuleController.closeWindow();
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Cancel got clicked");
				plotRuleController.setRulePhoneTel("");
				plotRuleController.setRulePhoneSMS("");
				plotRuleController.setRuleMail("");
				plotRuleController.setRuleMessage(""); // default Message?
				plotRuleController.closeWindow();
			}
		});

	}

	/**
	 * Configuration of the JTables
	 */
	private void initTables() {
		// Table availableBands
		tableAvailableBands.setModel(modelAvailable);
		tableAvailableBands.setForeground(Color.black);
		tableAvailableBands.setBackground(Color.white);
		tableAvailableBands.setSelectionForeground(Color.black);
		tableAvailableBands.setSelectionBackground(Color.LIGHT_GRAY);
		tableAvailableBands.setGridColor(Color.black);
		modelAvailable.setColumnIdentifiers(columns);

// Table selectedBands
		tableSelectedBands.setModel(modelSelected);
		tableSelectedBands.setForeground(Color.black);
		tableSelectedBands.setBackground(Color.white);
		tableSelectedBands.setSelectionForeground(Color.black);
		tableSelectedBands.setSelectionBackground(Color.LIGHT_GRAY);
		tableSelectedBands.setGridColor(Color.black);
		modelSelected.setColumnIdentifiers(columns);
	}

	/**
	 * Method to display the Bands in the List with the available Bands. Method is
	 * called by updateTables()
	 * 
	 * @param selectedBands
	 */
	private void displayTableAvailableBands(ArrayList<Band>[] availableBands) {
		modelAvailable.setRowCount(0);

		for (int i = 0; i < availableBands.length; i++) {
			ArrayList<Band> list = availableBands[i];
			if (list.size() > 0) {
				System.out.println("List.size() Available: " + list.size());
				for (int j = 0; j < list.size(); j++) {
					row[0] = list.get(j).getChannel().getChannelName(); // add channelName to modelAvailable
					row[1] = list.get(j).getMinFreq(); // add minFreq to modelAvailable
					row[2] = list.get(j).getMaxFreq(); // add maxFreq to modelAvailable
					row[3] = list.get(j); // add Band-Object to modelAvailable, The Object column won't be shown in the
											// Table.

					modelAvailable.addRow(row); // The actual adding to modelAvailable
				}
			}
		}
		// Removes the Column with the BandObject from the View, but not from the model
		if (tableAvailableBands.getColumnCount() > 3) { // Check if Table has the fourth column
			TableColumn colToDelete = tableAvailableBands.getColumnModel().getColumn(3);
			tableAvailableBands.removeColumn(colToDelete);
		}

	}

	/**
	 * Method to display the Bands in the List with the selected Bands. Method is
	 * called by updateTables()
	 * 
	 * @param selectedBands
	 */
	private void displayTableSelectedBands(ArrayList<Band>[] selectedBands) {
		modelSelected.setRowCount(0);

		for (int i = 0; i < selectedBands.length; i++) {
			ArrayList<Band> list = selectedBands[i];
			if (list.size() > 0) {
				System.out.println("List.size() Selected: " + list.size());
				for (int j = 0; j < list.size(); j++) {
					row[0] = list.get(j).getChannel().getChannelName(); // add channelName to modelSelected
					row[1] = list.get(j).getMinFreq();// add minFreq to modelSelected
					row[2] = list.get(j).getMaxFreq();// add maxFreq to modelSelected
					row[3] = list.get(j);// add Band-Object to modelSelected, The Object column won't be shown in the
											// Table.

					modelSelected.addRow(row);
				}

			}

		}

		// Removes the Column with the BandObject from the View, but not from the model
		if (tableSelectedBands.getColumnCount() > 3) {
			TableColumn colToDelete = tableSelectedBands.getColumnModel().getColumn(3);
			tableSelectedBands.removeColumn(colToDelete);
		}

	}

	/**
	 * @param availableBands
	 * @param selectedBands
	 * 
	 *                       Gets the actual Band selection and hand it to the
	 *                       methods displayTableAvailableBands,
	 *                       displayTableSelectedBands to fill the Tables with bands
	 */
	public void updateTables(ArrayList<Band>[] availableBands, ArrayList<Band>[] selectedBands) {
		displayTableAvailableBands(availableBands);
		displayTableSelectedBands(selectedBands);

	}

	@Override
	public void update(I_Rule rule) {
		txtRuleName.setText(rule.getRuleName()); // Set textfield if existing rule gets edited
		plotRuleMessage.updateUI();

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

}
