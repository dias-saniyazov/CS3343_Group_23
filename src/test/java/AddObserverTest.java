package test.java;

import main.java.user.Member;
import main.java.object.MenuItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class AddObserverTest {
    private Member member;
    private MenuItem menuItem;

    @Before
    public void setUp() {
        member = new Member("testUser", "password");
        menuItem = new MenuItem("Test Item", "Test Description", 5.99f, new ArrayList<>());
    }

    @Test
    public void testAddObserver() {
        menuItem.addObserver(member);
        menuItem.notifyObservers();
        assertTrue(member.getNotifications().contains("Menu item was added: Test Item"));
    }
}