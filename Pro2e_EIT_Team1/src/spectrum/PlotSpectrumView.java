package spectrum;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import channel.FFT;
import channel.Signal;
import channel.SignalChannel;
import ueberwachung.Ueberwachung_Singelton;

public class PlotSpectrumView extends JPanel {

	// TODO Herauszoomen über die Grenzen verbieten.evt. einstellunge am Graph
	// machen.
	// TODO Signal vom entsprechenden Channel ziehen
	//SignalChannel channel = new SignalChannel();
	
	private double[] signal;

	private double maxValue = 100.0;
	private double minValue = Math.pow(10, -20);
	private Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
	public boolean channelSizeViable = true;

	private SignalChannel currentChannel;
	private ArrayList<SignalChannel> channelList;

	// GUI Elements
	private JComboBox<String> cmbBchannel = new JComboBox<String>();
	private JCheckBox cbLogScale = new JCheckBox();
	private JLabel lblLog = new JLabel("Log-Darstellung");

	private final Insets insets = new Insets(5, 5, 5, 5);

	private String title;
	private JFreeChart xyLineChart;
	public Timer timer;
	
	public PlotSpectrumView() {
		
		maxValue = maxValue() + Math.pow(10, 2);

		update(ueberwachung.getChannelList());

		setChannelToSig();
		setChannel();
		if (channelList.size() == 0) {
			WarningMessageBox("Failure", "no channel detected");
			channelSizeViable = false;
		}
		xyLineChart = ChartFactory.createXYLineChart("", "f [Hz]", "PSD [W/Hz]", createDataset(),
				PlotOrientation.VERTICAL, false, true, false);

		initPlotSpectrum();
		updatePlot();
	}

	private double maxValue() {
		double max = 0;
		setChannel();
		setChannelToSig();
		for (int i = 0; i < signal.length; i++) {
			if (max < signal[i]) {
				max = signal[i];
			}
		}
		return max;
	}

	private void initPlotSpectrum() {
		setLayout(new GridBagLayout());

		ChartPanel chart = new ChartPanel(xyLineChart);
		chart.setPopupMenu(null);
		final XYPlot plot = xyLineChart.getXYPlot();
		// plot.getDomainAxis().setRange(0, currentChannel.getFrequency());
		NumberAxis domain = (NumberAxis) plot.getDomainAxis();
		// domain.setTickUnit(new NumberTickUnit(1000));
		LogAxis logAxis = new LogAxis("PSD [W/Hz]");
		NumberAxis numberAxis = new NumberAxis("PSD [W/Hz]");
		logAxis.setMinorTickMarksVisible(true);
		logAxis.setRange(minValue, maxValue); // TODO needs maxValue and minValue from Spectrum
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
		renderer.setSeriesPaint(0, Color.BLUE);
		plot.setRenderer(renderer);

		// maybe change later
		cmbBchannel.setMinimumSize(new Dimension(150, 20));
		cmbBchannel.setPreferredSize(new Dimension(200, 20));

		add(cmbBchannel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				insets, 0, 0));
		add(chart, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				insets, 0, 0));
		add(cbLogScale, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				insets, 0, 0));
		add(lblLog, new GridBagConstraints(1, 2, 1, 1, 1.0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets,
				0, 0)); // label flush left

		cbLogScale.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbLogScale.isSelected()) {
					plot.setRangeAxis(logAxis);
				} else {
					plot.setRangeAxis(numberAxis);

				}
			}
		});
		cmbBchannel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("currentChannel is: " + currentChannel.getChannelName()); //needs fixing because current Channel is always different
				int index = cmbBchannel.getSelectedIndex();
				if (index >= 0) {
					currentChannel = channelList.get(index);
					updatePlot();
				}
			}
		});

	}

	/**
	 * 
	 * @return int[freq][signal] dataset
	 */
	private XYDataset createDataset() {
		final XYSeries realTimeSignal = new XYSeries("RealTime Signal");
		signal = currentChannel.getSpectralvalues();
		for (int i = 0; i < signal.length; i++) {
			//for (int j = 0; j < currentChannel.getFrequencySpectrum().length; j++) {
				realTimeSignal.add(i, signal[i]);
				
				 
			//}
			
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(realTimeSignal);
		return dataset;
	}

	private void updateChannel() {
		int sel = cmbBchannel.getSelectedIndex();
		cmbBchannel.removeAllItems(); // Clear the combobox
		// Add all channel from the channellist
		if (channelList.size() > 0) {
			for (int i = 0; i < channelList.size(); i++) {
				cmbBchannel.addItem(channelList.get(i).getChannelName());
			}
		}
	}

	public void update(ArrayList<SignalChannel> arrayList) {
		channelList = arrayList;

		updateChannel(); // Update Checkbox with the channels

	}

	// if the SpectrumView starts up automatically selects the first channel.
	private void setChannel() {
		update(ueberwachung.getChannelList());
		System.out.println("Update " + channelList.size());
		if (currentChannel == null && channelList.size() != 0) {
			currentChannel = channelList.get(0);
		}
	}

	// sets the channel to signal
	private void setChannelToSig() {
		
		if (currentChannel != null) {
			
			signal = currentChannel.getSpectralvalues();
		}

	}
	
	private void updatePlot() {

		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				xyLineChart.getXYPlot().setDataset(createDataset());
				//System.out.println("Timeraction");
				repaint();
			}
		});
		timer.start();

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
