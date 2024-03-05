package fft_Settings;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import channel.I_Channel;
import channel.SignalChannel;
import frameGenerator.FrameGenerator;

public class PlotFftSettings2 extends JPanel implements I_Plot_FFT_Settings_2 {
	
	// -----------------------------
	// Gui Elements
	// -----------------------------

	private PlotFftSettings2Controller PlotFftSettings2Controller; // Controller
	private FrameGenerator frameGenerator = new FrameGenerator(); // Next window
	
	// Channel Arraylist
	private ArrayList<SignalChannel> channel;

	// Labels
	private JLabel lblChanelName = new JLabel("Channel/Sensor Name");
	private JLabel lblSensorLoc = new JLabel("Sensor Location");
	private JLabel lblSensorDes = new JLabel("Sensor Description");

	// Textfields
	private JTextField txtName = new JTextField();
	private JTextField txtLocation = new JTextField();
	private JTextField txtDescription = new JTextField();

	// Buttons
	private JButton btCancel = new JButton("Cancel");
	private JButton btOk = new JButton("OK");
	
	// ---------------------------
	
	private final Insets insets = new Insets(5, 5, 5, 5);
	
	boolean doubleChannel = false;
	

	public PlotFftSettings2(PlotFftSettings2Controller PlotFftSettings2Controller, ArrayList<SignalChannel> channel) {
		this.PlotFftSettings2Controller = PlotFftSettings2Controller;
		this.channel = channel;
		setPreferredSize(new Dimension(350, 150));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();
		initPlotFftSettings2();
	}

	private void initPlotFftSettings2() {
		txtName.setPreferredSize(new Dimension(200, 20));
		txtLocation.setPreferredSize(new Dimension(200, 20));
		txtDescription.setPreferredSize(new Dimension(200, 20));

		setLayout(new GridBagLayout());
		add(lblChanelName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtName, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				insets, 0, 0));

		add(lblSensorLoc, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtLocation, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		add(lblSensorDes, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtDescription, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		add(btOk, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets,
				0, 0));
		add(btCancel, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));

		btOk.addActionListener(new ActionListener() {
			//Transmitting the set values for a new channel
			//If no error message the window will be closed
			@Override
			public void actionPerformed(ActionEvent e) {
				PlotFftSettings2Controller.newChannel(txtName.getText(),txtLocation.getText(),txtDescription.getText());
				doubleChannel = false;
				
				if (channel.size() == 1) {
					frameGenerator.buildFftSettingsFrame();
					PlotFftSettings2Controller.closeWindow();
					return;
				}
				//Check if a channel name already exists
				for (int i = 0; i < channel.size() - 1; i++) {
					if (channel.get(channel.size() - 1).getChannelName().equals(channel.get(i).getChannelName())) {
						WarningMessageBox("Double input", "Name already Exists");
						txtName.setText(null);
						doubleChannel = true;
						channel.remove(channel.size()-1);
					}
				}
				if(doubleChannel == false) {
				frameGenerator.buildFftSettingsFrame();
				PlotFftSettings2Controller.closeWindow();
				}
			}
		});

		btCancel.addActionListener(new ActionListener() {
			//Cancel the action, reset settings
			@Override
			public void actionPerformed(ActionEvent e) {
				txtDescription.setText(null);
				txtLocation.setText(null);
				txtName.setText(null);
				frameGenerator.buildFftSettingsFrame();
				PlotFftSettings2Controller.closeWindow();
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

	@Override
	public void update(I_Channel channel) {
		// TODO Auto-generated method stub
	}
}
