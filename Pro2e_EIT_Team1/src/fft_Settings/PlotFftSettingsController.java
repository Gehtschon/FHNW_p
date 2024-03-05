package fft_Settings;

import javax.swing.JFrame;
import javax.swing.JPanel;

import band.PlotBandSettings;
import channel.I_Channel;
import channel.SignalChannel;
import rule.Rule;
import ueberwachung.Ueberwachung_Singelton;
import windowtype.Hanning;
import windowtype.I_Windowtype;
import controllers.Controller_Singleton;


public class PlotFftSettingsController implements I_Plot_FFT_Settings_Controller{

	public PlotFftSettings settingsView; //View
	public JFrame frame;
	private SignalChannel signalChannel; //Model
	private Ueberwachung_Singelton ueberwachung; 
	
	
	public PlotFftSettingsController(JFrame frame) {
		this.frame = frame;
		this.ueberwachung = Ueberwachung_Singelton.getInstance();
	}

	@Override
	public void setView(JPanel panel) {
		this.settingsView = (PlotFftSettings) panel;
		settingsView.update(ueberwachung.getChannelList());
	}

	@Override
	public void setModel(Object model) {
		this.signalChannel = (SignalChannel) model;
		
	}

	@Override
	public void setOverlap(int value,I_Channel chanel) {
		chanel.setOverlap(value); //sets the overlap value		
	}
	
	public int getOverlap() {
		return signalChannel.getOverlap();
	}

	@Override
	public void setSamples(int value, I_Channel chanel) {
		chanel.setSamples(value); //sets the samples value
	}
	
	public int getSamples() {
		return signalChannel.getSamples();
	}

	@Override
	public void setFrequency(int value, I_Channel chanel) {
		chanel.setFrequency(value); //sets the frequency
	}

	public int getFrequency() {
		return signalChannel.getFrequency();
	}
	
	@Override
	public void setWindow(I_Windowtype windowtype) {
		signalChannel.setWindow(windowtype);
		
	}
	
	public void closeWindow() {
		frame.dispose();

	}

	@Override
	public void setChannelSettings(I_Channel channel, int overlap, int samples, int frequency, I_Windowtype window) {
		// TODO Auto-generated method stub
		settingsView.update(ueberwachung.getChannelList());
		
	}
	
	public void removeChannel(I_Channel currentChannel) {
		ueberwachung.removeChannelFromList(currentChannel);
		Controller_Singleton.getInstance().updateControllers();
		update();
	}

	@Override
	public void update() {
		settingsView.update(ueberwachung.getChannelList());
	}


}
