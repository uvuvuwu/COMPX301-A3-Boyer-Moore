// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.util.HashMap;
import java.util.Arrays;

/*
 * BMTable.java
 * Holds the elements of the Boyer-Moore table,
 * with methods to get and set skip amounts for each row and column,
 * and an algorithm to fill the table with skip numbers based on the search string.
 */
public class BMTable {

    private HashMap<Character, BMTableRow> table;  // HashMap mapping characters to table rows
    private BMTableRow defaultSkipRow;  // "default" row for characters not in the search string
    private String searchString;  // the search string
    private char[] rowCharacters;  // (ordered) array of characters representing the rows in the table

    /*
     * Create a new BMTable object
     * @param searchString: The search string
     */
    public BMTable(String searchString) {
        this.table = new HashMap<>();
        this.searchString = searchString;
        // initialize the table with rows for each unique character in the search string
        int length = searchString.length();
        for (int i = 0; i < searchString.length(); i++) {
            char c = searchString.charAt(i);
            if (!this.table.containsKey(c)) {
                this.table.put(c, new BMTableRow(length));
            }
        }
        // create a default row for characters not in the search string
        this.defaultSkipRow = new BMTableRow(length);
        // set the characters for the rows in the table
        this.setSortedRowCharacters();
    }


    /*
     * Get the skip amount for a given row and column
     * @param key: The character key for the row
     * @param index: The column index
     * @return: The skip amount
     */
    public int getSkipAmount(char key, int index) {
        return this.table.getOrDefault(key, this.defaultSkipRow).getSkipAmount(index);
    }


    /*
     * Set the skip amount for a given row and column
     * @param key: The character key for the row
     * @param index: The column index
     * @param value: The skip amount
     */
    public void setSkipAmount(char key, int index, int value) {
        this.table.getOrDefault(key, this.defaultSkipRow).setSkipAmount(index, value);
    }


    /*
     * Get all the skip amounts for a given row
     * @param key: The character key for the row
     * @return: An array of integers representing the skip amount for each column
     */
    public int[] getSkipAmounts(char key) {
        return this.table.getOrDefault(key, this.defaultSkipRow).getSkipAmounts();
    }


    /*
     * Set all the skip amounts for a given row
     * @param key: The character key for the row
     * @param skipAmounts: An array of integers representing the skip amount for each column
     */
    public void setSkipAmounts(char key, int[] skipAmounts) {
        this.table.getOrDefault(key, this.defaultSkipRow).setSkipAmounts(skipAmounts);
    }


    /*
     * Get the row for a given character
     * @param key: The character key for the row
     * @return: The BMTableRow object for the given character
     */
    public BMTableRow getRow(char key) {
        return this.table.getOrDefault(key, this.defaultSkipRow);
    }


    /* 
     * Get the default row for characters not in the search string
     * @return: The default row
     */
    public BMTableRow getDefaultRow() {
        return this.defaultSkipRow;
    }


    /*
     * Get the search string
     * @return: The search string
     */
    public String getSearchString() {
        return this.searchString;
    }


    /*
     * Get the number of columns in the table
     * @return: The number of columns
     */
    public int getNumColumns(){
        return this.searchString.length();
    }


    /*
     * Set the characters for the rows in the table
     * Also includes the default row
     * Sorted for consistent order
     */
    public void setSortedRowCharacters() {
        this.rowCharacters = new char[this.table.size() + 1];
        int i = 0;
        // add all keys to the row characters array
        for (char c : this.table.keySet()) {
            this.rowCharacters[i] = c;
            i++;
        }
        // add the default row character
        this.rowCharacters[i] = '*';
        // sort the characters (excluding the default row)
        Arrays.sort(this.rowCharacters, 0, this.rowCharacters.length - 1);
    }

    /*
    * Get the characters for the rows in the table
    * Also includes the default row
    * @return: An array of characters representing the rows in the table
    */
    public char[] getRowCharacters() {
        return this.rowCharacters;
    }


    /*
     * Populates the skip table with skip numbers
     */
    public void fill() {
        // initialise max number to skip as the size of the search string
        int maxSkip = this.getNumColumns();
        // Starting from the last column, fill each row in the column
        // with calculated skip numbers
        // After filling a column, recalculate max number to skip
        for(int i = this.getNumColumns() - 1; i >= 0; i--){
            fillColumn(i, maxSkip);
            // After going down a column, recalculate max number to skip
            maxSkip = calcmaxSkip(i, maxSkip);
        }
    }


    /*
     * Fills a specified column in the skip table
     * @param column: The column to fill
     * @param maxSkip: The maximum number of spaces to skip
     */
    public void fillColumn(int column, int maxSkip){
        String substring;
        BMTableRow row;
        int nearestMatch;
        // Loop through all the rows, fill the rows at position 'column' with their skip numbers
        for (int i = 0; i < this.rowCharacters.length; i++) {
            if (i == this.rowCharacters.length - 1) {
                // default row
                // set the skip amount as the max number to skip
                this.defaultSkipRow.setSkipAmount(column, maxSkip);
            }
            else {
                // Get the BMTableRow for the current character
                row = this.getRow(this.rowCharacters[i]);
                // Get the substring for the current row and column
                substring = this.getSubstring(this.rowCharacters[i], column);
                // Get the nearest match of the substring
                nearestMatch = this.nearestMatch(substring);
                // If substring has no earlier instances of same substring, skip by max number to skip
                if (nearestMatch == -1) {
                    nearestMatch = maxSkip;
                }
                // Set the skip amount for that column in the row
                row.setSkipAmount(column, nearestMatch);
            }
        }
    }

    /*
     * Calculates and returns the maximum skip number for a column,
     * or return the original maxSkip if the substring at the column
     * doesn't match the start of the search string
     * @param column: The column to calculate the max number to skip for
     * @param maxSkip: The current maximum number to skip
     * @return: The updated maximum number to skip
     */
    public int calcmaxSkip(int column, int maxSkip) {
        // get the substring from the column to the end of the search string
        String end = this.searchString.substring(column);
        // get the substring from the start of the search string to the size of the end substring
        String start = this.searchString.substring(0, end.length());
        // check if start and end are equal
        if (end.equals(start)){
            // the max number to skip is the length of the search string minus the length of the end substring
            return this.searchString.length() - end.length();
        }
        // otherwise, return the original maxSkip
        return maxSkip;
    }

    /*
     * Find the nearest match of a substring in the stringToSearch
     * starting from the end of the stringToSearch, or return
     * -1 if no match is found
     * @param substring: substring to check
     * @return: number of spaces to move back to find the nearest match
     */
    public int nearestMatch(String substring){
        // Take substring, comparing against the search string from the end of the search string
        // Keep comparing against index -= 1 until there is a substring match. However many times
        // we had to iterate to find a match is the number of spaces we need to move back.
        // If no match is found, return -1

        // set the initial column index to start comparing from
        int initialColumn = this.searchString.length() - substring.length();
        int currentColumn = initialColumn;
        String stringToCheck;
        // Loop through all the columns to compare substrings
        while (currentColumn >= 0) {
            // Check if the substring we're looking for matches the substring in the search string
            stringToCheck = this.searchString.substring(currentColumn, currentColumn + substring.length());
            if(substring.equals(stringToCheck)) {
                return initialColumn - currentColumn;
            }
            // Move to the previous column
            currentColumn--;
        }
        // If no match found, return -1
        return -1;
    }

    /*
     * Get a substring by joining a character with the rest of the string
     * E.g. if stringToSearch is 'kokako', getSubstring('b', 3) returns 'bko'
     * @param c: The char to add to the substring
     * @param column: The column to start the substring from
     * @return: The substring
     */
    public String getSubstring(char c, int column) {
        // join the character with the rest of the search string at the column index
        return c + this.searchString.substring(column + 1);
    }
}
