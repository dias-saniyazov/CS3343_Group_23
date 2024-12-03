package test.java;
import main.java.app.AdminApplication;
import main.java.app.DBController;
import main.java.object.Order;
import main.java.user.Member;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class ViewAnalyticsTest {
    private AdminApplication adminApp;  
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private DBController dbController;
    private Member member;

    @Before
        public void setUp() {
            System.setOut(new PrintStream(outContent));
            adminApp = new AdminApplication();
            dbController = DBController.getInstance(); 
            member = dbController.validateMemberCredentials("testUser", "password");
        }

    @Test
    public void viewAnalyticsLastDay() {
        dbController.deleteOrders();
        Order order = new Order(member, member.getCart());
        dbController.createTransaction(order);
        order.changeOrderTime(LocalDateTime.now());
        String input = "1";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminApp.viewAnalytics(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Analytics for the selected period:"));
    }

    @Test
    public void viewAnalyticsLastWeek() {
        dbController.deleteOrders();
        Order order = new Order(member, member.getCart());
        dbController.createTransaction(order);
        order.changeOrderTime(LocalDateTime.now().minusDays(3));
        String input = "2";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminApp.viewAnalytics(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Analytics for the selected period:"));
    }

    @Test
    public void viewAnalyticsLastMonth() {
        dbController.deleteOrders();
        Order order = new Order(member, member.getCart());
        dbController.createTransaction(order);
        order.changeOrderTime(LocalDateTime.now().minusDays(20));
        String input = "3";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminApp.viewAnalytics(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Analytics for the selected period:"));
    }

    @Test
    public void viewAnalyticsInvalidChoice() {
        dbController.deleteOrders();
        Order order = new Order(member, member.getCart());
        dbController.createTransaction(order);
        order.changeOrderTime(LocalDateTime.now().minusDays(1));
        String input = "4";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminApp.viewAnalytics(new Scanner(System.in));
        assertTrue(outContent.toString().contains("Invalid choice"));
    }

    @Test
    public void viewAnalyticsEmpty() {
        dbController.deleteOrders();
        Order order = new Order(member, member.getCart());
        dbController.createTransaction(order);
        order.changeOrderTime(LocalDateTime.now().minusDays(5000));
        String input = "3";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        adminApp.viewAnalytics(new Scanner(System.in));
        assertTrue(outContent.toString().contains("No orders found for the selected period."));
    }
}