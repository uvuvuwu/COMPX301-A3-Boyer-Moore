// End to end testing for BM make and search
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

/*
 * End to end testing make BM table and search
 */
public class BMMakeAndSearch {

    private static final String BM_TABLE_FILE = "src/test/generated/BMMakeAndSearch.txt";
    private static final String BM_SEARCH_FILE = "src/test/generated/BMSearchFile.txt";

    public void MakeTable(String searchString) throws IOException {
        MakeBMTable.main(new String[] {searchString, BM_TABLE_FILE});
    }

    public String SearchBM(String inputString, String searchString) throws IOException {
        // create a BMSearchFile.txt file and write the search string to it
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(BM_SEARCH_FILE)));
        writer.write(inputString);
        writer.close();
        // need to capture lines printed to stdout
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BMSearch.main(new String[] {BM_TABLE_FILE, BM_SEARCH_FILE});
        // check the output
        return outContent.toString();
    }

    public void testMakeAndSearch(String inputString, String searchString, boolean expectedOutput, boolean expectError) throws IOException {
        MakeTable(searchString);
        String output = SearchBM(inputString, searchString);
        assertEquals(expectedOutput, output.contains(searchString));
    }

    public void expectMakeError(String searchString) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "MakeBMTable", BM_TABLE_FILE);
        Process p = pb.start();
        p.waitFor();
        int exitCode = p.exitValue();
        if (exitCode == 0) {
            fail("Expected error, but got success");
        }
    }

    public void expectSearchError(String inputString, String searchString) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "BMSearch", BM_TABLE_FILE, inputString);
        Process p = pb.start();
        p.waitFor();
        int exitCode = p.exitValue();
        if (exitCode == 0) {
            fail("Expected error, but got success");
        }
    }

    public void expectFound(String inputString, String searchString) throws IOException {
        MakeTable(searchString);
        String output = SearchBM(inputString, searchString);
        assertTrue(output.contains(searchString));
    }

    public void expectNotFound(String inputString, String searchString) throws IOException {
        MakeTable(searchString);
        String output = SearchBM(inputString, searchString);
        assertFalse(output.contains(searchString));
    }


    @Test
    public void testTestCases() throws IOException, InterruptedException {
        expectFound("Hello World!", "World");
        expectNotFound("Hello World!", "world");
        expectFound("The quick brown fox jumps", "fox");
        expectFound("alpha12345numeric", "12345");
        expectFound("A wild kokako appears", "kokako");
        expectFound("Seagulls and sand", "gull");
        expectFound("Testing, testing, 1, 2, 3", "1");
        expectFound("A", "A");
        expectNotFound("!", "1");
        expectMakeError("");
        expectSearchError("Hello World!", "");
        expectFound("This is a very long text...", "text");
        expectFound("Boundary and edge", "Boundary");
        expectNotFound("", "any");
        expectFound("12345@abcde@54321", "abc");
        expectNotFound("ABCDE", "abcde");
        expectNotFound("!!!@@@#1##2$$$3%%%^^^&&&***", "123");
        expectFound("Spaces around", "around");
        expectFound("Special$$$CharactersHere", "Here");
        expectFound("comma,separated,v,a,l,u,,e", "v,a,l,u,,e");
    }
}
