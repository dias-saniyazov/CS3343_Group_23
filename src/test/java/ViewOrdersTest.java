package test.java;

import main.java.app.AdminApplication;
import main.java.app.DBController;
import main.java.object.Cart;
import main.java.object.MenuItem;
import main.java.object.Order;
import main.java.user.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ViewOrdersTest {
    private DBController dbController;
    private AdminApplication adminApp;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        adminApp = new AdminApplication();
        member = new Member("testUser", "password");
        member.setMemberId(1);
        member.setRole("ADMIN");

        List<String> tags = new ArrayList<>();
        tags.add("Tag1");
        MenuItem item1 = new MenuItem("TestItem1", "Description1", 10.0f, tags);
        MenuItem item2 = new MenuItem("TestItem2", "Description2", 15.0f, tags);

        dbController.addNewMenuItem(item1);
        dbController.addNewMenuItem(item2);

        Cart cart = new Cart();
        cart.addItem(item1, 1);
        cart.addItem(item2, 2);

        Order order = new Order(member, cart);
        dbController.createTransaction(order);
    }

    @Test
    public void testViewOrders() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        adminApp.viewOrders();

        String output = outputStream.toString();
        assertTrue(output.contains("TestItem1"));
        assertTrue(output.contains("TestItem2"));
    }

    @After
    public void tearDown() {
        dbController.removeMember("testUser");
        dbController.removeNewMenuItem("TestItem1");
        dbController.removeNewMenuItem("TestItem2");
    }
}