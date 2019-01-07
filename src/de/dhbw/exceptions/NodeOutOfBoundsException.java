package de.dhbw.exceptions;

import de.dhbw.model.Node;
import de.dhbw.model.TerritoryMap;

/**
 * The Exception is used to check if a {@link Node} is within the bounds of a {@link TerritoryMap}.
 */
public class NodeOutOfBoundsException extends Exception {

    private static final long serialVersionUID = -6265760326094132668L;

    public NodeOutOfBoundsException(String msg) {
        super(msg);
    }

}
