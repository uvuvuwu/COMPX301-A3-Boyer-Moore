import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/*
    MakeBMTableTest.java tests the MakeBMTable class
 */
public class MakeBMTableTest {

    public boolean compareFiles(File file1, File file2) throws IOException {
        if (!file1.exists() || !file2.exists()) {
            return false;
        }
        if (file1.length() != file2.length()) {
            return false;
        }
        InputStream is1 = new FileInputStream(file1);
        InputStream is2 = new FileInputStream(file2);
        boolean result = true;
        int b1;
        int b2;
        while ((b1 = is1.read()) != -1) {
            b2 = is2.read();
            if (b1 != b2) {
                result = false;
                break;
            }
        }
        is1.close();
        is2.close();
        return result;
    }

    @Test
    public void testMain() throws IOException {
        MakeBMTable.main(new String[] {"test", "src/test/generated/test.txt"});
        File file = new File("src/test/generated/test.txt");
        assertTrue(file.exists());
    }

    @Test
    public void testKokako() throws IOException {
        MakeBMTable.main(new String[] {"kokako", "src/test/generated/kokako.txt"});
        File kokakoGenerated = new File("src/test/generated/kokako.txt");
        File kokako = new File("src/test/resources/kokako.txt");
        // check the two files are the same
        assertTrue(compareFiles(kokakoGenerated, kokako));
    }

    @Test
    public void testGull() throws IOException {
        MakeBMTable.main(new String[] {"gull", "src/test/generated/gull.txt"});
        File gullGenerated = new File("src/test/generated/gull.txt");
        File gull = new File("src/test/resources/gull.txt");
        // check the two files are the same
        assertTrue(compareFiles(gullGenerated, gull));
    }

    @Test
    public void testToo() throws IOException {
        MakeBMTable.main(new String[] {"too", "src/test/generated/too.txt"});
        File tooGenerated = new File("src/test/generated/too.txt");
        File too = new File("src/test/resources/too.txt");
        // check the two files are the same
        assertTrue(compareFiles(tooGenerated, too));
    }

    @Test
    public void testAsterisk() throws IOException {
        MakeBMTable.main(new String[] {"a*c", "src/test/generated/asterisk.txt"});
        File asteriskGenerated = new File("src/test/generated/asterisk.txt");
        File asterisk = new File("src/test/resources/asterisk.txt");
        // check the two files are the same
        assertTrue(compareFiles(asteriskGenerated, asterisk));
    }
}
