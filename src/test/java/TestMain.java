package test.java;
import main.java.app.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class TestMain {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMain() {
        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        Main main = new Main();
        Main.main(null);
        assertTrue(outContent.toString().contains("Welcome to the Food Delivery System!"));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}