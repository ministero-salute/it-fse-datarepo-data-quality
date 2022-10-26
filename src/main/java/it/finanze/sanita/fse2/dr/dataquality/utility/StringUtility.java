package it.finanze.sanita.fse2.dr.dataquality.utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StringUtility {

	/**
	 * Private constructor to avoid instantiation.
	 */
	private StringUtility() {
		// Constructor intentionally empty.
	}

	/**
	 * Returns a new String with replaced characters.
	 * 
	 * @param oldChar character to replace.
	 * @param newChar replaced character.
	 * @param str     String.
	 * @return String.
	 */
	public static String charReplacement(char oldChar, char newChar, String str) {
		String newStr = null;
		try {
			newStr = str.replace(oldChar, newChar);
		} catch (Exception e) {
			log.error("Errore", e);
		}

		return newStr;
	}

	/**
	 * Returns a string divided by an input char.
	 * 
	 * @param splitChar character given in input.
	 * @param str       String.
	 * @return String[] divided strings.
	 */
	public static String[] wordSplit(String splitChar, String str) {
		String[] newStr = null;
		try {
			str = str.trim();
			newStr = str.split(splitChar);
		} catch (Exception e) {
			log.error("Errore", e);
		}
		return newStr;
	}
}