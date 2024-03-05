package utils_DiCerbo;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import utils_DiCerbo.AudioRecorderEngine.SampleListener;

@SuppressWarnings("serial")
public class PlotPanelDiCerbo extends JPanel implements SampleListener {

	private final Object[] lock = new Object[] {};

	private int[] samples = new int[1024];
	private int[] lockedSamples = new int[1024];
	private int[] clear = new int[1024];

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double dx = getWidth() / (samples.length - 1.0);
		double dy = getHeight() / (double) Integer.MAX_VALUE;

		synchronized (lock) {
			System.arraycopy(samples, 0, lockedSamples, 0, samples.length);
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GREEN);

		for (int i = 0; i < lockedSamples.length - 1; i++) {
			int x0 = (int) (i * dx);
			int y0 = (int) (getHeight() / 2 - lockedSamples[i] * dy);
			int x1 = (int) ((i + 1) * dx);
			int y1 = (int) (getHeight() / 2 - lockedSamples[(i + 1)] * dy);

			g.drawLine(x0, y0, x1, y1);
		}
	}

	@Override
	public void onNewSamples(int[] samples, int length) {
		System.arraycopy(clear, 0, this.samples, 0, this.samples.length);
		synchronized (lock) {
			System.arraycopy(samples, 0, this.samples, 0, samples.length);
		}

		repaint();
	}

	public void init() {
		setLayout(null);
	}

}
