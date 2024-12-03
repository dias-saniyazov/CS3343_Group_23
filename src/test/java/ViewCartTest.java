package test.java;

import main.java.app.DBController;
import main.java.object.MenuItem;
import main.java.user.Member;
import main.java.app.MemberApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ViewCartTest {
    private DBController dbController;
    private Member member;
    private MemberApplication memberApp;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = new Member("testUser", "password");
        memberApp = new MemberApplication(member);

        List<String> tags = new ArrayList<>();
        tags.add("Tag1");
        MenuItem item1 = new MenuItem("TestItem1", "Description1", 10.0f, tags);
        MenuItem item2 = new MenuItem("TestItem2", "Description2", 15.0f, tags);

        dbController.addNewMenuItem(item1);
        dbController.addNewMenuItem(item2);

        member.addToCart(item1, 1);
        member.addToCart(item2, 2);
    }

    @Test
    public void testViewCart() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        memberApp.viewCart();

        String output = outputStream.toString();
        assertTrue(output.contains("TestItem1"));
        assertTrue(output.contains("TestItem2"));
        assertTrue(output.contains("Quantity: 1"));
        assertTrue(output.contains("Quantity: 2"));
        assertTrue(output.contains("Total: 40.0"));
    }

    @Test
    public void testViewEmptyCart() {
        member.getCart().emptyCart();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        memberApp.viewCart();

        String output = outputStream.toString();
        assertTrue(output.contains("Cart is empty."));
    }

    @After
    public void tearDown() {
        dbController.removeMember("testUser");
        dbController.removeNewMenuItem("TestItem1");
        dbController.removeNewMenuItem("TestItem2");
    }
}