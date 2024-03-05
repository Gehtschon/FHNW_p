package channel;

import java.util.Timer;
import java.util.TimerTask;


import windowtype.I_Windowtype;
import windowtype.Rectwin;

public class Signal extends TimerTask implements SignalSource {
	SignalChannel channel;
	
	
	private static int len; 
	
	Timer timer = new Timer(true);	
	
	
	@Override
	public void start() {
		
		double[] tast = linspace(0.0, channel.getSamples(), channel.getSamples());
		channel.onSignalSamples(0, testfkt(tast), len);
		
	}
	public static double[] testfkt(double[] tastwerte) {
		
		double[] tastSignal = new double[tastwerte.length];
		for (int i = 0; i < tastwerte.length; i++) {
			double x = 0.7 *Math.sin(2 * Math.PI * 50 * tastwerte[i]) + 2*Math.sin(5*Math.random());
			
			tastSignal[i] = x;
			// tastSignal[i][1] = 0;

		}
		len= tastSignal.length;
		
//		for (int i = 0; i < tastSignal.length; i++) {
//			System.out.println("Array Index " + i + "is: " + tastSignal[i]);
//		}
		
		return tastSignal;

	}
	@Override
	public void registerListener(SignalSourceListener listener) {
		channel = (SignalChannel) listener;
		System.out.println("hello");
		TimerTask task = this;
		timer.schedule(task, 0, 100);
		
	}
	public static double[] linspace(double begin, double end, int samples) {
		double[] res = new double[samples];
		double step = (end - begin) / (samples - 1);
		for (int i = 0; i < res.length; i++) {
			res[i] = begin + i * step;
		}

		return res;
	}
	@Override
	public void run() {
		
		start();
		
		
	}

}
