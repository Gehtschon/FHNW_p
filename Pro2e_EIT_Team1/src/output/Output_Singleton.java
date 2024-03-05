package output;

public class Output_Singleton implements I_Output {
	
	private static Output_Singleton INSTANCE_OF_OUTPUT; // only instance of OUTPUT
	private I_NotificationHandler notificationHandler;
	
	/**
	 * Private constructor
	 */
	private Output_Singleton() {
	}
	
	public synchronized static Output_Singleton getInstance() {

		if (INSTANCE_OF_OUTPUT == null) {
			INSTANCE_OF_OUTPUT = new Output_Singleton();
		}
		return INSTANCE_OF_OUTPUT;
	}
		
	
	@Override
	public void sendSms(String recipients, String message) {
		notificationHandler.sendSms(recipients, message);
	}

	@Override
	public void sendEmail(String recipients, String subject, String message) {
		notificationHandler.sendEmail(recipients, subject, message);
	}

	@Override
	public void phoneCall(String recipients, String message) {
		notificationHandler.phoneCall(recipients, message);
	}

}
