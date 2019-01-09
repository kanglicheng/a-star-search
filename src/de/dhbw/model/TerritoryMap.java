package de.dhbw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.dhbw.exceptions.InvalidNodeException;
import de.dhbw.exceptions.NodeOutOfBoundsException;

/**
 * A territory map basically consists of an two-dimensional array of {@link Node} objects that represents the whole
 * territory. The coordinates of the {@link Node} objects start at x = 1 and y = 1;
 */
public class TerritoryMap {

    private Node[][] territoryMap;
    private int height;
    private int width;

    /**
     * Returns the territory map.
     *
     * @return territoryMap
     */
    public Node[][] getTerritoryMap() {
        return territoryMap;
    }

    /**
     * Creates the final territory map of the description matrix and the costs table. The coordinates of the
     * {@link Node} objects start at x = 1 and y = 1;
     *
     * @param matrix
     *            the raw matrix
     * @param costsTable
     *            maps the description code of the matrix to its costs
     * @return an array with the final territory map
     * @throws InvalidNodeException
     *             thrown if a {@link Node} has not-positive coordinates.
     */
    public Node[][] initialize(List<List<Integer>> matrix, Map<Integer, Double> costsTable)
        throws InvalidNodeException {
        this.height = matrix.size();
        this.width = matrix.get(0).size();

        Node[][] territoryMap = new Node[this.height][this.width];
        for (int i = 0; i < territoryMap.length; i++) {
            for (int j = 0; j < territoryMap[i].length; j++) {
                territoryMap[j][i] = new Node(i + 1, j + 1);
                territoryMap[j][i].setKValue(costsTable.get(matrix.get(j).get(i)));
            }
        }
        this.territoryMap = territoryMap;
        return territoryMap;
    }

    /**
     * Returns all neighbours for a certain {@link Node}.
     *
     * @param node
     *            the {@link Node} whose neighbours should be returned
     * @return a list of all neighbours
     */
    public List<Node> getNeighbours(Node node) {
        List<Node> successors = new ArrayList<>();

        if (node.getXCoordinate() + 1 <= this.width) {
            successors.add(this.territoryMap[node.getYCoordinate() - 1][node.getXCoordinate()]);
        }
        if (node.getXCoordinate() - 1 >= 1) {
            successors.add(this.territoryMap[node.getYCoordinate() - 1][node.getXCoordinate() - 2]);
        }
        if (node.getYCoordinate() + 1 <= this.height) {
            successors.add(this.territoryMap[node.getYCoordinate()][node.getXCoordinate() - 1]);
        }
        if (node.getYCoordinate() - 1 >= 1) {
            successors.add(this.territoryMap[node.getYCoordinate() - 2][node.getXCoordinate() - 1]);
        }
        return successors;
    }

    /**
     * Resets the territory map to its original appearance by reseting each {@link Node}.
     */
    public void reset() {
        for (Node[] nodes : territoryMap) {
            for (Node node : nodes) {
                node.reset();
            }
        }
    }

    /**
     * Checks whether a {@link Node} is within the bound of the territory map.
     *
     * @param startNode
     *            the {@link Node} that should be checked
     * @throws NodeOutOfBoundsException
     *             if the {@link Node} is not within the bounds of the territory map
     */
    public void checkNodeMembership(Node startNode) throws NodeOutOfBoundsException {
        if (startNode.getXCoordinate() < 1 || startNode.getXCoordinate() > this.width) {
            throw new NodeOutOfBoundsException("The node is out of the bounds of the TerritoryMap " + startNode);
        }
        if (startNode.getYCoordinate() < 1 || startNode.getYCoordinate() > this.height) {
            throw new NodeOutOfBoundsException("The node is out of the bounds of the TerritoryMap " + startNode);
        }
    }

    /**
     * Checks whether a list of {@link Node} objects is within the bound of the territory map.
     *
     * @param nodes
     *            the {@link Node} objects that should be checked
     * @throws NodeOutOfBoundsException
     *             if one of the {@link Node} objects is not within the bounds of the territory map
     */
    public void checkNodeMembership(List<Node> nodes) throws NodeOutOfBoundsException {
        for (Node node : nodes) {
            this.checkNodeMembership(node);
        }
    }

}
