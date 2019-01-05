package de.dhbw.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.exceptions.NodeOutOfBoundsException;
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
     * @throws NodeOutOfBoundsException
     *             if one of the inserted nodes is not within the bounds of the territory map
     */
    public List<Node> run(TerritoryMap territoryMap, Node startNode, List<Node> terminalNodes)
        throws NodeOutOfBoundsException {

        territoryMap.checkNodeMembership(startNode);
        territoryMap.checkNodeMembership(terminalNodes);

        territoryMap.reset(); // Make sure the territory map does not contain junk data

        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        openList.add(startNode);

        startNode.setFValue(this.estimateCosts(startNode, terminalNodes));

        while (!openList.isEmpty()) {
            Node optimalNode = getNodeWithLowestFValue(openList);
            openList.remove(optimalNode);
            closedList.add(optimalNode);
            if (terminalNodes.contains(optimalNode)) {
                System.out.println("Found Solution!");
                Collections.reverse(optimalNode.getPath());
                return optimalNode.getPath();
            }
            double factor = calculatePathFactor(optimalNode.getPath());
            for (Node successor : territoryMap.getNeighbours(optimalNode)) {
                double gValue = optimalNode.getGValue() + successor.getKValue() * factor;
                double fValue = this.estimateCosts(successor, terminalNodes) + gValue;
                if (!openList.contains(successor) && !closedList.contains(successor)) {
                    openList.add(successor);
                    successor.addToPath(optimalNode.getPath());
                    successor.setGValue(gValue);
                    successor.setFValue(fValue);
                } else if (openList.contains(successor) && !closedList.contains(successor)
                    && gValue < successor.getGValue()) {
                    successor.addToPath(optimalNode.getPath());
                    successor.setGValue(gValue);
                    successor.setFValue(fValue);
                }
            }
        }
        System.out.println("Found No Solution!");
        return new ArrayList<Node>();
    }

    /**
     * Estimates the costs from the current node to the terminalNodes by calculating a heuristic function h for each
     * terminal node and selecting the smallest value.
     *
     * @param currentNode
     * @param terminalNodes
     * @return hValue the estimated costs from the current node to the terminal nodes
     */
    private double estimateCosts(Node currentNode, List<Node> terminalNodes) {
        double hValue = 0;
        for (Node terminalNode : terminalNodes) {
            double x = this.estimateCosts(currentNode, terminalNode);
            if (x < hValue) {
                hValue = x;
            }
        }
        return hValue;
    }

    /**
     * Estimates the costs from currentNode to terminalNode by calculating a heuristic function h with the the aid of
     * the Pythagoras' theorem.
     *
     * @param currentNode
     * @param terminalNode
     * @return hValue the estimated costs from the current node to the terminal node
     */
    private double estimateCosts(Node currentNode, Node terminalNode) {
        double a = Math.abs(currentNode.getXCoordinate() - terminalNode.getXCoordinate()) + 1;
        double b = Math.abs(currentNode.getYCoordinate() - terminalNode.getYCoordinate()) + 1;
        double hValue = Math.sqrt(a * a + b * b);
        return hValue;
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
