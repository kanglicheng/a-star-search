package de.dhbw.exceptions;

import de.dhbw.model.Node;

/**
 * The Exception is used to check if a {@link Node} is initializes in a correct way.
 */
public class InvalidNodeException extends Exception {

    private static final long serialVersionUID = -3628129558057095288L;

    public InvalidNodeException(String msg) {
        super(msg);
    }

}
