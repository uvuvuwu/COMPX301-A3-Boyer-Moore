import java.util.ArrayList;

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
            SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), uniqueLetters.get(i));
            tableRows.add(skipTableRow);
        }
        // Create row for '*' (Any other letter)
        SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), '*');
        tableRows.add(skipTableRow);
    }

    public ArrayList<SkipTableRow> getSkipTable(){
        return this.tableRows;
    }

    public int getNumRowsInTable(){
        return this.numRowsInTable;
    }

    /*
     * fillTable fills the skip table
     */
    public void fillTable(){
        // TODO complete fillTable

        int maxNumToSkip = stringToSearch.length();

        // Go down a column, starting from the last column, looping to the first column. 
        // To go down a column, look at the same index of all the arrays.
        for(int i = stringToSearch.length() - 1; i >= 0; i--){
            fillColumn(i, maxNumToSkip);
            // After going down a column
            // Calculate max number to skip
            maxNumToSkip = calcMaxNumToSkip();
        }
        
    }

    /*
     * fillColumn fills column 'column' in the skip table 
     */
    public void fillColumn(int column, int maxNumToSkip){
        // TODO complete fillColumn

        // When going down a column, check:
        // if current string(character) match what we’re looking for, input 0
        // if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
        // if current substring has no earlier instances of same substring, skip by max number to skip

        String currSubString = "";

        for(int i = 0; i < tableRows.size(); i++){
            SkipTableRow tableRow = tableRows.get(i);
            // if current string(character) match what we’re looking for, input 0
            if(tableRow.getRowCharacter() == stringToSearch.charAt(column)){
                int[] row = tableRow.getRow();
                row[column] = 0;
                tableRow.setRow(row);
            }

            // if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
            char currentLetter = tableRow.getRowCharacter();
            currSubString = updateCurrentSubString(currentLetter, column);

            // Next TODO moveBackDistance
            int moveBackDistance = moveBackDistance(currSubString);
        }
    }

    public int calcMaxNumToSkip(){
        // TODO complete function

        int maxNumToSkip = 0;
        return maxNumToSkip;
    }

    public int moveBackDistance(String currSubString){
        // TODO complete function

        int moveBackDistance = 0;
        return moveBackDistance;
    }

    /*
     * updateCurrentSubString returns the current substring,
     * which is the current character + the substring from column (exclusive) to end of stringToSearch
     */
    public String updateCurrentSubString(char currentLetter, int column){
        // Add current letter to rest of the end of string
        // E.g. if string to search is 'kokako', if current letter is 'a', column 3, end of string is 'ko'
        String endOfString = stringToSearch.substring(column+1);
        String currChar = Character.toString(currentLetter);
        String currentSubString = currChar + endOfString;
        return currentSubString;
    }
}
