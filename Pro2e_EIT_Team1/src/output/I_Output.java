package output;

public interface I_Output {

	/**
	 * Send a TextMessage to recipients
	 * 
	 * @param recipients String, multiple numbers separated by semicolon
	 * @param message    message to be sent, max 160 characters ASCII
	 */
	void sendSms(String recipients, String message);

	/**
	 * Send an email to recipients
	 * @param recipients recipients String, multiple e-mail addresses separated by semicolon
	 * @param subject
	 * @param message
	 */
	void sendEmail(String recipients, String subject, String message);
	
	
	/**
	 * Issue a voice call
	 * 
	 * @param recipients numbers, separated by semicolon if multiple
	 * @param message message to be played back
	 */
	void phoneCall(String recipients, String message);

}

