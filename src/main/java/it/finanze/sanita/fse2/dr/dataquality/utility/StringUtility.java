/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.utility;

import com.google.gson.Gson;

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

	/**
	 * Returns {@code true} if the String passed as parameter is null or empty.
	 * 
	 * @param str	String to validate.
	 * @return		{@code true} if the String passed as parameter is null or empty.
	 */
	public static boolean isNullOrEmpty(final String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * Transformation from Object to Json.
	 * 
	 * @param obj	object to transform
	 * @return		json
	 */
	public static String toJSON(final Object obj) {
		return new Gson().toJson(obj);
	}
}