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
package it.finanze.sanita.fse2.dr.dataquality.utility;

import java.util.UUID;

import org.apache.commons.codec.binary.Hex;

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
     * Returns {@code true} if the String passed as parameter is null or empty.
     * 
     * @param str String to validate.
     * @return {@code true} if the String passed as parameter is null or empty.
     */
    public static boolean isNullOrEmpty(final String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Encodes the byte array passed as parameter in hexadecimal.
     * 
     * @param input The byte array to encode.
     * @return The encoded byte array to String.
     */
    public static String encodeHex(final byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateWii() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    /**
     * Transformation from Json to Object.
     * 
     * @param <T>  Generic type of return
     * @param json json
     * @param cls  Object class to return
     * @return object
     */
    public static <T> T fromJSON(final String json, final Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    /**
     * Transformation from Object to Json.
     * 
     * @param obj object to transform
     * @return json
     */
    public static String toJSON(final Object obj) {
        return new Gson().toJson(obj);
    }

}
