package test.java;

import main.java.app.AdminApplication;
import main.java.app.DBController;
import main.java.object.MenuItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.io.ByteArrayInputStream;

public class CreateMenuItemTest {
    private AdminApplication adminApp;
    private DBController dbController;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        adminApp = new AdminApplication();
    }

    @Test
    public void testCreateMenuItemSuccess() {
        String input = "Test Item\nNew Description\n9.99\nTag1,Tag2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        adminApp.createMenuItem(scanner);
        for(MenuItem item : dbController.viewMenu()) {
            if (item.getName().equals("Test Item")) {
                assertTrue(item.getDescription().equals("New Description"));
            }
        }
    }

    @Test
    public void testCreateMenuItemDuplicate() {
        String input = "Pizza\nDuplicate Description\n8.99\nTag1,Tag2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        adminApp.createMenuItem(scanner);
        assertFalse(dbController.viewMenu().stream().anyMatch(item -> item.getDescription().equals("Duplicate Description")));
    }

    @After
    public void removeNewMenuItem() {
        dbController.removeNewMenuItem("Test Item");
    }
}