package nz.ac.auckland.musician.domain;

public enum Experience {
	BEGINNER, AMATEUR, INTERMEDIATE, ADVANCED, PROFESSIONAL;
	
	/**
	 * Creates a Instrument value from a text string.
	 * 
	 */
	public static Experience fromString(String text) {
		if (text != null) {
			for (Experience g : Experience.values()) {
				if (text.equalsIgnoreCase(g.toString())) {
					return g;
				}
			}
		}
		return null;
	}	
}
