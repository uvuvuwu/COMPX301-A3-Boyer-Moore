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
            SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), uniqueLetters.get(i), false);
            tableRows.add(skipTableRow);
        }
        // Create row for '*' (Any other letter)
        SkipTableRow skipTableRow = new SkipTableRow(stringToSearch.length(), '*', true);
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
        int maxNumToSkip = stringToSearch.length();

        // Go down a column, starting from the last column, looping to the first column. 
        // To go down a column, look at the same index of all the arrays.
        for(int i = stringToSearch.length() - 1; i >= 0; i--){
            fillColumn(i, maxNumToSkip);
            // After going down a column
            // Calculate max number to skip
            maxNumToSkip = calcMaxNumToSkip(i, maxNumToSkip);
        }
        
    }

    /*
     * fillColumn fills column 'column' in the skip table 
     */
    public void fillColumn(int column, int maxNumToSkip){
        // When going down a column, check:
        // if current string(character) match what we’re looking for, input 0
        // if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
        // if current substring has no earlier instances of same substring, skip by max number to skip

        String currSubString = "";
        boolean hasInput = false;

        for(int i = 0; i < tableRows.size(); i++){
            SkipTableRow tableRow = tableRows.get(i);
            if(tableRow.getOther() == true){
                int[] row = tableRow.getRow();
                row[column] = maxNumToSkip;
                tableRow.setRow(row);
                hasInput = true;
            }
            else{
                // if current string(character) match what we’re looking for, input 0
                if(tableRow.getRowCharacter() == stringToSearch.charAt(column)){
                    int[] row = tableRow.getRow();
                    row[column] = 0;
                    tableRow.setRow(row);
                    hasInput = true;
                }

                // if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
                char currentLetter = tableRow.getRowCharacter();
                currSubString = updateCurrentSubString(currentLetter, column);

                int moveBackDistance = moveBackDistance(currSubString);
                // if current substring has no earlier instances of same substring, skip by max number to skip
                if(moveBackDistance == -1 && hasInput == false){
                    int[] row = tableRow.getRow();
                    row[column] = maxNumToSkip;
                    tableRow.setRow(row);
                    hasInput = true;
                }
                // else if current substring (from curr character to end) has an earlier instance of same substring, input move back by that much
                else if(moveBackDistance > -1 && hasInput == false) {
                    int[] row = tableRow.getRow();
                    row[column] = moveBackDistance;
                    tableRow.setRow(row);
                    hasInput = true;
                }
                hasInput = false;
            }
        }
    }

    public int calcMaxNumToSkip(int column, int maxNumToSkip){
        // Does the current column to end of stringToSearch match the start of stringToSearch
        // If yes, update max num to skip
        // else return original int
        String end = stringToSearch.substring(column);
        String start = stringToSearch.substring(0, 0 + end.length());
        if(end.equals(start)){
            maxNumToSkip = stringToSearch.length() - end.length();
            return maxNumToSkip;
        }
        
        return maxNumToSkip;
    }

    public int moveBackDistance(String currSubString){
        int moveBackDistance = 0;

        // Take currsubstring, comparing against the substring at the index before current. 
        // E.g. if current substring is at index 3, compare current substring with substring at index 2. 
        // Keep comparing against index -= 1 until there is a substring match. However many spaces currsubstring went back by is the move back distance.
        // If after entire loop there was no match, return movebackdistance of -1

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

    public int getNumColums(){
        return this.stringToSearch.length();
    }

    public String getStringToSearch(){
        return this.stringToSearch;
    }
}
