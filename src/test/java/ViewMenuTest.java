package test.java;
import main.java.service.CommandService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class ViewMenuTest {
    private CommandService command;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
        public void setUp() {
            command = new CommandService();
            System.setOut(new PrintStream(outContent));
        }

    @Test
    public void testViewMenu() {
        command.viewMenu();
        assertTrue(outContent.toString().contains("Menu:"));
    }
}