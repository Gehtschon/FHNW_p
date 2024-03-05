package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import config.Config_Singleton;
import rule.I_Rule;
import rule.Ruletype;

/**
 * How to use Logger:
 * Logger_Singleton tmp = Logger_Singleton.getInstance();
 * tmp.freereport("close window");
 */

public class Logger_Singleton implements I_Logger {

	private String logPath = System.getProperty("user.home"); // default path -> Maybe change
	private static Logger_Singleton INSTANCE_OF_LOGGER; // only instance of logger
	private File logFile;

	public synchronized static Logger_Singleton getInstance() {

		if (INSTANCE_OF_LOGGER == null) {
			INSTANCE_OF_LOGGER = new Logger_Singleton();
		}
		return INSTANCE_OF_LOGGER;
	}

	/**
	 * Method to read the current logPath (directory only)
	 * 
	 * @return logPath
	 */
	public String getLogPath() {
		return logPath;
	}

	/**
	 * Method to set the logPath (directory only)
	 * 
	 * @param logPath
	 */
	public void setLogPath(String logPath) {
		this.logPath = logPath;
		Config_Singleton.getInstance().writejson();
	}

	/**
	 * Write a message in the logFile
	 */
	@Override
	public void freereport(String message) {
		writeLog(message);
	}

	@Override
	public void rulebroken(I_Rule rule) {
		writeLog(String.format("rulename: %s, message: %s", rule.getRuleName(), rule.getRuleMessage()));
	}

	@Override
	public void rulebroken(I_Rule rule, Ruletype ruletype) {
		writeLog(String.format("[%s],rulename: %s, message: %s", ruletype, rule.getRuleName(), rule.getRuleMessage()));
	}

	/**
	 * Creates a logfile for the current day
	 */
	private void createLogFile() {
		File directory = new File(logPath);
		File logFile = new File(String.format("%s\\%s_log.txt", logPath, java.time.LocalDate.now())); // %tF
		try {
			// Check if directory exist, when not create them
			if (!directory.exists()) {
				directory.mkdirs();
			}
			logFile.createNewFile(); // Creates file if it does not already exist
			this.logFile = logFile; // Overwrite the logFile from the class with the new created
		} catch (IOException e) {
			System.out.println("An error occurred by creating the logfile");
		}
	}

	/**
	 * Method to write the data from a log with the date to an textfile
	 * 
	 * @param data data who should be written in the textfile
	 */
	private void writeLog(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.now();
		String output = String.format("%s: %s\n", dtf.format(dateTime), data);
		try {
			createLogFile();
			Writer fileWriter = new BufferedWriter(new FileWriter(logFile, true));
			fileWriter.append(output);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred in writting to the log file");
		}
	}
}