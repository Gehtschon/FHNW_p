package band;

import javax.swing.JPanel;

import channel.SignalChannel;
import ueberwachung.Ueberwachung_Singelton;

public class PlotBandSettingsController implements I_Plot_Band_Settings_Controller {

	private PlotBandSettings view;
	private Ueberwachung_Singelton Ueberwachung;

	public PlotBandSettingsController() {
		this.Ueberwachung = Ueberwachung_Singelton.getInstance();
	}

	@Override
	public void setView(JPanel panel) {
		this.view = (PlotBandSettings) panel;
		System.out.println("PlotBandSettings view: " + view);
		view.update(Ueberwachung.getChannelList());
	}

	@Override
	public void setModel(Object model) {

	}

	@Override
	public void createBand(SignalChannel channel, int minFreq, int maxFreq) {
		view.createBand(channel, minFreq, maxFreq); // Create new Band on view
		view.update(Ueberwachung.getChannelList());
	}

	@Override
	public void removeBand(Band band, SignalChannel channel) {
		channel.removeBand(band); // Delete band in model
		view.update(Ueberwachung.getChannelList()); // Update view with the new model
	}

	@Override
	public void addBand(SignalChannel channel, Band band) {
		channel.addBand(band);
		view.update(Ueberwachung.getChannelList());
	}

	@Override
	public void update() {
		view.update(Ueberwachung.getChannelList());
		
	}

}
