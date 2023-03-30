/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.dr.dataquality.exceptions;

public class SchedulerRunningException extends IllegalStateException {
    /**
     * Constructs an IllegalStateException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public SchedulerRunningException(String s) {
        super(s);
    }
}
