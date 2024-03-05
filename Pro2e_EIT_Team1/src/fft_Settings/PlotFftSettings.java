package fft_Settings;

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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//import com.sun.org.apache.bcel.internal.generic.Select;

import channel.I_Channel;
import channel.SignalChannel;
import frameGenerator.FrameGenerator;
import ueberwachung.Ueberwachung_Singelton;
import windowtype.Hanning;
import windowtype.Rectwin;

public class PlotFftSettings extends JPanel implements I_Plot_FFT_Settings {
	
	
	// -----------------------------
	// GUI Elements
	// -----------------------------

	private PlotFftSettingsController PlotFftSettingsController; // Controller
	private FrameGenerator frameGenerator = new FrameGenerator(); // Next window

	// Labels
	private JLabel lblSample = new JLabel("Samples");
	private JLabel lblOverlap = new JLabel("Overlap");
	private JLabel lblFenster = new JLabel("Windowtype");
	private JLabel lblSampFreq = new JLabel("Sample Frequency");
	private JLabel lblOverlapUnit = new JLabel("%");
	private JLabel lblSampFreqUnit = new JLabel("1/s");

	// Textfields
	private JTextArea txtFFTpath = new JTextArea(2, 0);
	private JScrollPane scrollFFTpath = new JScrollPane(txtFFTpath);
	private JTextField txtSampFreqPath = new JTextField();

	// Spinners
	private SpinnerNumberModel overlapNumber = new SpinnerNumberModel(0, 0, 100, 1);
	private JSpinner spinnerOverlap = new JSpinner(overlapNumber);
	private SpinnerNumberModel samplesNumber = new SpinnerNumberModel(0, 0, 10000, 1);
	private JSpinner spinnerSamples = new JSpinner(samplesNumber);

	// Buttons
	private JButton btRemove = new JButton("Remove");
	private JButton btOk = new JButton("OK");
	private JButton btCancel = new JButton("Cancel");
	private JButton btAddChanel = new JButton("Add Channel");

	private JRadioButton radbtOverlap = new JRadioButton();
	private JRadioButton radbtSamples = new JRadioButton();

	// ComboBox
	private I_Channel currentChannel = null;
	private ArrayList<SignalChannel> channelList = new ArrayList<SignalChannel>(); // List with all the current channels
	private JComboBox<String> CmbBxChannel = new JComboBox<String>();
	String[] formName = { "Hanning", "Rect" }; // List with the current windows
	private JComboBox cmbBxWindow = new JComboBox(formName);

	// ---------------------------

	private final Insets insets = new Insets(5, 5, 5, 5);

	private int samplesValue;
	private int overlapValue;
	private int freqValue;

	public PlotFftSettings(PlotFftSettingsController PlotFftSettingsController) {
		this.PlotFftSettingsController = PlotFftSettingsController;
		setPreferredSize(new Dimension(250, 350));
		setDoubleBuffered(true);
		setFocusable(true);
		initPlotFftSettings();
	}

	private void initPlotFftSettings() {
		spinnerOverlap.setMinimumSize(new Dimension(60, 20));
		spinnerSamples.setMinimumSize(new Dimension(60, 20));
		CmbBxChannel.setPreferredSize(new Dimension(150, 20));
		CmbBxChannel.setMinimumSize(new Dimension(100, 20));
		cmbBxWindow.setPreferredSize(new Dimension(150, 20));
		cmbBxWindow.setMinimumSize(new Dimension(100, 20));

		txtSampFreqPath.setPreferredSize(new Dimension(100, 20));

		setLayout(new GridBagLayout());
		add(btAddChanel, new GridBagConstraints(0, 0, 4, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(CmbBxChannel, new GridBagConstraints(0, 1, 2, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(btRemove, new GridBagConstraints(2, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets,
				0, 0));

		add(radbtOverlap, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblOverlap, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(spinnerOverlap, new GridBagConstraints(2, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(lblOverlapUnit, new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		add(radbtSamples, new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblSample, new GridBagConstraints(1, 3, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(spinnerSamples, new GridBagConstraints(2, 3, 1, 1, 1, 0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		add(lblSampFreq, new GridBagConstraints(0, 4, 2, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(txtSampFreqPath, new GridBagConstraints(2, 4, 1, 1, 1, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(lblSampFreqUnit, new GridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));

		add(lblFenster, new GridBagConstraints(0, 5, 2, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(cmbBxWindow, new GridBagConstraints(2, 5, 1, 1, 1, 0, GridBagConstraints.EAST,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		add(btOk, new GridBagConstraints(0, 6, 2, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0,
				0));
		add(btCancel, new GridBagConstraints(2, 6, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets,
				0, 0));

		add(scrollFFTpath, new GridBagConstraints(0, 7, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0));

		//Default setting of the JTextArea
		txtFFTpath.setFont(txtSampFreqPath.getFont());
		txtFFTpath.setWrapStyleWord(true);
		txtFFTpath.setLineWrap(true);
		txtFFTpath.setEnabled(false);

		cmbBxWindow.addItemListener(new ItemListener() {
			//Selecting the window types Hanning & Rectwin
			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					if (cmbBxWindow.getSelectedIndex() == 0) {
						Hanning hannign = new Hanning();
						PlotFftSettingsController.setWindow(hannign);
					}
					if (cmbBxWindow.getSelectedIndex() == 1) {
						Rectwin rectwin = new Rectwin();
						PlotFftSettingsController.setWindow(rectwin);
					}
				} catch (Exception n2) {
					System.out.println("No channel created yet");
				}
			}
		});

		CmbBxChannel.addItemListener(new ItemListener() {
			//Selecting the shown channel
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Change of the combobox; currentChannel gets updated
				int index = CmbBxChannel.getSelectedIndex(); // Attention-> return -1 when empty
				if (index >= 0 && index < channelList.size()) {
					currentChannel = channelList.get(index);
					System.out.println("Aktueller Channel ist: " + currentChannel);
					PlotFftSettingsController.setModel(currentChannel);
					updateTxt();
					updateValues();
					try {
						cmbBxWindow.setSelectedIndex(getWindowTypeIndex());
					} catch (Exception e2) {
						System.out.println("New Channel --> No selected windowType yet");
					}
				}
			}
		});

		btAddChanel.addMouseListener(new MouseAdapter() {
			//opens new window for creating a channel
			@Override
			public void mousePressed(MouseEvent e) {
				frameGenerator.buildFftSettings2Frame(channelList);
				PlotFftSettingsController.closeWindow();
				try {
					PlotFftSettingsController.setChannelSettings(currentChannel, (int) spinnerOverlap.getValue(),
							(int) spinnerSamples.getValue(), Integer.parseInt(txtSampFreqPath.getText()),
							currentChannel.getWindow());
				} catch (Exception ex) {
					System.out.println("No selected chanel yet");
				}
			}
		});

		btOk.addActionListener(new ActionListener() {
			//Transmitting the set values to the controller
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PlotFftSettingsController.setFrequency(freqValue, currentChannel);
					PlotFftSettingsController.setOverlap(overlapValue, currentChannel);
					PlotFftSettingsController.setSamples(samplesValue, currentChannel);
					updateTxt();
				} catch (Exception ex) {
					System.out.println("No data available for transmission");
				}
				PlotFftSettingsController.closeWindow();
			}
		});

		btCancel.addActionListener(new ActionListener() {
			//Cancels the input
			@Override
			public void actionPerformed(ActionEvent e) {
				PlotFftSettingsController.closeWindow();
			}
		});

		btRemove.addActionListener(new ActionListener() {
			//Allows the removal of an existing channel
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btRemove) {
					try {
						removeChannel(currentChannel);//Removes the channel that is displayed in the window
					} catch (Exception ex) {
						System.out.println("no channel available in the channel list.");
					}
				}
				//PlotFftSettingsController.removeChannel(currentChannel);
			}
		});

		//defalut spinner configuration when no channel
		spinnerOverlap.setEnabled(false);
		spinnerSamples.setEnabled(false);

		radbtOverlap.addActionListener(new ActionListener() {
			//Locking the overlap spinner to the samples spinner
			@Override
			public void actionPerformed(ActionEvent e) {
				if (radbtOverlap.isSelected()) {
					spinnerOverlap.setEnabled(true);
					radbtSamples.setEnabled(false);
					spinnerSamples.setEnabled(false);
					spinnerSamples.setValue(0);
					currentChannel.setOverlapState(true);
					currentChannel.setSamplesState(false);
				} else {
					radbtSamples.setEnabled(true);
					currentChannel.setSamplesState(true);
					spinnerSamples.setEnabled(false);
					spinnerOverlap.setEnabled(false);
					spinnerOverlap.setValue(0);
				}
			}
		});

		radbtSamples.addActionListener(new ActionListener() {
			//Locking the sample spinner to the overlap spinner
			@Override
			public void actionPerformed(ActionEvent e) {
				if (radbtSamples.isSelected()) {
					spinnerSamples.setEnabled(true);
					radbtOverlap.setEnabled(false);
					radbtOverlap.setSelected(false);
					spinnerOverlap.setEnabled(false);
					spinnerOverlap.setValue(0);
					currentChannel.setOverlapState(false);
					currentChannel.setSamplesState(true);
				} else {
					radbtOverlap.setEnabled(true);
					currentChannel.setOverlapState(true);
					spinnerOverlap.setEnabled(false);
					spinnerSamples.setEnabled(false);
					spinnerSamples.setValue(0);

				}
			}
		});

		spinnerOverlap.addChangeListener(new ChangeListener() {
			//transmitting the overlap value
			@Override
			public void stateChanged(ChangeEvent e) {
				PlotFftSettingsController.setOverlap((int) spinnerOverlap.getValue(), currentChannel);
				overlapValue = (Integer) spinnerOverlap.getValue();
			}
		});

		spinnerSamples.addChangeListener(new ChangeListener() {
			//transmitting the samples value
			@Override
			public void stateChanged(ChangeEvent e) {
				PlotFftSettingsController.setSamples((int) spinnerSamples.getValue(), currentChannel);
				samplesValue = (Integer) spinnerSamples.getValue();
			}
		});

		txtSampFreqPath.getDocument().addDocumentListener(new DocumentListener() {
			//Reading out the entered sample frequency
			@Override
			public void removeUpdate(DocumentEvent e) {
				readValueFreq();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				readValueFreq();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				readValueFreq();
			}

			private void readValueFreq() {
				if (!(txtSampFreqPath.getText().equals(""))) {
					try {
						//Check whether the entered value can be processed
						if (Long.parseLong(txtSampFreqPath.getText()) < Integer.MAX_VALUE) {
							freqValue = Integer.parseInt(txtSampFreqPath.getText());
							PlotFftSettingsController.setFrequency(freqValue, currentChannel);
						//Output when wrong input is made
						} else {
							WarningMessageBox("Incorrect frequency", "frequency is bigger than the maximal frequency");
						}
					} catch (Exception e) {
						WarningMessageBox("Incorrect frequency", "Only numbers are allowed");
					}
				}
			}
		});

	}

	/**
	 * Removes an existing channel
	 */
	private void removeChannel(I_Channel currentChannel) {

		int RemoveIndex = -1;
		//default settings for all values
		for (int i = 0; i < channelList.size(); i++) {
			if (channelList.get(i).equals(currentChannel)) {
				RemoveIndex = i;
				spinnerOverlap.setValue(0);
				spinnerSamples.setValue(0);
				txtSampFreqPath.setText(Integer.toString(0));
			}
		}
		if (RemoveIndex != -1) {
			channelList.remove(RemoveIndex);
		}

		updateTxt();
		noChannelYet();
		updateChannel();
		PlotFftSettingsController.removeChannel(currentChannel);
	}

	/**
	 * Sets the text in the JTextArea
	 */
	private void updateTxt() {
		try {
			// Sets the text
			txtFFTpath.setText("Chanel/sensor name: " + (currentChannel.getChannelName()));
			txtFFTpath.append("\nThe sensor location: " + (currentChannel.getChanelLocation()));
			txtFFTpath.append("\nThe sensor description: " + (currentChannel.getChannelDescription()));
			
			
			// Removes the text
			if (channelList.size() <= 0) {
				txtFFTpath.setText(null);
			}
		} catch (Exception ex) {
			System.out.println("No data for output");
		}
	}

	/**
	 * Method to choose window type
	 * 
	 * @return the index of the selected window
	 */

	private int getWindowTypeIndex() {
		String windowName = (currentChannel).getWindow().getWindowTypeName();
		System.out.println("getWindowTypeIndex() called");
		switch (windowName) {
		case "Hanning":
			System.out.println("Windowtyp hanning selected");
			return 0;
		case "Rect":
			System.out.println("Windowtyp rectWin selected");
			return 1;
		default:
			System.out.println("Switch Case Error");
			return 0;
		}
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

	/*
	 * Method to update the JCombobox with the new channels
	 */
	private void updateChannel() {
		int sel = CmbBxChannel.getSelectedIndex();
		CmbBxChannel.removeAllItems(); // Clear the combobox

		// Add all channel from the channellist
		if (channelList.size() > 0) {
			for (int i = 0; i < channelList.size(); i++) {
				CmbBxChannel.addItem(channelList.get(i).getChannelName());
				System.out.println("Add Channel to ComboBox; Index:" + i);
			}
		}
		// Selct first item there are any items in the combobox
		if (sel == -1 && channelList.size() > 0) {
			sel = 0;

		}
		CmbBxChannel.setSelectedIndex(sel);
	}

	/**
	 * Method for GUI display if no channel available.
	 */
	private void noChannelYet() {
		//If no channel exists
		if (channelList.size() <= 0) {
			radbtOverlap.setEnabled(false);
			spinnerOverlap.setEnabled(false);
			spinnerSamples.setEnabled(false);
			radbtSamples.setEnabled(false);
			txtSampFreqPath.setEnabled(false);
			cmbBxWindow.setEnabled(false);
		} else {
			//When a channel exists
			radbtOverlap.setEnabled(true);
			radbtSamples.setEnabled(true);
			txtSampFreqPath.setEnabled(true);
			cmbBxWindow.setEnabled(true);
		}
	}

	/*
	 * Method updates the values for the different channels.
	 */
	private void updateValues() {
		try {
			spinnerOverlap.setValue(currentChannel.getOverlap());
			if (!currentChannel.getOverlapState() && !currentChannel.getSamplesState()) {
				radbtOverlap.setSelected(true);
				radbtOverlap.setEnabled(true);
				spinnerOverlap.setEnabled(true);
				radbtSamples.setSelected(false);
				radbtSamples.setEnabled(false);
				spinnerSamples.setEnabled(false);
			} else {
				spinnerOverlap.setEnabled(currentChannel.getOverlapState());
				radbtOverlap.setSelected(currentChannel.getOverlapState());
				radbtOverlap.setEnabled(currentChannel.getOverlapState());

				radbtSamples.setSelected(currentChannel.getSamplesState());
				spinnerSamples.setEnabled(currentChannel.getSamplesState());
				radbtSamples.setEnabled(currentChannel.getSamplesState());

			}
			int newSamplesValue = (currentChannel).getSamples();
			spinnerSamples.setValue(newSamplesValue);
			int newFrequencyValue = (currentChannel).getFrequency();
			txtSampFreqPath.setText(Integer.toString(newFrequencyValue));
		} catch (Exception ex) {
			System.out.println("No values to update");
		}
	}

	/**
	 * Update method for the channels
	 */
	@Override
	public void update(ArrayList<SignalChannel> channels) {
		channelList = channels;
		updateChannel(); //Update the channellist with the new channel
		updateValues(); //Update the values for each channel
		noChannelYet(); //Update when no channel exists
		updateTxt(); //Update the JTextArea with the new Text for each channel

	}
}
