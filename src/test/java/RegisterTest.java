package test.java;
import main.java.service.CommandService;
import main.java.app.DBController;
import main.java.exception.InvalidInputException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;





public class RegisterTest {
    private DBController dbController;
    private CommandService commandService;
    private Scanner scanner;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        dbController = DBController.getInstance();  
        commandService = new CommandService(); 
        // Assume CommandService uses DBController.getInstance(), mock static if necessary
    }

    @Test
    public void testRegisterSuccess() {
        String input = "newuser\npassword\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        commandService.register();
        assertTrue(dbController.validateMemberCredentials(input, input) != null);
    }

    // @Test
    // public void testRegisterUsernameExistsTryAgain() {
    //     String input = "existinguser\npassword\n1\nnewuser\npassword\n";
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));

    //     when(dbController.createMember("existinguser", "password")).thenReturn(false);
    //     when(dbController.createMember("newuser", "password")).thenReturn(true);

    //     commandService.register();

    //     verify(dbController).createMember("existinguser", "password");
    //     verify(dbController).createMember("newuser", "password");
    //     assertTrue(outContent.toString().contains("Registration successful. You can now log in."));
    // }

    // @Test
    // public void testRegisterUsernameExistsGoBack() {
    //     String input = "existinguser\npassword\n2\n";
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));

    //     when(dbController.createMember("existinguser", "password")).thenReturn(false);

    //     commandService.register();

    //     verify(dbController).createMember("existinguser", "password");
    //     assertTrue(outContent.toString().contains("Username already exists."));
    // }

    // @Test
    // public void testRegisterInvalidInput() {
    //     String input = "existinguser\npassword\ninvalid\n2\n";
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));

    //     when(dbController.createMember("existinguser", "password")).thenReturn(false);

    //     commandService.register();

    //     verify(dbController).createMember("existinguser", "password");
    //     assertTrue(outContent.toString().contains("Input valid command number!"));
    // }

    // @After
    // public void tearDown() {
    //     System.setOut(originalOut);
    //     System.setIn(originalIn);
    // }
}