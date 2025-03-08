package org.theleakycauldron.diagonalley.outboxservice.exceptions;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class OutboxNotExistsException extends RuntimeException {

    public OutboxNotExistsException(String message) {
        super(message);
    }
}
