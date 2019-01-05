package de.dhbw.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.dhbw.astar.AStar;
import de.dhbw.datareader.DataReader;
import de.dhbw.exceptions.NodeOutOfBoundsException;
import de.dhbw.model.Node;
import de.dhbw.model.TerritoryMap;

public class ExampleTerritory {

    private static final String SAMPLE_DATA_LOCATION = "resources/S_011_Daten.csv";
    private static final Node START_NODE = new Node(10, 5);
    private static final Node TERMINAL_NODE = new Node(4, 9);
    private static final List<Node> TERMINAL_NODES = Arrays.asList(TERMINAL_NODE);

    /**
     * Runs the A*-Algorithm for some sample data.
     */
    public static void main(String[] args) throws IOException, NodeOutOfBoundsException {
        DataReader dataReader = new DataReader();
        List<List<Integer>> matrix = dataReader.readMatrix(SAMPLE_DATA_LOCATION);
        Map<Integer, Double> costsTable = dataReader.readCostsTable(SAMPLE_DATA_LOCATION);

        TerritoryMap territoryMap = new TerritoryMap();
        territoryMap.initialize(matrix, costsTable);

        AStar aStar = new AStar();
        List<Node> path = aStar.run(territoryMap, START_NODE, TERMINAL_NODES);

        System.out.println("Path: " + path);
        System.out.println("Path Length: " + path.get(path.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path.size() - 1));

        path = aStar.run(territoryMap, START_NODE, TERMINAL_NODES);

        System.out.println("Path: " + path);
        System.out.println("Path Length: " + path.get(path.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path.size() - 1));

    }
}
