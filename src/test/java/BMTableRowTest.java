import org.junit.Test;
import static org.junit.Assert.*;

/*
 * BMTableRowTest.java tests the BMTableRow class
 */
public class BMTableRowTest {

    @Test
    public void testInstantion() {
        BMTableRow row = new BMTableRow(4);
        assertEquals(4, row.getSkipAmounts().length);
        assertEquals(0, row.getSkipAmounts()[0]);
        assertEquals(0, row.getSkipAmounts()[1]);
        assertEquals(0, row.getSkipAmounts()[2]);
        assertEquals(0, row.getSkipAmounts()[3]);
    }

    @Test
    public void testSetSkipAmount() {
        BMTableRow row = new BMTableRow(3);
        row.setSkipAmount(0, 5);
        row.setSkipAmount(1, 6);
        row.setSkipAmount(2, 7);
        assertEquals(5, row.getSkipAmount(0));
        assertEquals(6, row.getSkipAmount(1));
        assertEquals(7, row.getSkipAmount(2));
    }

    @Test
    public void testSetSkipAmounts() {
        BMTableRow row = new BMTableRow(3);
        int[] skipAmounts = {5, 6, 7};
        row.setSkipAmounts(skipAmounts);
        assertEquals(5, row.getSkipAmount(0));
        assertEquals(6, row.getSkipAmount(1));
        assertEquals(7, row.getSkipAmount(2));
    }

    @Test
    public void testGetSkipAmounts() {
        BMTableRow row = new BMTableRow(3);
        int[] skipAmounts = {5, 6, 7};
        row.setSkipAmounts(skipAmounts);
        assertArrayEquals(skipAmounts, row.getSkipAmounts());
    }
}
