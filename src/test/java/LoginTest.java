package test.java;

import main.java.app.Application;
import main.java.app.ApplicationFactory;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.service.CommandService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private CommandService commandService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // dbController = mock(DBController.class);
        commandService = new CommandService();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testLoginAdminSuccess() {
        String input = "admin\nadmin\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
    
        commandService.login(scanner);

        assertTrue(outContent.toString().contains("Admin panel"));
    }

    @Test
    public void testLoginMemberSuccess() {
        String input = "testUser\npassword\n14\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        commandService.login(scanner);

        assertTrue(outContent.toString().contains("Welcome testUser"));
    }

    @Test
    public void testLoginInvalidCredentials() {
        String input = "wrongUser\nwrongPassword\n1\nwrongUser\nwrongPassword\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        commandService.login(scanner);
        assertTrue(outContent.toString().contains("Invalid login credentials."));
    }
}