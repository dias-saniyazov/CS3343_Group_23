package test.java;

import main.java.user.Member;
import main.java.app.DBController;
import main.java.object.MenuItem;
import main.java.user.MembershipState;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;   

import java.util.ArrayList;
import java.util.List;

//import static org.junit.Assertions.*;

public class AddNewMenuItemTest {

    private DBController dbController;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
    }
    @Test
    public void testAddNewMenuItemSuccess() {
        List<String> tags = new ArrayList<>();
        MenuItem newItem = new MenuItem("Test Item", "Test Description", 5.99f, tags);
        boolean result = dbController.addNewMenuItem(newItem);
        assertTrue(result);
    }

    @Test
    public void testAddNewMenuItemDuplicate() {
        List<String> tags = new ArrayList<>();
        MenuItem newItem = new MenuItem("Pizza", "Test Description", 8.99f, tags);
        boolean result = dbController.addNewMenuItem(newItem);
        assertFalse(result);
    }

    @Test
    public void testAddNewMenuItemInvalidPrice() {
        List<String> tags = new ArrayList<>();
        MenuItem newItem = new MenuItem("Test Item", "Test Description", -1.5f, tags);
        boolean result = dbController.addNewMenuItem(newItem);
        assertFalse(result);
    }

    @Test
    public void testAddNewMenuItemNullName() {
        List<String> tags = new ArrayList<>();
        MenuItem newItem = new MenuItem("", "Test Description", 1.5f, tags);
        boolean result = dbController.addNewMenuItem(newItem);
        assertFalse(result);
    }

    @After
    public void removeItem(){
        dbController.removeNewMenuItem("Test Item");
    }
}