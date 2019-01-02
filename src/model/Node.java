package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Node represents a single field in the territory map. A node can be determined by its x and y coordinates.
 * Furthermore, there are fields for the g, k, h and f values that are necessary to find the best path in a territory
 * map.
 */
public class Node {

    private int xCoordinate;
    private int yCoordinate;
    private double gValue;
    private double hValue;
    private double fValue;
    private int kValue;
    private List<Node> path;

    public Node(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.path = new ArrayList<>();
        this.path.add(this);
    }

    /**
     * Returns the x coordinate of a Node.
     *
     * @return xValue
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y coordinate of a Node.
     *
     * @return yValue
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Returns the g value of a Node.
     *
     * @return gValue
     */
    public double getGValue() {
        return gValue;
    }

    /**
     * Sets the gValue of a Node.
     *
     * @param gValue
     */
    public void setGValue(double gValue) {
        this.gValue = gValue;
    }

    /**
     * Returns the f value of a Node.
     *
     * @return fValue
     */
    public double getFValue() {
        return fValue;
    }

    /**
     * Returns the k value of a Node.
     *
     * @return kValue
     */
    public int getKValue() {
        return kValue;
    }

    /**
     * Sets the kValue of a Node.
     *
     * @param kValue
     */
    public void setKValue(int kValue) {
        this.kValue = kValue;
    }

    /**
     * Returns the path of a Node.
     *
     * @return path
     */
    public List<Node> getPath() {
        return path;
    }

    /**
     * Adds a Node to the path.
     *
     * @param node
     */
    public void addToPath(Node node) {
        this.path.add(node);
    }

    /**
     * Adds a list of Nodes to the path.
     *
     * @param nodes
     */
    public void addToPath(List<Node> nodes) {
        this.path.addAll(nodes);
    }

    /**
     * Calculates a heuristic function h that is used to estimate the cheapest costs to the terminal node.
     *
     * @param terminalNode
     */
    public void calculateHValue(Node terminalNode) {
        double a = Math.abs(terminalNode.xCoordinate - this.xCoordinate) + 1;
        double b = Math.abs(terminalNode.yCoordinate - this.yCoordinate) + 1;
        this.hValue = Math.sqrt(a * a + b * b);
        this.fValue = this.gValue + this.hValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xCoordinate;
        result = prime * result + yCoordinate;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (xCoordinate != other.xCoordinate)
            return false;
        if (yCoordinate != other.yCoordinate)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + this.xCoordinate + ", " + this.yCoordinate + ")";
    }

}
