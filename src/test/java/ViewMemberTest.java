package test.java;
import main.java.app.AdminApplication;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class ViewMemberTest {
    private AdminApplication adminApp;  
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
        public void setUp() {
            System.setOut(new PrintStream(outContent));
            adminApp = new AdminApplication();
        }

    @Test
    public void testMembersPrinted() {
        adminApp.viewMembers();
        assertTrue(outContent.toString().contains("Members:"));
    }
}