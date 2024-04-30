import org.junit.Test;
import static org.junit.Assert.*;

/*
 * BMTableRowTest.java tests the BMTableRow class
 */
public class BMTableTest {

    @Test
    public void testInstantion() {
        BMTable table = new BMTable("test");
        assertEquals(4, table.getSkipAmounts('t').length);
        assertEquals(0, table.getSkipAmount('t', 0));
        assertEquals(0, table.getSkipAmount('t', 1));
        assertEquals(0, table.getSkipAmount('t', 2));
        assertEquals(0, table.getSkipAmount('t', 3));
        assertEquals(4, table.getSkipAmounts('e').length);
        assertEquals(4, table.getSkipAmounts('s').length);
        assertEquals(4, table.getSkipAmounts('t').length);
        assertEquals(4, table.getDefaultRow().getSkipAmounts().length);
    }

    @Test
    public void testSetSkipAmount() {
        BMTable table = new BMTable("test");
        table.setSkipAmount('t', 0, 5);
        table.setSkipAmount('t', 1, 6);
        table.setSkipAmount('t', 2, 7);
        table.setSkipAmount('t', 3, 8);
        assertEquals(5, table.getSkipAmount('t', 0));
        assertEquals(6, table.getSkipAmount('t', 1));
        assertEquals(7, table.getSkipAmount('t', 2));
        assertEquals(8, table.getSkipAmount('t', 3));
    }

    @Test
    public void testSetSkipAmounts() {
        BMTable table = new BMTable("test");
        int[] skipAmounts = {5, 6, 7, 8};
        table.setSkipAmounts('t', skipAmounts);
        assertEquals(5, table.getSkipAmount('t', 0));
        assertEquals(6, table.getSkipAmount('t', 1));
        assertEquals(7, table.getSkipAmount('t', 2));
        assertEquals(8, table.getSkipAmount('t', 3));
    }

    @Test
    public void testGetSkipAmounts() {
        BMTable table = new BMTable("test");
        int[] skipAmounts = {5, 6, 7, 8};
        table.setSkipAmounts('t', skipAmounts);
        assertArrayEquals(skipAmounts, table.getSkipAmounts('t'));
    }

    @Test
    public void testGetRow() {
        BMTable table = new BMTable("test");
        BMTableRow row = table.getRow('t');
        assertEquals(4, row.getSkipAmounts().length);
        assertEquals(0, row.getSkipAmounts()[0]);
        assertEquals(0, row.getSkipAmounts()[1]);
        assertEquals(0, row.getSkipAmounts()[2]);
        assertEquals(0, row.getSkipAmounts()[3]);
    }

    @Test
    public void testGetDefaultRow() {
        BMTable table = new BMTable("test");
        BMTableRow row = table.getDefaultRow();
        assertEquals(4, row.getSkipAmounts().length);
        assertEquals(0, row.getSkipAmounts()[0]);
        assertEquals(0, row.getSkipAmounts()[1]);
        assertEquals(0, row.getSkipAmounts()[2]);
        assertEquals(0, row.getSkipAmounts()[3]);
        BMTableRow row2 = table.getRow('a');  // This should return the default row, since 'a' is not in the search string
        assertEquals(row, row2);
    }
}
