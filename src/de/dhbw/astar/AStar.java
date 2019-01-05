package de.dhbw.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.model.Node;
import de.dhbw.model.TerritoryMap;

/**
 * The PathFinder receives an array with the territory map, a start node and a terminal node to calculate the shortest
 * path from the start node to the terminal node.
 */
public class AStar {

    /**
     * Finds the shortest path from a start node to a terminal node.
     *
     * @param territoryMap
     *            an array with the territory map
     * @param startNode
     *            a {@link Node} that represents the start
     * @param terminalNode
     *            a {@link Node} that represents the goal
     * @return a list of {@link Node} objects that represents the shortest path. If no path is found, an empty list is
     *         returned.
     */
    public List<Node> run(TerritoryMap territoryMap, Node startNode, Node terminalNode) {

        territoryMap.reset(); // Make sure the territory map does not contain junk data

        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        openList.add(startNode);
        startNode.calculateHValue(terminalNode);

        while (!openList.isEmpty()) {
            Node optimalNode = getNodeWithLowestFValue(openList);
            openList.remove(optimalNode);
            closedList.add(optimalNode);
            if (optimalNode.equals(terminalNode)) {
                System.out.println("Found Solution!");
                Collections.reverse(optimalNode.getPath());
                return optimalNode.getPath();
            }
            double factor = calculatePathFactor(optimalNode.getPath());
            for (Node successor : territoryMap.getNeighbours(optimalNode)) {
                double gValue = optimalNode.getGValue() + successor.getKValue() * factor;
                if (!openList.contains(successor) && !closedList.contains(successor)) {
                    openList.add(successor);
                    successor.addToPath(optimalNode.getPath());
                    successor.setGValue(gValue);
                    successor.calculateHValue(terminalNode);
                } else if (openList.contains(successor) && !closedList.contains(successor)
                    && gValue < successor.getGValue()) {
                    successor.addToPath(optimalNode.getPath());
                    successor.setGValue(gValue);
                    successor.calculateHValue(terminalNode);
                }
            }
        }
        System.out.println("Found No Solution!");
        return new ArrayList<Node>();
    }

    /**
     * Calculates the factor to increase the costs of the path by 10% after each 5 nodes.
     *
     * @param node
     * @return the factor
     */
    private double calculatePathFactor(List<Node> path) {
        int pathLength = path.size();
        double factor = 1;
        while (pathLength > 5) {
            factor = factor * 1.1;
            pathLength -= 5;
        }
        return factor;
    }

    /**
     * Determines the {@link Node} with the lowest f value.
     *
     * @param openList
     *            the list with all open nodes
     * @return the {@link Node} with the lowest f value
     */
    private Node getNodeWithLowestFValue(List<Node> openList) {
        double min = openList.get(0).getFValue();
        Node bestNode = openList.get(0);
        for (Node node : openList) {
            if (node.getFValue() < min) {
                min = node.getFValue();
                bestNode = node;
            }
        }
        return bestNode;
    }

}
