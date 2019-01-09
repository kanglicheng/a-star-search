package de.dhbw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.dhbw.astar.AStar;
import de.dhbw.datareader.DataReader;
import de.dhbw.exceptions.InvalidArgumentException;
import de.dhbw.exceptions.InvalidNodeException;
import de.dhbw.exceptions.InvalidNumberOfArgumentsException;
import de.dhbw.exceptions.NodeOutOfBoundsException;
import de.dhbw.model.Node;
import de.dhbw.model.TerritoryMap;

public class AStarAlgorithmApplication {

    private static final int MIN_NUMBER_OF_ARGUMENTS = 5;

    /**
     * Runs the A*-Algorithm by reading the necessary data from the command line arguments.
     *
     * @throws InvalidNumberOfArgumentsException
     *             thrown if the number of arguments is invalid
     * @throws InvalidArgumentException
     *             thrown if an argument has a wrong format
     * @throws IOException
     *             thrown if the requested file is not existing
     * @throws NodeOutOfBoundsException
     *             thrown if the coordinates of a {@link Node} are not matching with the coordinates of the
     *             {@link TerritoryMap}
     * @throws InvalidNodeException
     *             thrown if a {@link Node} has not-positive coordinates.
     */
    public static void main(String[] args)
        throws InvalidNumberOfArgumentsException, InvalidArgumentException, IOException,
        NodeOutOfBoundsException, InvalidNodeException {

        checkArguments(args);

        TerritoryMap territoryMap = createTerritoryMap(args);
        Node startNode = createStartNode(args);
        List<Node> terminalNodes = createTerminalNodes(args);
        AStar aStar = new AStar();

        System.out.println("Searching Path from " + startNode + " to " + terminalNodes + " ...");

        List<Node> path = aStar.run(territoryMap, startNode, terminalNodes);

        System.out.println("Path: " + path);
        System.out.println("Path Length: " + path.get(path.size() - 1).getGValue());
        System.out.println("Number of Steps: " + (path.size() - 1));
    }

    /**
     * Checks the format of the arguments and the number of arguments and throws an exception if necessary.
     *
     * @param args
     *            the arguments from the command line
     * @throws InvalidNumberOfArgumentsException
     *             thrown if the number of arguments is invalid
     * @throws InvalidArgumentException
     *             thrown if an argument has a wrong format
     */
    private static void checkArguments(String[] args)
        throws InvalidNumberOfArgumentsException, InvalidArgumentException {
        if (args.length < MIN_NUMBER_OF_ARGUMENTS) {
            throw new InvalidNumberOfArgumentsException(
                "At least " + MIN_NUMBER_OF_ARGUMENTS + " arguments are necessary!");
        }
        if ((args.length % 2) == 0) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments!");
        }

        if (!args[0].endsWith(".csv")) {
            throw new InvalidArgumentException("The first argument should be a .csv-file!");
        }
    }

    /**
     * Creates the {@link TerritoryMap} of the command line arguments.
     *
     * @param args
     *            the arguments from the command line
     * @return the {@link TerritoryMap}
     * @throws IOException
     *             thrown if the requested file is not existing
     * @throws InvalidNodeException
     *             thrown if a {@link Node} has not-positive coordinates.
     */
    private static TerritoryMap createTerritoryMap(String[] args) throws IOException, InvalidNodeException {
        String fileLocation = args[0];
        DataReader dataReader = new DataReader();
        List<List<Integer>> matrix = dataReader.readMatrix(fileLocation);
        Map<Integer, Double> costsTable = dataReader.readCostsTable(fileLocation);

        System.out.println("Matrix and costs table has been successfully loaded from " + fileLocation);

        TerritoryMap territoryMap = new TerritoryMap();
        territoryMap.initialize(matrix, costsTable);
        return territoryMap;
    }

    /**
     * Creates the start {@link Node} of the command line arguments.
     *
     * @param args
     *            the arguments from the command line
     * @return the {@link Node} that represents the start node
     * @throws InvalidArgumentException
     *             thrown if an argument has a wrong format
     */
    private static Node createStartNode(String[] args) throws InvalidArgumentException {
        Node startNode;
        try {
            startNode = new Node(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Rear arguments have to be integers!");
        } catch (InvalidNodeException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
        return startNode;
    }

    /**
     * Creates the terminal {@link Node}s of the command line arguments.
     *
     * @param args
     *            the arguments from the command line
     * @return a list of {@link Node}s that represents the terminal nodes
     * @throws InvalidArgumentException
     *             thrown if an argument has a wrong format
     */
    private static List<Node> createTerminalNodes(String[] args) throws InvalidArgumentException {
        List<Node> terminalNodes = new ArrayList<>();
        try {
            for (int i = 3; i < args.length; i = i + 2) {
                Node terminalNode = new Node(Integer.parseInt(args[i]), Integer.parseInt(args[i + 1]));
                terminalNodes.add(terminalNode);
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Rear arguments have to be integers!");
        } catch (InvalidNodeException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
        return terminalNodes;
    }

}
