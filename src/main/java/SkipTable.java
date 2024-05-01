// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.util.ArrayList;

/*
 * SkipTable class is a SkipTable object to be used in MakeBMTable
 */
public class SkipTable {
    private String stringToSearch;
    private ArrayList<Character> uniqueLetters;
    private ArrayList<SkipTableRow> tableRows;
    private int numRowsInTable;

    /*
     * Initialises SkipTable object
     * Note numRowsInTable is not correct, this is set in buildEmptyTable
     */
    public SkipTable(String stringToSearch, ArrayList<Character> uniqueLetters){
        this.stringToSearch = stringToSearch;
        this.uniqueLetters = uniqueLetters;
        this.tableRows = new ArrayList<>();
        numRowsInTable = 0;
    }

    /*
     * buildEmptyTable builds an empty skip table
     */
    public void buildEmptyTable(){
        // Find the number of rows in the table
        numRowsInTable = uniqueLetters.size() + 1; 
        // Create the rows for each unique letter
        for(int i = 0; i < numRowsInTable - 1; i++){
            SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), uniqueLetters.get(i), false);
            tableRows.add(skipTableRow);
        }
        // Create row for '*' (Any other letter)
        SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), '*', true);
        tableRows.add(skipTableRow);
    }

    /*
     * getSkipTable returns the ArrayList of SkipTableRow objects
     */
    public ArrayList<SkipTableRow> getSkipTable(){
        return this.tableRows;
    }


    /*
     * getNumRowsInTable returns the number of rows in the table
     */
    public int getNumRowsInTable(){
        return this.numRowsInTable;
    }

    /*
     * fillTable fills an empty skip table
     */
    public void fillTable(){
        int maxNumToSkip = stringToSearch.length();

        // Go down a column, starting from the last column, looping to the first column. Fill each of these columns
        for(int i = stringToSearch.length() - 1; i >= 0; i--){
            fillColumn(i, maxNumToSkip);
            // After going down a column, recalculate max number to skip
            maxNumToSkip = calcMaxNumToSkip(i, maxNumToSkip);
        }
    }

    /*
     * fillColumn fills a specified column in the skip table 
     */
    public void fillColumn(int column, int maxNumToSkip){
        String currSubString = "";
        boolean hasInput = false;

        // Loop through all the rows, fill the rows at position 'column' with their skip numbers
        for(int i = 0; i < tableRows.size(); i++){
            SkipTableRow tableRow = tableRows.get(i);
            // If the row is '*', any letter not in the string to search for, set skip number to max number to skip
            if(tableRow.getOther() == true){
                int[] row = tableRow.getRow();
                row[column] = maxNumToSkip;
                tableRow.setRow(row);
                hasInput = true;
            }
            else{
                // If current character match what we’re looking for, skip by 0
                if(tableRow.getRowCharacter() == stringToSearch.charAt(column)){
                    int[] row = tableRow.getRow();
                    row[column] = 0;
                    tableRow.setRow(row);
                    hasInput = true;
                }

                char currentLetter = tableRow.getRowCharacter();
                currSubString = updateCurrentSubString(currentLetter, column);

                int moveBackDistance = moveBackDistance(currSubString);
                // If current substring has no earlier instances of same substring, skip by max number to skip
                // If hasInput == true, there is already a number there, don't overwrite it
                if(moveBackDistance == -1 && hasInput == false){
                    int[] row = tableRow.getRow();
                    row[column] = maxNumToSkip;
                    tableRow.setRow(row);
                    hasInput = true;
                }
                // Else if current substring (from current character to end) has an earlier instance of same substring, skip by current distance to the earlier instance distance
                // If hasInput == true, there is already a number there, don't overwrite it
                else if(moveBackDistance > -1 && hasInput == false) {
                    int[] row = tableRow.getRow();
                    row[column] = moveBackDistance;
                    tableRow.setRow(row);
                    hasInput = true;
                }
                // Set hasInput to false for next row
                hasInput = false;
            }
        }
    }

    /*
     * calcMaxNumToSkip calculates and returns the maximum skip number for a column
     */
    public int calcMaxNumToSkip(int column, int maxNumToSkip){
        // If the current column to end of stringToSearch match the start of stringToSearch, update max number to skip
        // If it doesn't match, return original maxNumToSkip
        String end = stringToSearch.substring(column);
        String start = stringToSearch.substring(0, 0 + end.length());
        if(end.equals(start)){
            maxNumToSkip = stringToSearch.length() - end.length();
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

        int moveBackDistance = 0;

        // Calculate the number of columns to look through
        int timesToLoopBy = stringToSearch.length() - currSubString.length();
        // Loop through all the columns before current to compare substrings
        for(int i = timesToLoopBy - 1; i >= 0; i--){
            moveBackDistance++;
            // Check if currsubstring equals substring at i
            String stringToCheck = stringToSearch.substring(i, i + currSubString.length());
            if(currSubString.equals(stringToCheck)){
                return moveBackDistance;
            }
        }
        
        return -1;
    }

    /*
     * updateCurrentSubString returns the current substring,
     * which is the current character + the substring from column (exclusive) to end of stringToSearch (inclusive)
     */
    public String updateCurrentSubString(char currentLetter, int column){
        // Add current letter to rest of the end of string
        // E.g. if string to search is 'kokako', if current letter is 'a', column 3, end of string is 'ko'
        String endOfString = stringToSearch.substring(column+1);
        String currChar = Character.toString(currentLetter);
        String currentSubString = currChar + endOfString;
        return currentSubString;
    }

    /*
     * getNumColums returns the number of columns there are in the skip table
     */
    public int getNumColumns(){
        return this.stringToSearch.length();
    }

    /*
     * getStringToSearch returns the string to search in the skip table
     */
    public String getStringToSearch(){
        return this.stringToSearch;
    }
}
