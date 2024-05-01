import java.util.Arrays; 

/*
 * SkipTableRow holds a row in the skip table
 */
public class SkipTableRow {
    private int rowLength;
    private char rowCharacter;
    private int[] row;
    private boolean other;

    public SkipTableRow(int rowLength, char rowCharacter, boolean other){
        this.rowLength = rowLength;
        this.rowCharacter = rowCharacter;
        this.row = new int[rowLength];
        this.other = other;
    }

    public int getRowLength(){
        return this.rowLength;
    }

    public void setRowLength(int rowLength){
        this.rowLength = rowLength;
    }

    public char getRowCharacter(){
        return this.rowCharacter;
    }

    public void setRowCharacter(char rowCharacter){
        this.rowCharacter = rowCharacter;
    }

    public int[] getRow(){
        return this.row;
    }

    public void setRow(int[] row){
        this.row = row;
    }

    public void printRow(){
        System.out.println(rowCharacter + " " + Arrays.toString(row));
    }

    public boolean getOther(){
        return this.other;
    }
}
