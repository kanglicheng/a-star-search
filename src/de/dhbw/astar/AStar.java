package de.dhbw.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.exceptions.NodeOutOfBoundsException;
import de.dhbw.model.Node;
import de.dhbw.model.TerritoryMap;

/**
 * The AStar class represents the implementation of the A*-Algorithm. Its run()-method receives an array with the
 * {@link TerritoryMap}, a start {@link Node} and a list of terminal {@link Node}s to calculate the shortest path from
 * the start {@link Node} to the terminal {@link Node}s.
 */
public class AStar {

    /**
     * Finds the shortest path from a start {@link Node} to a terminal {@link Node}s.
     *
     * @param territoryMap
     *            an array with the {@link TerritoryMap}
     * @param startNode
     *            a {@link Node} that represents the start
     * @param terminalNode
     *            a {@link Node} that represents the goal
     * @return a list of {@link Node} objects that represents the shortest path. If no path is found, an empty list is
     *         returned.
     * @throws NodeOutOfBoundsException
     *             if one of the inserted {@link Node}s is not within the bounds of the {@link TerritoryMap}
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
     * Estimates the costs from a origin {@link Node} to a list of destination {@link Node}s by calculating a heuristic
     * function h for each {@link Node} of the list and selecting the smallest value.
     *
     * @param originNode
     * @param destinationNodes
     * @return the estimated costs from the originNode to the destinationNodes
     */
    private double estimateCosts(Node originNode, List<Node> destinationNodes) {
        double hValue = 0;
        for (Node destinationNode : destinationNodes) {
            double x = this.estimateCosts(originNode, destinationNode);
            if (x < hValue) {
                hValue = x;
            }
        }
        return hValue;
    }

    /**
     * Estimates the costs from a origin {@link Node} to a destination {@link Node} by calculating a heuristic function
     * h with the the aid of the Pythagoras' theorem.
     *
     * @param originNode
     * @param destinationNode
     * @return the estimated costs from the originNode to the destinationNode
     */
    private double estimateCosts(Node originNode, Node destinationNode) {
        double a = Math.abs(originNode.getXCoordinate() - destinationNode.getXCoordinate()) + 1;
        double b = Math.abs(originNode.getYCoordinate() - destinationNode.getYCoordinate()) + 1;
        double hValue = Math.sqrt(a * a + b * b);
        return hValue;
    }

    /**
     * Calculates the factor to increase the costs of the path by 10% after each 5 {@link Node}s.
     *
     * @param path
     *            a list of {@link Node}s that represent the walked path
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
     *            the list with all open {@link Node}s
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
