package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Node;

/**
 * The TerritoryParser is used to read a .csv file of a description matrix of a territory and its related costs table.
 */
public class TerritoryParser {

    /**
     * Extracts the matrix and the costs table of a .csv file and returns a territory map.
     *
     * @param fileLocation
     *            The path to the .csv file that contains the matrix and the costs table
     * @return territoryMap an array of {@link Node} objects that represents the territory
     * @throws IOException
     *             if the file cannot be opened
     */
    public Node[][] parse(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        // Read the matrix
        String st;
        List<List<String>> descriptionMatrix = new ArrayList<>();
        while ((st = reader.readLine()) != null && st.charAt(0) != ';') {
            String[] line = st.split(";");
            descriptionMatrix.add(Arrays.asList(line));
        }

        // Ignore the header of the costs table
        reader.readLine();

        // Read the costs table
        Map<Integer, Integer> codeToCosts = new HashMap<>();

        while ((st = reader.readLine()) != null && st.charAt(0) != ';') {
            String[] line = st.split(";");
            codeToCosts.put(Integer.parseInt(line[0]), Integer.parseInt(line[2]));
        }

        reader.close();
        return generateTerritoryMap(descriptionMatrix, codeToCosts);
    }

    /**
     * Calculates the final territory map of the description matrix and the costs table.
     *
     * @param descriptionMatrix
     *            a matrix containing the information of a territory
     * @param codeToCosts
     *            maps the code of the territory to the corresponding costs
     * @return an array with the final territory map
     */
    private Node[][] generateTerritoryMap(List<List<String>> descriptionMatrix, Map<Integer, Integer> codeToCosts) {
        int heigth = descriptionMatrix.size();
        int width = descriptionMatrix.get(0).size();

        Node[][] territoryMap = new Node[heigth][width];
        for (int i = 0; i < territoryMap.length; i++) {
            for (int j = 0; j < territoryMap[i].length; j++) {
                territoryMap[j][i] = new Node(i + 1, j + 1);
                territoryMap[j][i].setKValue(codeToCosts.get(Integer.parseInt(descriptionMatrix.get(j).get(i))));
            }
        }
        return territoryMap;
    }

}
