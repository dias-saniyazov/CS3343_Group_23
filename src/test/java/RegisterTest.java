package test.java;
import main.java.service.CommandService;
import main.java.app.DBController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;





public class RegisterTest {
    private DBController dbController;
    private CommandService commandService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        dbController.deleteMember("newuser");  
        commandService = new CommandService(); 
        System.setOut(new PrintStream(outContent));
        // Assume CommandService uses DBController.getInstance(), mock static if necessary
    }

    @Test
    public void testRegisterSuccess() {
        String input = "newuser\npassword\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        commandService.register(scanner);
        assertTrue(dbController.validateMemberCredentials("newuser", "password").getUsername().equals("newuser"));
    }

    @Test
    public void testRegisterUsernameExistsTryAgain() {
        dbController.deleteMember("newuser");
        String input = "testUser\npassword\n1\nnewuser\npassword\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        commandService.register(scanner);
        assertTrue(outContent.toString().contains("Registration successful. You can now log in."));
    }

    @Test
    public void testRegisterUsernameExistsGoBack() {
        String input = "testUser\npassword\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        commandService.register(scanner);
        assertTrue(outContent.toString().contains("Username already exists."));
    }

    @Test
    public void testRegisterIncorrectInput() {
        String input = "\ntestUser\npassword\n3\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        commandService.register(scanner);
        assertTrue(outContent.toString().contains("Input valid command number!"));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}