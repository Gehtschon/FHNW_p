package testing;

import java.util.ArrayList;
import java.util.Arrays;

import band.Band;
import band.BandRuleAtrributes;
import band.I_Band;
import channel.SignalChannel;
import rule.Rule;

public class BandTest {
	public static void main(String[] args) {
		SignalChannel testchannel = new SignalChannel("channel", "location", "description"); // Channel for testing
		boolean testfailure = false; // for checking if test has errors

		ArrayList<I_Band> bandList = new ArrayList<I_Band>();
		
		// Creating multiple bands and test instantiation
		for (int i = 0; i < 10 && !testfailure; i++) {
			//Create random bands
			bandList.add(new Band(testchannel,i+100,i+200));
			//Read back the min. frequency and check if its equal to the set frequency
			if(bandList.get(i).getMinFreq() != i+100) {
				System.out.printf("Error by min. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", i+100, bandList.get(i).getMinFreq());
				testfailure = true;
				break;
			}
			//Read back the max. frequency and check if its equal to the set frequency
			if(bandList.get(i).getMaxFreq() != i+200) {
				System.out.printf("Error by max. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", i+200, bandList.get(i).getMinFreq());
				testfailure = true;
				break;
			}
			//Read back the channel and check if its equal to the set channel
			if(!bandList.get(i).getChannel().getChannelName().equals(testchannel.getChannelName())) {
				System.out.printf("Error by channel. Expected channel name, Actual channel name: %s, %s \n", testchannel.getChannelName(), bandList.get(i).getChannel().getChannelName());
				testfailure = true;
				break;
			}
		}
		
		// Set different values to the band and read them back
		for (int i = 0; i < bandList.size() && !testfailure; i++) {
			bandList.get(i).setMinFreq(i);
			//Read back the min. frequency and check if its equal to the set frequency
			if(bandList.get(i).getMinFreq() != i) {
				System.out.printf("Error by min. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", i, bandList.get(i).getMinFreq());
				testfailure = true;
				break;
			}
			bandList.get(i).setMaxFreq(i+100);
			//Read back the max. frequency and check if its equal to the set frequency
			if(bandList.get(i).getMaxFreq() != i+100) {
				System.out.printf("Error by max. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", i+100, bandList.get(i).getMinFreq());
				testfailure = true;
				break;
			}
			SignalChannel testChannel = new SignalChannel("channel", "location", "description");
			bandList.get(i).setChannel(testChannel);
			//Read back the channel and check if its equal to the set channel
			if(!bandList.get(i).getChannel().getChannelName().equals(testchannel.getChannelName())) {
				System.out.printf("Error by channel. Expected channel, Actual channel: %s, %s \n", testchannel.getChannelName(), bandList.get(i).getChannel().getChannelName());
				testfailure = true;
				break;
			}
			
			//Set new BandRuleAtrributes to Band and check if it is set.
			BandRuleAtrributes bra = new BandRuleAtrributes(new int[] {i,i}, 50, new Rule());
			bandList.get(i).addRuleSettingsToBand(bra);
			if(!bandList.get(i).getBandRuleAttributesList().contains(bra)) {
				System.out.printf("Error by adding BandRuleAttribute. List does not contain BandRuleAttribute\n");
				testfailure = true;
				break;
			}
			// Remove the BandRuleAtrributes on the basis of the rule and check if band does not contain the Attribute anymore
			bandList.get(i).removeRuleSettingsFromBand(bra.getRule());
			if(bandList.get(i).getBandRuleAttributesList().contains(bra)) {
				System.out.printf("Error by removing BandRuleAttribute. Band still contain BandRuleAttribute\n");
				testfailure = true;
				break;
			}
			// Set a frequencySepctrum to the band and read back the chopped bandSpectrum
			int[] frequencySepctrum = new int[] {i+2,i+4,i+6,i+8,i+10}; //Values from i are in BandSpectrum
			bandList.get(i).setFrequencySpectrum( frequencySepctrum);
			int[] getBandSepctrum = bandList.get(i).getBandSpectrum(); //First two values should be chopped
			if(!Arrays.equals(bandList.get(i).getBandSpectrum(),frequencySepctrum)) {
				System.out.printf("Error by setting frequecySpectrum. BandSpectrum is wrong!\n");
				testfailure = true;
				break;
			}	
		}
		
		// Testing setting negative frequency:
		bandList.get(0).setMinFreq(100); // Set to a valid value
		bandList.get(0).setMinFreq(-1); // Set to a incorrect value -> should not be set
		//Read back the min. frequency and check if its equal to the set frequency
		if(bandList.get(0).getMinFreq() != 100) {
			System.out.printf("Error by negative min. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", 100, bandList.get(0).getMinFreq());
			testfailure = true;
		}
		// Testing setting negative frequency:
		bandList.get(0).setMaxFreq(100); // Set to a valid value
		bandList.get(0).setMaxFreq(-1); // Set to a incorrect value -> should not be set
		//Read back the max. frequency and check if its equal to the set frequency
		if(bandList.get(0).getMaxFreq() != 100) {
			System.out.printf("Error by negative max. frequency. Expected freq, Actual freq: %5d, %5d [Hz]\n", 100, bandList.get(0).getMaxFreq());
			testfailure = true;
		}
		
		// Testing equal-Method of band
		Band band1 = new Band(testchannel,100,100);
		Band band2 = band1;
		if(!band1.equals(band2)) {
			System.out.printf("Error by Equal-Methode of Bands!\n");
			testfailure = true;
		}
		
		// Testing chopping of bandspectrum
		Band band3 = new Band(testchannel,1,10);
		int[] frequencySepctrum = new int[] {-10,-2,0,2,10}; //Only Value 2 and 10 should be in the band spectrum
		band3.setFrequencySpectrum(frequencySepctrum);
		int[] getBandSepctrum = band3.getBandSpectrum(); //First two values should be chopped
		if(!Arrays.equals(band3.getBandSpectrum(),new int[] {2,10})) {
			System.out.printf("Error by chopping band spectrum. Band spectrum is wrong!\n");
			testfailure = true;
		}	
		
		// Give out if test was successful or not
		if(!testfailure) {
			System.out.printf("Band-Model successfully tested!\n");
		}else {
			System.out.printf("Band-Model has errors!\n");
		}
	}
}
