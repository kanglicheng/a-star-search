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

        Node startNode1 = new Node(10, 5);
        Node terminalNode1 = new Node(4, 9);
        List<Node> terminalNodes1 = Arrays.asList(terminalNode1);

        System.out.println("1. Path from " + startNode1 + " to " + terminalNode1 + ":");

        List<Node> path1 = aStar.run(territoryMap, startNode1, terminalNodes1);

        System.out.println("Path: " + path1);
        System.out.println("Path Length: " + path1.get(path1.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path1.size() - 1));

        System.out.println();

        Node startNode2 = new Node(15, 5);
        Node terminalNode2 = new Node(1, 15);
        List<Node> terminalNodes2 = Arrays.asList(terminalNode2);

        System.out.println("2. Path from " + startNode2 + " to " + terminalNode2 + ":");

        List<Node> path2 = aStar.run(territoryMap, startNode2, terminalNodes2);

        System.out.println("Path: " + path2);
        System.out.println("Path Length: " + path2.get(path2.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path2.size() - 1));
    }
}
