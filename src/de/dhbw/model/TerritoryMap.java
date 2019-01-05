package de.dhbw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.dhbw.exceptions.NodeOutOfBoundsException;

/**
 * A territory map basically consists of an array of Nodes that represents the whole territory.
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
     * Creates the final territory map of the description matrix and the costs table.
     *
     * @param matrix
     *            the raw matrix
     * @param costsTable
     *            maps the description code of the matrix to its costs
     * @return an array with the final territory map
     */
    public Node[][] initialize(List<List<Integer>> matrix, Map<Integer, Double> costsTable) {
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
     * Resets the territory map to its original appearance by reseting each node.
     */
    public void reset() {
        for (Node[] nodes : territoryMap) {
            for (Node node : nodes) {
                node.reset();
            }
        }
    }

    /**
     * Checks whether a node is within the bound of the territory map.
     *
     * @param startNode
     *            the node that should be checked
     * @throws NodeOutOfBoundsException
     *             if the node is not within the bounds of the territory map
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
     * Checks whether a list of nodes is within the bound of the territory map.
     *
     * @param nodes
     *            the nodes that should be checked
     * @throws NodeOutOfBoundsException
     *             if one of the nodes is not within the bounds of the territory map
     */
    public void checkNodeMembership(List<Node> nodes) throws NodeOutOfBoundsException {
        for (Node node : nodes) {
            this.checkNodeMembership(node);
        }
    }

}
