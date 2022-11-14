/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.dr.dataquality.config.Constants;
import it.finanze.sanita.fse2.dr.dataquality.utility.StringUtility;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = { Constants.ComponentScan.BASE })
@ActiveProfiles(Constants.Profile.TEST)
class StringUtilityTest {

	@Test
	@DisplayName("ReplaceChar")
	void testCharReplacement() {
		assertEquals("Pippi", StringUtility.charReplacement('o', 'i', "Pippo"));
	}

	@Test
	@DisplayName("SplitWord")
	void testWordSplit() {

		String[] array1 = new String[] { "Pipp", "plut" };
		String[] array2 = StringUtility.wordSplit("o", "Pippopluto");

		assertArrayEquals(array1, array2);
	}

}
