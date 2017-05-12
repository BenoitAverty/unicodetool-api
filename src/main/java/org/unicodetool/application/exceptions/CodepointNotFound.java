package org.unicodetool.application.exceptions;

/**
 * Exception thrown when the consumer tries to find a codepoint that doesn't exist
 * in the Unicode Character Database (outside range or unassigned character)
 */
public class CodepointNotFound extends RuntimeException {
}
