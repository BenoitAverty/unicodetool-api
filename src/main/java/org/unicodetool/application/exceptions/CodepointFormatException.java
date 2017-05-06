package org.unicodetool.application.exceptions;

/**
 * Exception thrown when a codepoint described as string has an incorrect format.
 */
public class CodepointFormatException extends RuntimeException {
    public CodepointFormatException(String strValue) {
        super("The value " + strValue + " has an incorrect format to describe a codepoint");
    }

    public CodepointFormatException(String strValue, Throwable t) {
        super("The value " + strValue + " has an incorrect format to describe a codepoint", t);
    }
}
