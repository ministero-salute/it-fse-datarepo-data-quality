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
package it.finanze.sanita.fse2.dr.dataquality;

import it.finanze.sanita.fse2.dr.dataquality.scheduler.SearchParamScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static it.finanze.sanita.fse2.dr.dataquality.config.Constants.Profile.TEST;
import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_REFRESH_SCHEDULER;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(TEST)
@AutoConfigureMockMvc
class SchedulerTest {

	@SpyBean
	private SearchParamScheduler scheduler;

	@Autowired
	private MockMvc mvc;

	@Test
	void schedulerAlreadyRunning() throws Exception {
		// Setup condition
		doReturn(true).when(scheduler).isRunning();
		// Run request
		mvc.perform(post(API_REFRESH_SCHEDULER)).andExpectAll(
			status().isLocked(),
			content().contentType(APPLICATION_PROBLEM_JSON_VALUE)
		);
	}

}