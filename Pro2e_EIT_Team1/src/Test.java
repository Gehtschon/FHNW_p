import java.util.ArrayList;
import java.util.Iterator;

import band.Band;
import band.I_Band;
import channel.I_Channel;
import channel.SignalChannel;
import rule.I_Rule;
import rule.Rule;
import rule.Rule_Creator_Singelton;
import rule.Ruletype;
import ueberwachung.Ueberwachung_Singelton;

public class Test {

	private static Rule_Creator_Singelton rulecreator = Rule_Creator_Singelton.GetInstance();
	private static Ueberwachung_Singelton ueberwachung = Ueberwachung_Singelton.getInstance();

	/**
	 * Method to test the build process of a rule
	 */
	public static void testcreateRule() {

		// generate Channels
		SignalChannel channel1 = new SignalChannel("channel1", "location", "description");
		SignalChannel channel2 = new SignalChannel("channel2", "location", "description");
		SignalChannel channel3 = new SignalChannel("channel3", "location", "description");
		// add Channels to channelList
		ueberwachung.addChanelToList(channel1);
		ueberwachung.addChanelToList(channel2);
		ueberwachung.addChanelToList(channel3);
		// generate Bands
		Band band1 = new Band(channel1, 100, 200);
		Band band2 = new Band(channel2, 200, 300);
		// add bands to channels
		channel1.addBand(band1);
		channel2.addBand(band2);
		// generate BandLists
		int numberOfChannels = ueberwachung.getChannelArray().length;
		ArrayList<Band>[] bandList = new ArrayList[numberOfChannels];
		for (int i = 0; i < numberOfChannels; i++) {
			bandList[i] = new ArrayList<Band>();
		}
		// Add Bands to bandList1
		bandList[0].add(band1);
		bandList[0].add(band2);
		// create first rule --> This rule should not affect channel 1
		rulecreator.createRule("Rule1", Ruletype.WARNING, "056", "056", "@gmail", "message", true, true, true,
				bandList);
		// add Bands to bandList 2
		bandList[1].add(band1);
		bandList[2].add(band2);
		// create seccond Rule --> This rule should affect both channels

		rulecreator.createRule("RuleHoppsassa", Ruletype.WARNING, "056", "056", "@gmail", "message", true, true, true,
				bandList);

		// System.out.printf("The first rule in the List : %s
		// \n",((ueberwachung.getRuleList()).get(0)).getRuleName());
		// System.out.printf("The seccond rule in the List: %s
		// \n",((ueberwachung.getRuleList()).get(1)).getRuleName());
		System.out.println(ueberwachung.getRuleList());
		channel1 = null;
		channel2 = null;
		channel3 = null;
		band1 = null;
		band2 = null;

	}

	/**
	 * Method to test the build process of the method buildBandList() in
	 * Ueberwachung_Singelton
	 */
	public static void testBuildBandList() {

		// Build Channels
		SignalChannel channel1 = new SignalChannel("channel1", "location", "description");
		SignalChannel channel2 = new SignalChannel("channel2", "location", "description");
		SignalChannel channel3 = new SignalChannel("channel3", "location", "description");
		ueberwachung.addChanelToList(channel1);
		ueberwachung.addChanelToList(channel2);
		ueberwachung.addChanelToList(channel3);

		// Build Bands
		Band band11 = new Band(channel1, 100, 200);
		Band band12 = new Band(channel1, 200, 300);
		Band band13 = new Band(channel1, 200, 400);

		Band band21 = new Band(channel2, 200, 300);
		Band band22 = new Band(channel2, 400, 600);

		Band band31 = new Band(channel3, 400, 500);

		// Add Bands to the Channels
		channel1.addBand(band11);
		System.out.println("Added Band11 to channel1");
		channel1.addBand(band12);
		System.out.println("Added Band12 to channel1");
		channel1.addBand(band13);
		System.out.println("Added Band13 to channel1");

		channel2.addBand(band21);
		System.out.println("Added Band21 to channel2");
		channel2.addBand(band22);
		System.out.println("Added Band22 to channel2");

		channel3.addBand(band31);
		System.out.println("Added Band31 to channel3");

		// generate BandList
		ArrayList<Band>[] bandList = ueberwachung.getBandList();

		// Output of the max Values of each Band
		for (int i = 0; i < 3; i++) {
			ArrayList<Band> f = bandList[i];
			Iterator<Band> itr = f.iterator();
			System.out.printf("Channel %d: ", i + 1);
			while (itr.hasNext()) {
				// System.out.println("Liste: " + bandList[i]);
				System.out.printf("%d ", itr.next().getMaxFreq());

			}
			System.out.printf("\n");
			itr = null;
		}
//		channel1 = null;
//		channel2 = null;
//		channel3 = null;
//		band11 = null;
//		band12 = null;
//		band13 = null;
//		band21 = null;
//		band22 = null;
//		band31 = null;
//		bandList = null;
	}

	public static void createTestChannel(String name, String location, String description) {
		SignalChannel channel = new SignalChannel(name, location, description);
		ueberwachung.addChanelToList(channel);
		System.out.println("test Channel " + channel + " created");
	}
	public static void createTestBands(int channelIndex, int firstMin, int firstMax, int amountBands)	{
		for (int i = 0; i <amountBands; i++) {
			SignalChannel channel = ueberwachung.getChannelList().get(channelIndex);
			Band band = new Band(channel, firstMin+10*i, firstMax+10*i);
			channel.addBand(band);
		}

	}
}
