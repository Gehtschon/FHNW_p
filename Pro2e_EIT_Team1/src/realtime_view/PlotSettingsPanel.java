package realtime_view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import band.I_Band;
import rule.I_Rule;

public class PlotSettingsPanel extends JPanel implements I_Plot_Settings_Panel {

	private PlotSettingsPanelController plotSettingsPanelController;

	private JLabel lblNumExeed = new JLabel("Number of exceedances");
	private JLabel lblAlertPegel = new JLabel("Alert level");
	private JLabel lblSlash = new JLabel("/");
	private JLabel lblSeconds = new JLabel("[s]");

	private JButton btOk = new JButton("OK");
	private JButton btCancel = new JButton("Cancel");

	private JTextField txtPegel = new JTextField();
	private JTextField txtExceed = new JTextField();
	private JTextField txtSeconds = new JTextField();
	

	private final Insets insets = new Insets(5, 5, 5, 5);

	public PlotSettingsPanel(PlotSettingsPanelController plotSettingsPanelController) {
		this.plotSettingsPanelController = plotSettingsPanelController;
		setPreferredSize(new Dimension(350, 100));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();
		initPlotSettingsPanel();
	}

	private void initPlotSettingsPanel() {
		txtPegel.setPreferredSize(new Dimension(100, 20));
		txtExceed.setPreferredSize(new Dimension(100, 20));
		txtSeconds.setPreferredSize(new Dimension(50, 20));

		setLayout(new GridBagLayout());
		add(lblNumExeed, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtExceed, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblSlash, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtSeconds, new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblSeconds, new GridBagConstraints(4, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));

		add(lblAlertPegel, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtPegel, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));

		add(btOk, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets,
				0, 0));
		add(btCancel, new GridBagConstraints(2, 2, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets,
				0, 0));

		btOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int[] exceedances = getNumberExceedances();
					if(exceedances[1] >= 1 && exceedances[1] <= 600) {
						plotSettingsPanelController.applySettings(exceedances, getAlertLevel());
						plotSettingsPanelController.closeWindow();
					}
					else {
						WarningMessageBox("Incorrect time", "The value of the seconds have to be between 1 and 600 seconds");
					}
				}catch(Exception ex) {
					WarningMessageBox("Incorrect input", "The value in one of the boxes is incorrect");
				}
			}
		});

		btCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Close window without writing something to the controller
				plotSettingsPanelController.closeWindow();
			}
		});
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

	/**
	 * int[0] = number of exceedances
	 * int[1] = time while this exeedances can happen
	 */
	@Override
	public int[] getNumberExceedances() {
		int[] exceedances = new int[2];
		exceedances[0] = Integer.parseInt(txtExceed.getText());
		exceedances[1] = Integer.parseInt(txtSeconds.getText());
		System.out.println("exceedances:"+exceedances[0]+exceedances[1]);
		return exceedances;
	}

	/**
	 * int[0] = number of exceedances
	 * int[1] = time while this exeedances can happen
	 */
	@Override
	public void setNumberExceedances(int[] exceedances) {
		txtExceed.setText(String.valueOf(exceedances[0]));
		txtSeconds.setText(String.valueOf(exceedances[1]));
	}

	@Override
	public int getAlertLevel() {
	System.out.println("pegel:"+Integer.parseInt(txtPegel.getText()));
		return Integer.parseInt(txtPegel.getText());
	}

	@Override
	public void setAlertLevel(int levelvalue) {
		txtPegel.setText(String.valueOf(levelvalue));
	}

	@Override
	public void update(I_Band band, I_Rule rule) {
		if(rule != null && band != null) {
			for(int i = 0; i < band.getBandRuleAttributesList().size();i++) {
				if(rule.getRuleName() == band.getBandRuleAttributesList().get(i).getRule().getRuleName()) {
					setAlertLevel(band.getBandRuleAttributesList().get(i).getAlertLevel());
					setNumberExceedances(band.getBandRuleAttributesList().get(i).getNumberExceedances());
				}
			}
		}
		
	}

}
