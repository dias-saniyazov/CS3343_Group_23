package test.java;
import main.java.user.Member;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.io.PrintStream;



public class GetNotificationTest {
    private DBController dbController;  
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private MemberApplication memberApp;
    private Member member;

    @Before
        public void setUp() {
        System.setOut(new PrintStream(outContent));
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        memberApp = new MemberApplication(member);
    }

    @Test
    public void testNoNotifications() {
        List<String> notifications = member.getNotifications();
        notifications.clear();
        memberApp.getNotifications();
        assertTrue(outContent.toString().contains("No new notifications."));
    }

    @Test
    public void testNotificationsPrinted() {
        List<String> notifications = member.getNotifications();
        notifications.add("Notification 1");
        notifications.add("Notification 2");
        memberApp.getNotifications();
        assertTrue(outContent.toString().contains("Notification 1"));
        assertTrue(outContent.toString().contains("Notification 2"));
    }
}