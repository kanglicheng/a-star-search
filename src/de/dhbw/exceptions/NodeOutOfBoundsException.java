package de.dhbw.exceptions;

public class NodeOutOfBoundsException extends Exception {

    /**
     * The Exception is used to check if a Node is within the bounds of a territory map.
     */
    private static final long serialVersionUID = -6265760326094132668L;

    public NodeOutOfBoundsException(String msg) {
        super(msg);
    }

}
