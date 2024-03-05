package fft_Settings;

import javax.swing.JFrame;
import javax.swing.JPanel;

import channel.SignalChannel;
import ueberwachung.Ueberwachung_Singelton;

public class PlotFftSettings2Controller implements I_Plot_FFT_Settings_2_Controller{

	private PlotFftSettings2 settings2View; //View
	private JFrame frame;
	private SignalChannel signalChannel; //Model
	private Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();
	
	public PlotFftSettings2Controller(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void setView(JPanel panel) {
		// TODO Auto-generated method stub
		this.settings2View = (PlotFftSettings2) panel;
		
	}

	@Override
	public void setModel(Object model) {
		// TODO Auto-generated method stub
		this.signalChannel = (SignalChannel) model;
	}

	@Override
	public void setChanelname(String name) {
		// TODO Auto-generated method stub
		//	signalChannel.setChanelname(name);
		
	}

	@Override
	public void setChanelLocation(String name) {
		// TODO Auto-generated method stub
		//signalChannel.setChanelLocation(name);
		
	}

	@Override
	public void setChanelDescription(String name) {
		// TODO Auto-generated method stub
		//signalChannel.setChannelDescription(name);
		
	}
	
	
	public void closeWindow() {
		frame.dispose();
	}

	public void newChannel(String channelName, String channelLocation, String channelDescription) {
		// TODO Auto-generated method stub
		ueberwachung.addChanelToList(new SignalChannel(channelName, channelLocation, channelDescription));
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
