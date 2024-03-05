package realtime_view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import band.I_Band;
import channel.FFT;
import channel.SignalChannel;
import ueberwachung.Ueberwachung_Singelton;
import utils_DiCerbo.AudioRecorderEngine.SampleListener;

public class RealTimeView extends JPanel implements SampleListener {

	SignalChannel channel;

	private I_Band band;

	private final Object[] lock = new Object[] {};

	private int[] samples = new int[1024];
	private int[] lockedSamples = new int[1024];
	private int[] clear = new int[1024];

	double begin = 0.0;
	double end = 20.0;

	private String title;
	private JFreeChart xyLineChart;
	private ChartPanel chart;
	public Timer timer;
	private JButton button = new JButton("log scaling");
	private final Insets insets = new Insets(5, 5, 5, 5);

	public RealTimeView(I_Band band) {
		this.band = band;
		title = "Channel: " + band.getChannel().getChannelName() + ", min Freq: " + band.getMinFreq() + ", max Freq: "
				+ band.getMaxFreq();
		xyLineChart = ChartFactory.createXYLineChart(title, "t [ms]", "y[t]", createDataset(), PlotOrientation.VERTICAL,
				false, true, false);
		initRealTimeView();
		updatePlot();
	}

	private void initRealTimeView() {
		setLayout(new GridBagLayout());

		chart = new ChartPanel(xyLineChart);
		chart.setPopupMenu(null);
		final XYPlot plot = xyLineChart.getXYPlot();
		plot.getDomainAxis().setRange(band.getMinFreq(), band.getMaxFreq());
		LogAxis logAxis = new LogAxis("PSD [W/Hz]");
		logAxis.setMinorTickMarksVisible(true);
		logAxis.setAutoRange(true);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
		renderer.setSeriesPaint(0, Color.BLUE);
		plot.setRenderer(renderer);

		add(chart, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				insets, 0, 0));

	}

	private XYDataset createDataset() {
		int minFreq = band.getMinFreq();
		int maxFreq = band.getMaxFreq();

		channel = (SignalChannel) band.getChannel();
		int samples = channel.getSamples();

		double[] tastSignal = channel.getValidsamples();

		final XYSeries realTimeSignal = new XYSeries("RealTime Signal");
		for (int i = 0; i < (tastSignal.length); i++) {
			realTimeSignal.add(i, tastSignal[i]);
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(realTimeSignal);
		return dataset;
	}

	@Override
	public void onNewSamples(int[] samples, int length) {
		System.arraycopy(clear, 0, this.samples, 0, this.samples.length);
		synchronized (lock) {
			System.arraycopy(samples, 0, this.samples, 0, samples.length);
		}

		repaint();

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

}
