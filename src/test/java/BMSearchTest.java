import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

/*
 * BMSearchTest.java tests the BMSearch class
 */
public class BMSearchTest {

    private BMTable getKokakoTable() throws IOException {
        return BMSearch.createBMTable("src/test/resources/kokako.txt");
    }

    @Test
    public void testCorrectString() throws IOException {
        BMTable table = getKokakoTable();
        assertEquals("kokako", table.getSearchString());
    }

    @Test
    public void testCorrectRows() throws IOException {
        BMTable table = getKokakoTable();
        assertEquals(6, table.getSkipAmounts('a').length);
        assertEquals(6, table.getSkipAmounts('k').length);
        assertEquals(6, table.getSkipAmounts('o').length);
        assertEquals(table.getDefaultRow(), table.getRow('*'));
        assertEquals(6, table.getDefaultRow().getSkipAmounts().length);
    }

    @Test
    public void testCorrectSkipAmounts() throws IOException {
        BMTable table = getKokakoTable();
        BMTableRow row;
        row = table.getRow('a');
        assertEquals(4, row.getSkipAmount(0));
        assertEquals(4, row.getSkipAmount(1));
        assertEquals(4, row.getSkipAmount(2));
        assertEquals(0, row.getSkipAmount(3));
        assertEquals(6, row.getSkipAmount(4));
        assertEquals(2, row.getSkipAmount(5));

        row = table.getRow('k');
        assertEquals(0, row.getSkipAmount(0));
        assertEquals(4, row.getSkipAmount(1));
        assertEquals(0, row.getSkipAmount(2));
        assertEquals(4, row.getSkipAmount(3));
        assertEquals(0, row.getSkipAmount(4));
        assertEquals(1, row.getSkipAmount(5));

        row = table.getRow('o');
        assertEquals(4, row.getSkipAmount(0));
        assertEquals(0, row.getSkipAmount(1));
        assertEquals(4, row.getSkipAmount(2));
        assertEquals(4, row.getSkipAmount(3));
        assertEquals(6, row.getSkipAmount(4));
        assertEquals(0, row.getSkipAmount(5));

        row = table.getDefaultRow();
        assertEquals(4, row.getSkipAmount(0));
        assertEquals(4, row.getSkipAmount(1));
        assertEquals(4, row.getSkipAmount(2));
        assertEquals(4, row.getSkipAmount(3));
        assertEquals(6, row.getSkipAmount(4));
        assertEquals(6, row.getSkipAmount(5));
    }

    @Test
    public void testSearchKokako() throws IOException {
        BMTable table = getKokakoTable();
        // need to capture lines printed to stdout
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // search for the string in the file
        BMSearch.search(table, "src/test/resources/kokakoSearchTest.txt");
        // check the output
        String[] expectedLines = {
            "some kokako text goes here", 
            "some kokako2 text goes here"
        };
        String[] actualLines = outContent.toString().split("\\r?\\n");
        assertEquals(2, actualLines.length);
        assertArrayEquals(expectedLines, actualLines);
    }

    @Test
    public void testSearchGull() throws IOException {
        BMTable table = BMSearch.createBMTable("src/test/resources/gull.txt");
        // need to capture lines printed to stdout
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // search for the string in MobyDick.txt
        BMSearch.search(table, "src/test/resources/MobyDick.txt");
        // check the output
        String[] expectedLines = {
            "her affrighted face from the steep gullies in the blackness overhead, aghast",
            "the landless gull, that at sunset folds her wings and is rocked to sleep",
            "screaming gull flew overhead; the two hulls wildly rolled; we gave three",
            "he raised a gull-like cry in the air, There she blows! --there she blows!  A"
        };
        String[] actualLines = outContent.toString().split("\\r?\\n");
        assertEquals(4, actualLines.length);
        assertArrayEquals(expectedLines, actualLines);
    }

    @Test
    public void testSearchToo() throws IOException {
        BMTable table = BMSearch.createBMTable("src/test/resources/too.txt");
        // need to capture lines printed to stdout
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // search for the string in MobyDick.txt
        BMSearch.search(table, "src/test/resources/MobyDick.txt");
        // check the output
        // since there are so many, we won't list them here
        // but we can verify the count (374)
        String[] actualLines = outContent.toString().split("\\r?\\n");
        assertEquals(374, actualLines.length);
    }

    @Test
    public void testSearchAsterisk() throws IOException {
        BMTable table = BMSearch.createBMTable("src/test/resources/asterisk.txt");
        // need to capture lines printed to stdout
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // search for the string in MobyDick.txt
        BMSearch.search(table, "src/test/resources/asteriskSearchText.txt");
        // check the output
        String[] expectedLines = {
            "hijk a*c d",
            "a*c defghi"
        };
        String[] actualLines = outContent.toString().split("\\r?\\n");
        assertEquals(2, actualLines.length);
        assertArrayEquals(expectedLines, actualLines);
    }
}
