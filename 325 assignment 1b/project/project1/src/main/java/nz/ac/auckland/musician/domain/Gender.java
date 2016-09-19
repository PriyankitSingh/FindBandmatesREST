package nz.ac.auckland.musician.domain;

/**
 * Simple enumeration for representing Gender.
 * 
 * @author Ian Warren
 *
 */
public enum Gender {
	MALE, FEMALE;
	
	/**
	 * Creates a Gender value from a text string.
	 * 
	 */
	public static Gender fromString(String text) {
		if (text != null) {
			for (Gender g : Gender.values()) {
				if (text.equalsIgnoreCase(g.toString())) {
					return g;
				}
			}
		}
		return null;
	}
}
