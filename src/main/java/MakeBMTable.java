/*
 * MakeBMTable creates a skip table on a given string
 * Inputs are
 * 1. String to search
 * 2. Name of the file to store the skip table to
 */
public class MakeBMTable {
    public static void main(String [] args) {
        // If there isn't 2 arguments, tell user to put in 2 arguments or else >:(
        if(args.length != 2){
            System.out.println("Write: java MakeBMTable stringToSearch skipTableFileName >:(");
            System.exit(0);
        }

        String substring = args[0];
        String fileName = args[1];

        
    }
}
