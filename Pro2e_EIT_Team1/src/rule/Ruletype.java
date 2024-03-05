package rule;

public enum Ruletype {
	// default case at the start
	CUSTOM("misc"), INFO("Info"), WARNING("Warnung"), DANGER("Alarm"); 

	final String name;

	Ruletype(String name) {
		this.name = name;

	}

}
