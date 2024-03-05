package realtime_view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import band.I_Band;
import rule.I_Rule;

public class BandPanel extends JPanel {

	private I_Band band;
	private I_Rule rule;
	private static Insets insets = new Insets(5, 5, 5, 5);
	private JButton btnSettings = new JButton("Settings");
	private JButton btnView = new JButton("View");
	private PlotPanelController plotPanelController;
	private JSlider sliderLevel = new JSlider(SwingConstants.VERTICAL,0,1000,0);
	private JProgressBar progressBar = new JProgressBar(SwingConstants.VERTICAL,0,1000);

	//Returns the current band of the bandPanel
	public I_Band getBand() {
		return band;
	}
	
	//Returns the current rule of the bandPanel
	public I_Rule getRule() {
		return rule;
	}

	//Model band and rule needed for namegiving and identification
	public BandPanel(I_Band band, I_Rule rule, PlotPanelController plotPanelController) {
		super();
		this.band = band;
		this.rule = rule;
		this.plotPanelController = plotPanelController;
		initComponents();
	}

	private void initComponents() {
		//Border around the panel
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//Set preferred size of elements a
		sliderLevel.setPreferredSize(new Dimension(20, 100));
		progressBar.setPreferredSize(new Dimension(30,100));
		//Slider settings
		sliderLevel.setPaintTicks(true);
		sliderLevel.setToolTipText("Max. power of band");
		//Progressbar settings
		progressBar.setStringPainted(true);
		progressBar.setString(sliderLevel.getValue()+"");
		
		
		//Create Layout of a BandPanel
		setLayout(new GridBagLayout());
		add(new JLabel(band.getChannel().getChannelName()), new GridBagConstraints(0, 0, 2, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));
		add(new JLabel(band.getMinFreq() + "-" + band.getMaxFreq() + " Hz"), new GridBagConstraints(0, 1, 2, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 0, 0));
		add(btnSettings, new GridBagConstraints(0, 2, 1, 1, 0, 1, GridBagConstraints.LAST_LINE_START,
				GridBagConstraints.NONE, insets, 0, 0));
		add(btnView, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LAST_LINE_START,
				GridBagConstraints.NONE, insets, 0, 0));
		add(sliderLevel, new GridBagConstraints(1, 2, 1, 2, 0, 1, GridBagConstraints.LAST_LINE_END,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
		add(progressBar, new GridBagConstraints(2, 2, 1, 2, 0, 1, GridBagConstraints.LAST_LINE_END,
				GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));

		//Inform controller, that new openSetting-Button was clicked
		btnSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plotPanelController.openSettings(band, rule);
			}
		});

		//Inform controller, that new openView-Button was clicked
		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				plotPanelController.openView(band);

			}
		});

		//Inform controller, that Level has changed and update progress bar
		sliderLevel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				plotPanelController.setAlertLevel(sliderLevel.getValue(), rule, band);
				progressBar.setValue(sliderLevel.getValue());
				progressBar.setString(sliderLevel.getValue()+"");
			}
		});
	}

	private void setSliderValue(int value) {
		sliderLevel.setValue(value);
	}
	
	/*
	 * Update metho for the BandPanel
	 */
	public void update() {
		for (int i = 0; i < band.getBandRuleAttributesList().size(); i++) {
			if (rule.equals(band.getBandRuleAttributesList().get(i).getRule())) {
				setSliderValue(band.getBandRuleAttributesList().get(i).getAlertLevel());
			}
		}
	}
	

	/*
	 * Methode to compare to BandPanels with each other
	 */
	@Override
	public boolean equals(Object obj) {
		// check if ref is the same
		if (obj == this) {
			return true;
		}

		// Check if compared object is a band-object
		if (!(obj instanceof BandPanel)) {
			return false;
		}

		// Typecast object in Band
		BandPanel bandobj = (BandPanel) obj;
		// Check if SliderValue match
		return (Integer.compare(bandobj.sliderLevel.getValue(), sliderLevel.getValue()) == 0);
	}
}
