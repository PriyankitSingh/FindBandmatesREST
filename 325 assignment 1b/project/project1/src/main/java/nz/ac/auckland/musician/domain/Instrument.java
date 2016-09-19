/**
 * 
 */
package nz.ac.auckland.musician.domain;

/**
 * @author Priyankit
 *
 */
public enum Instrument {
	GUITAR, BASS, DRUMS, KEYBOARD, WOODWIND, VOCALS, BOWEDSTRINGS, OTHER;
	
	/**
	 * Creates a Instrument value from a text string.
	 * 
	 */
	public static Instrument fromString(String text) {
		if (text != null) {
			for (Instrument g : Instrument.values()) {
				if (text.equalsIgnoreCase(g.toString())) {
					return g;
				}
			}
		}
		return null;
	}
}
