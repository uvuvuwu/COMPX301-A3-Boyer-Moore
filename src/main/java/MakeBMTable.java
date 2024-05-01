import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays; 

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
            System.out.println("Write: java MakeBMTable stringToSearch skipTableFileName >:(");
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


        // Fill table
        skipTable.fillTable();
        // Go down a column, starting from the last column, looping to the first column. 
        // To go down a column, look at the same index of all the arrays.

        // When going down a column, check:
        // if current string(character) match what we’re looking for, input 0
        // if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
        // if current substring has no earlier instances of same substring, skip by max number to skip

        // After going down a column
        // Calculate max number to skip

        // DELETE LATER. Testing table
        for(int i = 0; i < skipTable.getNumRowsInTable(); i++){
            skipTable.getSkipTable().get(i).printRow();
        }


        // Output table
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
}
