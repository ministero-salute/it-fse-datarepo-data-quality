/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants application.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Profile {
		
		public static final String TEST = "test";

		public static final String DEV = "dev";

		public static final String TEST_PREFIX = "test_";
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Logs {

		public static final String ERR_SCH_RUNNING = "Il processo di aggiornamento risulta già avviato";
		public static final String DTO_RUN_TASK_QUEUED = "Processo avviato, verifica i logs";
		public static final String SRV_QUERY_RESPONSE = "{} status returned from eds";

	}

}
