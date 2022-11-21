/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.config;

/**
 * Constants application.
 */
public final class Constants {

	/**
	 *	Path scan.
	 */
	public static final class ComponentScan {

		public static final String BASE = "it.finanze.sanita.fse2.dr.dataquality";

		private ComponentScan() {
			//This method is intentionally left blank.
		}

	}


	public static final class Profile {
		
		public static final String TEST = "test";

		public static final String DEV = "dev";

		public static final String TEST_PREFIX = "test_";

		/** 
		 * Constructor.
		 */
		private Profile() {
			//This method is intentionally left blank.
		}

	}
   

	/**
	 *	Constants.
	 */
	private Constants() {

	}

}
