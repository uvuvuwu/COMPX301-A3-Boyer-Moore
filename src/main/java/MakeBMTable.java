// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.util.ArrayList;
import java.util.HashMap;
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
    public static void main(String [] args) {
        // If there isn't 2 arguments, tell user to put in 2 arguments or else >:(
        if(args.length != 2){
            System.out.println(">:( write: java MakeBMTable stringToSearch skipTableFileName");
            System.exit(0);
        }

        // Store arguments given by user
        String stringToSearch = args[0];
        String skipTableFileName = args[1];

        // Build empty table
        ArrayList<Character> uniqueLetters = findAllUniqueLetters(stringToSearch);
        SkipTable skipTable = new SkipTable(stringToSearch, uniqueLetters);
        skipTable.buildEmptyTable();

        // Fill the empty table
        skipTable.fillTable();

        // Output the table to a file, file name specified by the user
        outputTable(skipTable, skipTableFileName);
    }

    /*
     * findAllUniqueLetters finds and returns a list of all the unique letters in a passed in string
     */
    public static ArrayList<Character> findAllUniqueLetters(String stringToSearch){
        ArrayList<Character> uniqueLettersList = new ArrayList<>();
        for (int i = 0; i < stringToSearch.length(); i++) {
            char c = stringToSearch.charAt(i);
            if (!uniqueLettersList.contains(c)) {
                uniqueLettersList.add(c);
            }
        }
        return uniqueLettersList;
    }

    /*
     * outputTable writes the generated skipTable out to a file
     */
    public static void outputTable(SkipTable skipTable, String skipTableFileName){
        // Stores the strings to output to skip table
        ArrayList<String> rows = new ArrayList<>();
        String tempRow = "";

        // Generate first row of skip table 
        tempRow = "*";
        for(int i = 0; i < skipTable.getNumColumns(); i++){
            tempRow = tempRow + "," + skipTable.getStringToSearch().charAt(i);
        }
        rows.add(tempRow);
        
        // Generate rest of rows of skip table
        int tableNumRows = skipTable.getNumRowsInTable();
        for(int i = 0; i < tableNumRows; i++){
            tempRow = Character.toString(skipTable.getSkipTable().get(i).getRowCharacter());
            for(int j = 0; j < skipTable.getNumColumns(); j++){
                tempRow = tempRow + "," + skipTable.getSkipTable().get(i).getRow()[j];
            }
            rows.add(tempRow);
        }

        // Generate a file and write the rows to the file
        try {
            File file = new File(skipTableFileName);
            file.createNewFile();
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(skipTableFileName));

            for(int i = 0; i < rows.size(); i++){
                myWriter.write(rows.get(i));
                // Go to new line except on the last row to write
                if(i != rows.size() - 1){
                    myWriter.newLine();
                }
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
