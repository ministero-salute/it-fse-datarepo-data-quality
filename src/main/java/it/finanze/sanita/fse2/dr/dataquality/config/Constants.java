/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.config;

/**
 * Constants application.
 */
public final class Constants {

 
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
	 * Constants used in logging. 
	 *
	 */	
	public static final class Logs {

		/**
		 * When a Connection Refused Error occurs 
		 */
        public static final String ERROR_CONNECTION_REFUSED = "Error: Connection refused";

        /**
         * Srv Query Response Log 
         */
		public static final String SRV_QUERY_RESPONSE = "{} status returned from eds";

		/**
		 * This method is intentionally left blank 
		 */
        private Logs() {

        }

	}
  
	/**
	 *	Constants.
	 */
	private Constants() {

	}

}
