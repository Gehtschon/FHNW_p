package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import channel.SignalChannel;
import logger.Logger_Singleton;
import rule.Rule;

/**
 * How to use WriteJson WriteJson_Singelton tmp =
 * WriteJson_Singelton.getInstance();
 */

public class WriteJson_Singelton {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private String logPath = System.getProperty("user.home"); // default path -> Maybe change
	private static WriteJson_Singelton WriteJson_Singelton; // only instance of logger
//	private File logFile = new File(String.format("%s\\%s.json", logPath, "jasontest"));
	private File gsonLogFile = new File(String.format("%s\\%s.json", logPath, "GSON"));

	private FileReader reader;
	private FileWriter writer;

	public synchronized static WriteJson_Singelton getInstance() {

		if (WriteJson_Singelton == null) {
			WriteJson_Singelton = new WriteJson_Singelton();
		}
		return WriteJson_Singelton;
	}

	public void writeConfigToJson(Config_Singleton config) {

		// GSON
		try (FileWriter writer = new FileWriter(gsonLogFile)) {
			gson.toJson(config, writer);

		} catch (IOException e) {
			System.out.println("ERROR GSON");
		}

	}

	/**
	 * Method to read the current configPath (directory only)
	 * 
	 * @return logPath
	 */
	public String getLogPath() {
		return logPath;
	}

	/**
	 * Method to set the configPath (directory only)
	 * 
	 * @param logPath
	 */
	public void setLogPath(String logPath) {
		this.logPath = logPath;
		gsonLogFile = new File(String.format("%s\\%s.json", logPath, "GSON"));
	}

	// This code dosen't work wit writeconfigtojson
//	private FileWriter getFileWriter() {
//		if (this.writer == null) {
//			try {
//				this.writer = new FileWriter(gsonLogFile);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//
//		}
//		return this.writer;
//
//	}

	private FileReader getFileReader() {
		this.createFile();
		if (this.reader == null) {
			try {
				this.reader = new FileReader(gsonLogFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.reader;

	}

	public JsonConfig getObject() {

		// GSON
		this.createFile();
		// Create Channels
		JsonConfig gsonconfig = null;
		try {
			gsonconfig = gson.fromJson(this.getFileReader(), JsonConfig.class);
		} catch (Exception e) {
			Logger_Singleton.getInstance().freereport("Problem mit JSON FILE: " + e.getMessage());
			this.writeConfigToJson(Config_Singleton.getInstance());

		}


		return gsonconfig;
	}

	private void createFile() {
		File directory = new File(logPath);
		File logFile = new File(String.format("%s\\%s.json", logPath, "GSON")); // %tF
		try {
			// Check if directory exist, when not create them
			if (!directory.exists()) {
				directory.mkdirs();
			}
			logFile.createNewFile(); // Creates file if it does not already exist
			this.gsonLogFile = logFile; // Overwrite the logFile from the class with the new created
		} catch (IOException e) {
			System.out.println("An error occurred by creating the GSONFILE");
		}
	}

}
