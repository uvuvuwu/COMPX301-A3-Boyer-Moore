import java.util.Arrays; 

/*
 * SkipTableRow holds a row in the skip table
 */
public class SkipTableRow {
    private int rowLength;
    private char rowCharacter;
    private char[] row;

    public SkipTableRow(int rowLength, char rowCharacter){
        this.rowLength = rowLength;
        this.rowCharacter = rowCharacter;
        this.row = new char[rowLength];
    }

    public int getRowLength(){
        return this.rowLength;
    }

    public void setRowLength(int rowLength){
        this.rowLength = rowLength;
    }

    public int getRowCharacter(){
        return this.rowCharacter;
    }

    public void setRowCharacter(char rowCharacter){
        this.rowCharacter = rowCharacter;
    }

    public char[] getRow(){
        return this.row;
    }

    public void setRow(char[] row){
        this.row = row;
    }

    public void printRow(){
        System.out.println(Arrays.toString(row));
    }
}
