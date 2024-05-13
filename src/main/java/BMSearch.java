// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;


/*
 * BMSearch.java searches for a string in a file using the Boyer-Moore skip table.
 * It parses a BMTable file to create a BMTable object, then searches a file for the search string
 * using the BMTable object. It outputs the lines that contain the search string.
 */
public class BMSearch {

    /*
     * Main method for BMSearch
     * Parses the command line arguments, creates a BMTable object, and searches a file
     * Outputs the lines that contain the search string
     * @param args: Command line arguments (2)
     * args[0]: The name of the BMTable file
     * args[1]: The name of the file to search
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java BMSearch <BMTable.txt> <SearchFile.txt>");
            System.exit(1);
        }
        BMTable table = createBMTable(args[0]);
        search(table, args[1]);
    }


    /*
     * Create a BMTable object from a BMTable file
     * @param BMTableFilename: The name of the BMTable file
     * @return: A BMTable object
     */
    public static BMTable createBMTable(String BMTableFilename) throws IOException {
        // create a new BufferedReader to read from the BMTable file
        BufferedReader reader = new BufferedReader(new FileReader(BMTableFilename));
        // get the search string from the first line of the BMTable file
        String searchString = parseSearchString(reader.readLine());
        // create a new BMTable object with the search string
        BMTable table = new BMTable(searchString);
        // now read the rest of the file to populate the table
        // we have two line variables so we can process the final line separately
        String nextLine = reader.readLine();
        String line = nextLine;
        // read each line of the BMTable file
        while ((nextLine = reader.readLine()) != null) {
            // get the key (first character of the line)
            char key = line.charAt(0);
            // get the skip amounts from the line
            int[] skipAmounts = parseSkipAmounts(line);
            // set the skip amounts in the table
            table.setSkipAmounts(key, skipAmounts);
            // set the line to the next line
            line = nextLine;
        }
        // final line, set the default row's skip amounts
        int[] skipAmounts = parseSkipAmounts(line);
        table.getDefaultRow().setSkipAmounts(skipAmounts);
        // close the reader
        reader.close();
        // return the BMTable object
        return table;
    }


    /*
     * Get the search string from the first line of the BMTable file
     * This is done by joining the first character of each comma-separated value
     * (skipping the first value)
     * @param line: The first line of the BMTable file
     * @return: The search string
     */
    public static String parseSearchString(String line) {
        if(line == null){
            System.out.println("Search failed, the skip table is empty");
            System.exit(1);
        }
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
     * @param BMTableLine: A line from the BMTable file
     * @return: An array of integers representing the skip amounts
     */
    public static int[] parseSkipAmounts(String BMTableLine) {
        // Split the line by commas
        String[] splitString = BMTableLine.split(",");
        // Create a char array to hold the values
        int[] skipAmounts = new int[splitString.length - 1];
        // For each element in the split array, parse it as an integer and add it to the char array
        for (int i = 1; i < splitString.length; i++) {
            skipAmounts[i - 1] = Integer.parseInt(splitString[i]);
        }
        // Return the char array
        return skipAmounts;
    }


    /*
     * Search for a string in a file using the Boyer-Moore algorithm
     * Outputs the lines that contain the search string
     * @param table: The BMTable object
     * @param filename: The name of the file to search
     */
    public static void search(BMTable table, String filename) throws IOException {
        // create a new BufferedReader to read from the file
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        // create a new BufferedWriter to write to stdout
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        // get the search string from the BMTable object
        String searchString = table.getSearchString();
        // get the length of the search string
        int searchStringLength = searchString.length();
        // index through the line
        int index;
        // index back (amount of characters matched so far)
        int matchCount;
        // amount to skip
        int skipAmount;
        // current line
        String line;
        // read each line of the file
        while ((line = reader.readLine()) != null) {
            // get the length of the line
            int lineLength = line.length();
            // reset the index
            index = searchStringLength - 1;
            // while we are within the line
            while (index < lineLength) {
                // reset the match count and skip amount
                matchCount = 0;
                skipAmount = 0;
                // loop until we have to skip, or we've matched the search string
                while (skipAmount == 0 && matchCount < searchStringLength) {
                    // get the character at the index
                    char c = line.charAt(index - matchCount);
                    // lookup that character in the BMTable and get the skip amount
                    skipAmount = table.getSkipAmount(c, searchStringLength - 1 - matchCount);
                    // increment the match count
                    matchCount++;
                }
                // if we don't have to skip, we have a full match
                if (skipAmount == 0) {
                    // write the line to stdout
                    writer.write(line);
                    writer.newLine();
                    // stop searching this line
                    break;
                }
                // increment the index by the skip amount
                index += skipAmount;
            }
        }
        // close the reader
        reader.close();
        // close the writer
        writer.close();
    }
}
