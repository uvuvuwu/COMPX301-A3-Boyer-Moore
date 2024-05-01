// Name: Daniel Su  |  ID: 1604960
// Name: Alma Walmsley  |  ID: 1620155

import java.util.Arrays; 

/*
 * SkipTableRow holds a row for the SkipTable object
 */
public class SkipTableRow {
    private int rowLength;
    private char rowCharacter;
    private int[] row;
    private boolean other;

    /*
     * Initialises SkipTableRow object
     */
    public SkipTableRow(int rowLength, char rowCharacter, boolean other){
        this.rowLength = rowLength;
        this.rowCharacter = rowCharacter;
        this.row = new int[rowLength];
        this.other = other;
    }

    /*
     * getRowLength returns the length of the row
     */
    public int getRowLength(){
        return this.rowLength;
    }

    /*
     * getRowCharacter returns the character of the row on the left hand side of the table
     */
    public char getRowCharacter(){
        return this.rowCharacter;
    }

    /*
     * getRow returns the row the object is holding
     */
    public int[] getRow(){
        return this.row;
    }

    /*
     * setRow sets the row the object is holding
     */
    public void setRow(int[] row){
        this.row = row;
    }

    /*
     * printRow prints the row out
     */
    public void printRow(){
        System.out.println(rowCharacter + " " + Arrays.toString(row));
    }

    /*
     * getOther returns a boolean result, true if the row is the row of other characters, '*'
     */
    public boolean getOther(){
        return this.other;
    }
}
