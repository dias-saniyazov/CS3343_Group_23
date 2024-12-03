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

public class NotifyObserversTest {

    private DBController dbController;
    private Member newMember1;
    private Member newMember2;
    private MenuItem newItem;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        newMember1 = new Member("testUser1", "password");
        newMember2 = new Member("testUser2", "password");
        newItem = new MenuItem("Test Item", "Test Description", 5.99f, new ArrayList<>());
        newItem.addObserver(newMember1);
        newItem.addObserver(newMember2);
    }
    @Test
    public void testUpdateSuccess() {
        newItem.notifyObservers();
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Menu item was added: Test Item");
        expectedList.add("Menu item was added: Test Item");

        List<String> notifications = new ArrayList<>();
        notifications.add(newMember1.getNotifications().get(0));
        notifications.add(newMember2.getNotifications().get(0));
        
        assertEquals(notifications, expectedList);
    }

    @After
    public void removeItems(){
        dbController.removeMember("testUser1");
        dbController.removeMember("testUser2");
        dbController.removeNewMenuItem("Test Item");
    }
}