package example;

import java.io.IOException;
import java.util.List;

import model.Node;
import parser.TerritoryParser;
import pathFinder.PathFinder;

public class ExampleTerritory {

    private static final String SAMPLE_DATA_LOCATION = "resources/S_011_Daten.csv";
    private static final Node START_NODE = new Node(10, 5);
    private static final Node TERMINAL_NODE = new Node(4, 9);

    /**
     * Runs the a*-algorithm for the sample data in the resources folder.
     */
    public static void main(String[] args) throws IOException {
        TerritoryParser territoryParser = new TerritoryParser();
        Node[][] territoryMap = territoryParser.parse(SAMPLE_DATA_LOCATION);

        PathFinder pathFinder = new PathFinder();
        List<Node> path = pathFinder.findPath(territoryMap, START_NODE, TERMINAL_NODE);

        System.out.println("Path: " + path);
        System.out.println("Path Length: " + path.get(path.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path.size() - 1));
    }
}
