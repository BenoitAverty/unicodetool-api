package org.unicodetool.application.exceptions;

/**
 * Exception thrown when looking for a codepoint with a value that's negative or greater than 0X10FFFF (1114111)
 */
public class ValueOutsideRangeException extends RuntimeException {
    public ValueOutsideRangeException(String msg) {
        super(msg);
    }
}
