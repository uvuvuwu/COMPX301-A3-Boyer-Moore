// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/*
 * MakeBMTable creates a Boyer-Moore table from a search string
 */
public class MakeBMTable {

    /*
     * Main method for MakeBMTable
     * Parses the command line arguments, creates and fills a 
     * BMTable object, and outputs the table to a file
     * @param args: Command line arguments (2)
     * args[0]: The search string
     * args[1]: The name of the file to output the BMTable to
     */
    public static void main(String [] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java MakeBMTable <searchString> <outputTable.txt>");
            System.exit(1);
        }
        if(args[0].equals("")){
            System.out.println("Cannot search for an empty pattern");
            // Make sure the output file is empty
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
            writer.close();
            System.exit(1);
        }
        // Create a new BMTable object
        BMTable table = new BMTable(args[0]);
        // Populate the BMTable object with skip amounts
        table.fill();
        // Output the table to a file, file name specified by the user
        outputTable(table, args[1]);
    }


    /*
     * Write the populated BMTable out to a file
     * @param table: The BMTable object to output
     * @param filename: The name of the file to output the BMTable to
     */
    public static void outputTable(BMTable table, String filename) throws IOException {
        // Create a new file and BufferedWriter to write to
        File file = new File(filename);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Write the first row of the table (search string)
        writer.write('*');
        for (int i = 0; i < table.getNumColumns(); i++) {
            writer.write(",");
            writer.write(table.getSearchString().charAt(i));
        }
        writer.newLine();

        // Write the rest of rows of the BMTable
        char[] tableChars = table.getRowCharacters();
        BMTableRow tableRow;
        for (int i = 0; i < tableChars.length; i++) {
            // Write the character for the row
            writer.write(tableChars[i]);
            if (i == tableChars.length - 1) {
                // If it's the last row, set the row to the default row
                tableRow = table.getDefaultRow();
            }
            else {
                // Otherwise, get the row from the table
                tableRow = table.getRow(tableChars[i]);
            }
            // Write the skip amounts for each row
            for (int j = 0; j < table.getNumColumns(); j++){
                writer.write(',');
                writer.write(Integer.toString(tableRow.getSkipAmount(j)));
            }
            if (i != tableChars.length - 1) {
                // Write a newline if it's not the last row
                writer.newLine();
            }
        }
        // Close the writer
        writer.close();
    }
}
