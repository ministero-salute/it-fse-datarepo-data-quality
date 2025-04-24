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
