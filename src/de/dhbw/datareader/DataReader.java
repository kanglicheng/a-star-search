package de.dhbw.datareader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The TerritoryParser is used to read a .csv file of a description matrix of a territory and its related costs table.
 */
public class DataReader {

    /**
     * Extracts the matrix of a .csv file.
     *
     * @param fileLocation
     *            The path to the .csv file that contains the matrix and the costs table
     * @return matrix List of Lists of Integers that represents raw matrix with the description code values
     * @throws IOException
     *             if the file cannot be opened
     */
    public List<List<Integer>> readMatrix(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<List<Integer>> matrix = new ArrayList<>();

        // Read the matrix
        // The semicolon line indicates the end of the matrix.
        String line;
        while ((line = reader.readLine()) != null && line.charAt(0) != ';') {
            List<Integer> convertedLine = this.convertListFromStringToInteger(Arrays.asList(line.split(";")));
            matrix.add(convertedLine);
        }
        reader.close();
        return matrix;
    }

    /**
     * Converts a list with string values to a list with integer values
     *
     * @param strings
     *            a list with string values
     * @return integers a list with integer values
     */
    private List<Integer> convertListFromStringToInteger(List<String> strings) {
        List<Integer> integers = new ArrayList<>();
        for (String entry : strings) {
            integers.add(Integer.parseInt(entry));
        }
        return integers;
    }

    /**
     * Extracts the costs table of a .csv file. A costs table maps the code of the description matrix to its costs.
     *
     * @param fileLocation
     *            The path to the .csv file that contains the matrix and the costs table
     * @return costsTable a map that maps the code of the description matrix to its costs
     * @throws IOException
     *             if the file cannot be opened
     */
    public Map<Integer, Double> readCostsTable(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Map<Integer, Double> costsTable = new HashMap<>();

        // Step over the matrix
        String line;
        while ((line = reader.readLine()) != null && line.charAt(0) != ';') {
            // do nothing
        }

        // Ignore the header of the costs table
        reader.readLine();

        // Read necessary information of the costs table
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(";");
            costsTable.put(Integer.parseInt(row[0]), Double.parseDouble(row[2]));
        }

        reader.close();
        return costsTable;
    }

}
