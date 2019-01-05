package de.dhbw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A territory map basically consists of an array of Nodes that represents the whole territory.
 */
public class TerritoryMap {

    public Node[][] territoryMap;

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
    public Node[][] initialize(List<List<String>> matrix, Map<Integer, Integer> costsTable) {
        int heigth = matrix.size();
        int width = matrix.get(0).size();

        Node[][] territoryMap = new Node[heigth][width];
        for (int i = 0; i < territoryMap.length; i++) {
            for (int j = 0; j < territoryMap[i].length; j++) {
                territoryMap[j][i] = new Node(i + 1, j + 1);
                territoryMap[j][i].setKValue(costsTable.get(Integer.parseInt(matrix.get(j).get(i))));
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
        int hight = this.territoryMap.length;
        int width = this.territoryMap[0].length;
        List<Node> successors = new ArrayList<>();

        if (node.getXCoordinate() + 1 <= width) {
            successors.add(this.territoryMap[node.getYCoordinate() - 1][node.getXCoordinate()]);
        }
        if (node.getXCoordinate() - 1 >= 1) {
            successors.add(this.territoryMap[node.getYCoordinate() - 1][node.getXCoordinate() - 2]);
        }
        if (node.getYCoordinate() + 1 <= hight) {
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

}
