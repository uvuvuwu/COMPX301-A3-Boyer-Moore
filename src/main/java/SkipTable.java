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
}
