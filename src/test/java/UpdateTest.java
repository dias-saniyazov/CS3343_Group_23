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

import static org.junit.Assertions.*;

public class UpdateTest {

    private DBController dbController;
    private Member newMember;
    private MenuItem newItem;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        newItem = new MenuItem("Test Item", "Test Description", 5.99f, new ArrayList<>());
        newMember = new Member("testUser", "password");
    }
    @Test
    public void testUpdateSuccess() {
        newMember.update(newItem, newItem.getName());
        List<String> notifications = newMember.getNotifications();
        assertEquals(notifications.get(0), newItem.getName());
    }

    @After
    public void removeItems(){
        dbController.removeMember("testUser");
        dbController.removeNewMenuItem("Test Item");
    }
}