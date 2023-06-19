/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
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
