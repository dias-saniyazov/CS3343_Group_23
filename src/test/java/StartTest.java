package test.java;
import main.java.app.ClientApplication;
import main.java.app.MemberApplication;
import main.java.app.DBController;
import main.java.app.AdminApplication;
import main.java.user.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;





public class StartTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private DBController dbController;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testStartClient1() {
        String input = "1\ntestUser\npassword\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome to the Food Delivery System!"));
    }

    @Test
    public void testStartClient2() {
        String input = "2\ntestUser\npassword\n2\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome to the Food Delivery System!"));
    }

    @Test
    public void testStartClient3() {
        String input = "3\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome to the Food Delivery System!"));
    }

    @Test
    public void testStartClient4() {
        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome to the Food Delivery System!"));
    }

    @Test
    public void testStartClient5() {
        String input = "51\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 4"));
    }

    @Test
    public void testStartClient6() {
        String input = "a\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Input valid command number!"));
    }

    @Test
    public void testStartClient7() {
        String input = "-3\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientApplication clientApp = new ClientApplication();
        clientApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 4"));
    }

    @Test
    public void testStartMember1() {
        String input = "1\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome testUser"));
    }

    @Test
    public void testStartMember2() {
        String input = "2\na\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Enter tags (comma separated):"));
    }

    @Test
    public void testStartMember3() {
        String input = "3\na\n\n1\n2\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Enter item name:"));
    }

    @Test
    public void testStartMember4() {
        String input = "4\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("CART"));
    }

    @Test
    public void testStartMember5() {
        String input = "5\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Cart emptied."));
    }

    @Test
    public void testStartMember6() {
        String input = "6\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Failed to complete order."));
    }

    @Test
    public void testStartMember7() {
        String input = "7\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Notifications:"));
    }

    @Test
    public void testStartMembe8() {
        String input = "8\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Number of orders:"));
    }

    @Test
    public void testStartMember9() {
        String input = "9\n1\n1\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Enter amount to top up:"));
    }

    @Test
    public void testStartMember10() {
        String input = "10\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome testUser"));
    }

    @Test
    public void testStartMember11() {
        String input = "11\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome testUser"));
    }

    @Test
    public void testStartMember12() {
        String input = "12\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Premium Membership Information:"));
    }

    @Test
    public void testStartMember13() {
        String input = "13\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Your Profile:"));
    }
    
    @Test
    public void testStartMember14() {
        String input = "14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Welcome testUser"));
    }

    @Test
    public void testStartMember15() {
        String input = "15\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 14"));
    }

    @Test
    public void testStartMember16() {
        String input = "-1\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 14"));
    }

    @Test
    public void testStartMember17() {
        String input = "a\n14\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        Member member = dbController.validateMemberCredentials("testUser", "password");
        MemberApplication memberApp = new MemberApplication(member);

        memberApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number."));
    }

    @Test
    public void testStartAdmin1() {
        AdminApplication adminApp = new AdminApplication();
        String input = "1\na\na\n-1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Price cannot be negative."));
    }

    @Test
    public void testStartAdmin2() {
        AdminApplication adminApp = new AdminApplication();
        String input = "2\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Menu:"));
    }

    @Test
    public void testStartAdmin3() {
        AdminApplication adminApp = new AdminApplication();
        String input = "3\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Orders:"));
    }

    @Test
    public void testStartAdmin4() {
        AdminApplication adminApp = new AdminApplication();
        String input = "4\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Members:"));
    }

    @Test
    public void testStartAdmin5() {
        AdminApplication adminApp = new AdminApplication();
        String input = "5\n1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Analytics for the selected period:"));
    }

    @Test
    public void testStartAdmin6() {
        AdminApplication adminApp = new AdminApplication();
        String input = "7\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 6"));
    }

    @Test
    public void testStartAdmin7() {
        AdminApplication adminApp = new AdminApplication();
        String input = "-1\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Invalid input. Please enter a number between 1 and 6"));
    }

    @Test
    public void testStartAdmin8() {
        AdminApplication adminApp = new AdminApplication();
        String input = "a\n6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Input valid command number!"));
    }

    @Test
    public void testStartAdmin9() {
        AdminApplication adminApp = new AdminApplication();
        String input = "6\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        adminApp.start(scanner);
        assertTrue(outContent.toString().contains("Admin panel"));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}