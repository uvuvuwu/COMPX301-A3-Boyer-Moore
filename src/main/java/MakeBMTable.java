// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/*
 * MakeBMTable creates a skip table on a given string
 * Inputs are
 * 1. String to search
 * 2. Name of the file to store the skip table to
 */
public class MakeBMTable {
    public static void main(String [] args) throws IOException {
        // If there isn't 2 arguments, tell user to put in 2 arguments or else >:(
        if(args.length != 2){
            System.out.println(">:( write: java MakeBMTable stringToSearch skipTableFileName");
            System.exit(0);
        }

        // Store arguments given by user
        String inputString = args[0];
        String outputFilename = args[1];

        // Create a new BMTable object
        BMTable table = new BMTable(inputString);
        // Populate the BMTable object with the skip amounts
        table.fill();

        // Output the table to a file, file name specified by the user
        outputTable(table, outputFilename);
    }


    /*
     * outputTable writes the generated skipTable out to a file
     */
    public static void outputTable(BMTable table, String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Generate first row of skip table 
        String tempRow = "*";
        for (int i = 0; i < table.getNumColumns(); i++){
            tempRow += "," + table.getSearchString().charAt(i);
        }
        writer.write(tempRow);
        writer.newLine();

        // Generate rest of rows of skip table
        char[] tableChars = table.getRowCharacters();
        BMTableRow tableRow;
        for (int i = 0; i < tableChars.length; i++) {
            tempRow = "" + tableChars[i];
            if (i == tableChars.length - 1) {
                // If it's the last row, set the row to the default row
                tableRow = table.getDefaultRow();
            } else {
                // Otherwise, get the row from the table
                tableRow = table.getRow(tableChars[i]);
            }
            for (int j = 0; j < table.getNumColumns(); j++){
                tempRow += "," + tableRow.getSkipAmount(j);
            }
            writer.write(tempRow);
            if (i != tableChars.length - 1) {
                // Write a newline if it's not the last row
                writer.newLine();
            }
        }
        // Close the writer
        writer.close();
    }
}
