/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.dto.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorInstance {
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class IO {
                public static final String QUEUE = "/queue";
        }
}
