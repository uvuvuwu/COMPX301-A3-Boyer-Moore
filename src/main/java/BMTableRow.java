
/*
 * BMTableRow.java
 * Each row in the BM table is represented by a BMTableRow object
 * Each object contains an array of integers that represent how many characters to skip when a mismatch occurs
 */
public class BMTableRow {

    private int[] skipAmounts;  // The skip amounts for each column in the row

    /*
     * Create a new BMTableRow object with a given length
     * @param length The length of the BMTableRow
     */
    public BMTableRow(int length) {
        this.skipAmounts = new int[length];
    }

    /*
     * Get the skip amount for a given column
     */
    public int getSkipAmount(int index) {
        return this.skipAmounts[index];
    }

    /*
     * Set the skip amount for a given column
     */
    public void setSkipAmount(int index, int value) {
        this.skipAmounts[index] = value;
    }

    /*
     * Get the skip amounts array
     */
    public int[] getSkipAmounts() {
        return this.skipAmounts;
    }

    /*
     * Set the skip amounts array
     */
    public void setSkipAmounts(int[] skipAmounts) {
        this.skipAmounts = skipAmounts;
    }
}
