package de.dhbw.exceptions;

/**
 * The Exception is used to indicate if a invalid number of command line arguments is passed.
 */
public class InvalidNumberOfArgumentsException extends Exception {

    private static final long serialVersionUID = -158707971114612511L;

    public InvalidNumberOfArgumentsException(String msg) {
        super(msg);
    }

}
