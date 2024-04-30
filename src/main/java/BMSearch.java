import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;


public class BMSearch {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java BMSearch <BMTable.txt> <SearchFile.txt>");
            System.exit(1);
        }
        String BMTableName = args[0];
        String SearchFileName = args[1];

        BufferedReader BMTableReader = new BufferedReader(new FileReader(BMTableName));
        String searchString = parseSearchString(BMTableReader.readLine());
        BMTable table = new BMTable(searchString);
        // now read the rest of the file to populate the table
        String line;
        // read each line of the BMTable file
        while ((line = BMTableReader.readLine()) != null) {
            // get the key (first character of the line)
            char key = line.charAt(0);
            // get the skip amounts from the line
            int[] skipAmounts = parseSkipAmounts(line);
            // set the skip amounts in the table
            table.setSkipAmounts(key, skipAmounts);
        }
        BMTableReader.close();

        // TODO: read the search file and search for the search string
        // ...
    }


    /*
     * Get the search string from the first line of the BMTable file
     * This is done by joining the first character of each comma-separated value
     * (skipping the first value)
     * @param line The first line of the BMTable file
     * @return The search string
     */
    public static String parseSearchString(String line) {
        // Split the line by commas
        String[] splitString = line.split(",");
        // Create a StringBuilder to hold the joined string
        StringBuilder joinedString = new StringBuilder();
        // For each element in the split array, append the first character to the StringBuilder
        // (skipping the first element)
        for (int i = 1; i < splitString.length; i++) {
            joinedString.append(splitString[i].charAt(0));
        }
        // Return the joined string
        return joinedString.toString();
    }


    /*
     * Get the skip amounts from a line of the BMTable file
     * This is done by parsing each comma-separated value as an integer
     * (skipping the first value)
     * @param BMTableLine A line from the BMTable file
     * @return An array of integers representing the skip amounts
     */
    public static int[] parseSkipAmounts(String BMTableLine) {
        // Split the line by commas
        String[] splitString = BMTableLine.split(",");
        // Create a char array to hold the values
        int[] skipAmounts = new int[splitString.length - 1];
        // For each element in the split array, parse it as an integer and add it to the char array
        for (int i = 1; i < splitString.length; i++) {
            skipAmounts[i] = Integer.parseInt(splitString[i]);
        }
        // Return the char array
        return skipAmounts;
    }
}
