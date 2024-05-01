import java.util.HashMap;
import java.util.Arrays;

/*
* BMTable.java
* Holds the elements of the Boyer-Moore table
*/
public class BMTable {

    private HashMap<Character, BMTableRow> table;
    private BMTableRow defaultSkipRow;
    private String searchString;
    private char[] rowCharacters;

    /*
     * Create a new BMTable object
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
     * populates the skip table with skip numbers
     */
    public void fill() {
        // initialise max number to skip as the size of the search string
        int maxNumToSkip = this.getNumColumns();
        // Starting from the last column, fill each row in the column
        // with calculated skip numbers
        // After filling a column, recalculate max number to skip
        for(int i = this.getNumColumns() - 1; i >= 0; i--){
            fillColumn(i, maxNumToSkip);
            // After going down a column, recalculate max number to skip
            maxNumToSkip = calcMaxNumToSkip(i, maxNumToSkip);
        }
    }


    /*
     * fillColumn fills a specified column in the skip table 
     */
    public void fillColumn(int column, int maxNumToSkip){
        String currSubString;
        BMTableRow row;
        int moveBackDistance;
        // Loop through all the rows, fill the rows at position 'column' with their skip numbers
        for (int i = 0; i < this.rowCharacters.length; i++) {
            if (i == this.rowCharacters.length - 1) {
                // default row
                // set the skip amount as the max number to skip
                this.defaultSkipRow.setSkipAmount(column, maxNumToSkip);
            }
            else {
                // Get the BMTableRow for the current letter
                row = this.getRow(this.rowCharacters[i]);
                // Update current substring
                currSubString = updateCurrentSubString(this.rowCharacters[i], column);
                // get the move back distance for the current substring
                moveBackDistance = moveBackDistance(currSubString);
                // If current substring has no earlier instances of same substring, skip by max number to skip
                if (moveBackDistance == -1) {
                    moveBackDistance = maxNumToSkip;
                }
                // set the skip amount for that column in the row
                row.setSkipAmount(column, moveBackDistance);
            }
        }
    }

    /*
     * calcMaxNumToSkip calculates and returns the maximum skip number for a column
     */
    public int calcMaxNumToSkip(int column, int maxNumToSkip){
        // If the current column to end of stringToSearch match the start of stringToSearch, update max number to skip
        // If it doesn't match, return original maxNumToSkip
        String end = this.searchString.substring(column);
        String start = this.searchString.substring(0, end.length());
        if(end.equals(start)){
            maxNumToSkip = this.searchString.length() - end.length();
            return maxNumToSkip;
        }
        
        return maxNumToSkip;
    }

    /*
     * moveBackDistance calculates and returns the distance a substring needs to move back to find a previous occurrence of itself in stringToSearch
     */
    public int moveBackDistance(String currSubString){
        // Take currsubstring, comparing against the substring at the index before current. 
        // E.g. if current substring is at index 3, compare current substring with substring at index 2. 
        // Keep comparing against index -= 1 until there is a substring match. However many spaces currsubstring went back by is the move back distance.
        // If after entire loop there was no match, return moveBackDistance of -1

        // set the initial column
        int initialColumn = this.searchString.length() - currSubString.length();
        int currentColumn = initialColumn;
        // Loop through all the columns before current to compare substrings
        while (currentColumn >= 0) {
            // Check if currsubstring equals substring at i
            String stringToCheck = this.searchString.substring(currentColumn, currentColumn + currSubString.length());
            if(currSubString.equals(stringToCheck)) {
                return initialColumn - currentColumn;
            }
            // Move to the previous column
            currentColumn--;
        }
        // If no match found, return -1
        return -1;
    }

    /*
     * updateCurrentSubString returns the current substring,
     * which is the current character + the substring from column (exclusive) to end of stringToSearch (inclusive)
     */
    public String updateCurrentSubString(char currentLetter, int column){
        // Add current letter to rest of the end of string
        // E.g. if string to search is 'kokako', if current letter is 'a', column 3, end of string is 'ko'
        String endOfString = this.searchString.substring(column+1);
        String currChar = Character.toString(currentLetter);
        String currentSubString = currChar + endOfString;
        return currentSubString;
    }
}
