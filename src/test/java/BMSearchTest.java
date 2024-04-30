import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

/*
 * BMSearchTest.java tests the BMSearch class
 */
public class BMSearchTest {

    private BMTable getTable() throws IOException {
        return BMSearch.createBMTable("src/test/resources/kokako.txt");
    }

    @Test
    public void testCorrectString() throws IOException {
        BMTable table = getTable();
        assertEquals("kokako", table.getSearchString());
    }

    @Test
    public void testCorrectRows() throws IOException {
        BMTable table = getTable();
        assertEquals(6, table.getSkipAmounts('a').length);
        assertEquals(6, table.getSkipAmounts('k').length);
        assertEquals(6, table.getSkipAmounts('o').length);
        assertEquals(table.getDefaultRow(), table.getRow('*'));
        assertEquals(6, table.getDefaultRow().getSkipAmounts().length);
    }

    @Test
    public void testCorrectSkipAmounts() throws IOException {
        BMTable table = getTable();
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
}
