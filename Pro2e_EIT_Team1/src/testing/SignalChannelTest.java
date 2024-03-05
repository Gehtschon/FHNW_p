package testing;

import band.Band;
import channel.SignalChannel;
import rule.Rule;
import windowtype.Hanning;
import windowtype.I_Windowtype;

public class SignalChannelTest {
	public static void main(String[] args) {
		boolean testfailure = false; // for checking if test has errors
		
		/*
		 * Test attributes
		 */
		int overlap = 20;
		boolean overlapState = true;
		int samples = 1000;
		boolean sampleState = true;
		int freq = 300;
		I_Windowtype window = new Hanning();
		String channelName = "Channel";
		String channelLocation = "Location";
		Band band = new Band(new SignalChannel(),100,100);
		Rule rule = new Rule();
		int[] frequencySpectrum = new int[]{100,100,100};
		
		
		// Testchannel
		SignalChannel testChannel = new SignalChannel("testname","testlocation","testlocation");
		
		//Check constructor
		if(testChannel.getChannelName().equals("testname") && !testChannel.getChanelLocation().equals("testlocation")  && testChannel.getChanelLocation().equals("testdiscription")) {
			System.out.printf("Error by cunstrutor of SignalChannel \n");
			testfailure = true;
		}
		
		//Test overlap
		testChannel.setOverlap(overlap);
		if(testChannel.getOverlap() != overlap) {
			System.out.printf("Error by overlap of SignalChannel. Expected overlap, Actual overlap: %5d, %5d\n",overlap,testChannel.getOverlap());
			testfailure = true;
		}
		
		//Test overlapState
		testChannel.setOverlapState(overlapState);
		if(testChannel.getOverlapState() != overlapState) {
			System.out.printf("Error by overlapState of SignalChannel. Expected overlap state:, Actual overlap state: %d, %d\n",overlapState,testChannel.getOverlapState());
			testfailure = true;
		}
		
		//Test samples
		testChannel.setSamples(samples);
		if(testChannel.getSamples() != samples) {
			System.out.printf("Error by samples of SignalChannel. Expected samples, Actual samples: %5d, %5d\n",samples,testChannel.getSamples());
			testfailure = true;
		}
		
		//Test sampleState
		testChannel.setSamplesState(sampleState);
		if(testChannel.getSamplesState() != sampleState) {
			System.out.printf("Error by sampleState of SignalChannel. Expected sample state:, Actual sample state: %d, %d\n",sampleState,testChannel.getSamplesState());
			testfailure = true;
		}
		
		//Test frequency
		testChannel.setFrequency(freq);
		if(testChannel.getFrequency() != freq) {
			System.out.printf("Error by freq of SignalChannel. Expected freq:, Actual freq: %5d, %5d\n",freq,testChannel.getFrequency());
			testfailure = true;
		}
		
		//Test window
		testChannel.setWindow(window);
		if(testChannel.getWindow() != window) {
			System.out.printf("Error by window of SignalChannel. Expected window:, Actual window: %s, %s\n",window,testChannel.getWindow());
			testfailure = true;
		}
		
		//Test channelName
		testChannel.setChanelname(channelName);
		if(!testChannel.getChannelName().equals(channelName)) {
			System.out.printf("Error by ChannelName of SignalChannel. Expected name:, Actual name: %s, %s\n",channelName,testChannel.getChannelName());
			testfailure = true;
		}
		
		//Test channelLocation
		testChannel.setChanelLocation(channelLocation);
		if(!testChannel.getChanelLocation().equals(channelLocation)) {
			System.out.printf("Error by ChannelLocation of SignalChannel. Expected location:, Actual location: %s, %s\n",channelLocation,testChannel.getChanelLocation());
			testfailure = true;
		}
		
		//Test band (adding and removing)
		testChannel.addBand(band);
		if(!testChannel.getBands()[0].equals(band)) {
			System.out.printf("Error by adding band to SignalChannel\n");
			testfailure = true;
		}
		testChannel.removeBand(band);
		if(testChannel.getBands().length != 0) {
			System.out.printf("Error by removing band of SignalChannel\n");
			testfailure = true;
		}
			
		//Test rule (adding and removing)
		testChannel.setRule(rule);
		if(!testChannel.getRules()[0].equals(rule)) {
			System.out.printf("Error by adding rule to SignalChannel\n");
			testfailure = true;
		}
		testChannel.deleteRule(rule);
		if(testChannel.getRules().length != 0) {
			System.out.printf("Error by removing rule of SignalChannel\n");
			testfailure = true;
		}
		
		//Test FrequencySpectrum
		testChannel.setFrequencySpectrum(frequencySpectrum);
		if(!testChannel.getFrequencySpectrum().equals(frequencySpectrum)) {
			System.out.printf("Error by frequencySpectrum of SignalChannel\n");
			testfailure = true;
		}
		
		//Testing equal-Method
		SignalChannel channel2 = new SignalChannel();
		if(testChannel.equals(channel2)) {
			System.out.printf("Error by equal Method of SignalChannel\n");
			testfailure = true;
		}
		if(!testChannel.equals(testChannel)) {
			System.out.printf("Error by equal Method of SignalChannel\n");
			testfailure = true;
		}
		
		// Give out if test was successful or not
		if(!testfailure) {
			System.out.printf("SignalChannel-Model successfully tested!\n");
		}else {
			System.out.printf("SignalChannel-Model has errors!\n");
		}
	}
}
