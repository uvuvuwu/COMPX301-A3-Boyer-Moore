import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays; 
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

        String stringToSearch = args[0];
        String skipTableFileName = args[1];

        // Build empty table
        // Find all the unique letters in the string
        // Find the number of unique letters in the string (numUniqueLetters)
        // Create numUniqueLetters + 1 number of arrays, array length initial string
        ArrayList<Character> uniqueLetters = findAllUniqueLetters(stringToSearch);
        // Create empty skip table
        SkipTable skipTable = new SkipTable(stringToSearch, uniqueLetters);
        skipTable.buildEmptyTable();


        // Fill the empty table
        skipTable.fillTable();


        // DELETE LATER. Testing table
        // for(int i = 0; i < skipTable.getNumRowsInTable(); i++){
        //     skipTable.getSkipTable().get(i).printRow();
        // }


        // TODO Output table
        outputTable(skipTable, skipTableFileName);
    }

    /*
     * findAllUniqueLetters finds and returns a list of all the unique letters in a passed in string
     */
    public static ArrayList<Character> findAllUniqueLetters(String stringToSearch){
        // Go through stringToSearch, put each into hash map
        HashMap<Character, Character> hashMap = new HashMap<>();
        for(int i = 0; i < stringToSearch.length(); i++){
            Character c = stringToSearch.charAt(i);
            hashMap.put(c, c);
        }
        // Turn hashmap into list, return the list
        ArrayList<Character> uniqueLettersList = new ArrayList<Character>(hashMap.values());
        return uniqueLettersList;
    }

    public static void outputTable(SkipTable skipTable, String skipTableFileName){
        ArrayList<String> rows = new ArrayList<>();
        String tempRow = "";

        // Generate first row of skip table 
        tempRow = "*";
        for(int i = 0; i < skipTable.getNumColums(); i++){
            tempRow = tempRow + "," + skipTable.getStringToSearch().charAt(i);
        }
        rows.add(tempRow);
        
        // Generate rest of rows of skip table
        int tableNumRows = skipTable.getNumRowsInTable();
        for(int i = 0; i < tableNumRows; i++){
            tempRow = Character.toString(skipTable.getSkipTable().get(i).getRowCharacter());
            for(int j = 0; j < skipTable.getNumColums(); j++){
                tempRow = tempRow + "," + skipTable.getSkipTable().get(i).getRow()[j];
            }
            rows.add(tempRow);
        }

        // for(int i = 0; i < rows.size(); i++){
        //     System.out.println(rows.get(i));
        // }


        // Generate a file
        // print rows to the file
        try {
            File file = new File(skipTableFileName);
            file.createNewFile();
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(skipTableFileName));

            for(int i = 0; i < rows.size(); i++){
                myWriter.write(rows.get(i));
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
