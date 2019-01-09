package de.dhbw.exceptions;

/**
 * The Exception is used to indicate if an argument has an invalid format.
 */
public class InvalidArgumentException extends Exception {

    private static final long serialVersionUID = 6773670128241416449L;

    public InvalidArgumentException(String msg) {
        super(msg);
    }

}
