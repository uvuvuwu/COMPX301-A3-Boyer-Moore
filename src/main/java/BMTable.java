import java.util.HashMap;

/*
* BMTable.java
* Holds the elements of the Boyer-Moore table
*/
public class BMTable {

    private HashMap<Character, BMTableRow> table;
    private BMTableRow defaultSkipRow;
    private String searchString;

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
}
