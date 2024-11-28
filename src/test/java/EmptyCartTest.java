package test.java;
import main.java.object.MenuItem;
import main.java.user.Member;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class EmptyCartTest {
    private Member member;
    private MemberApplication memberApp;
    private DBController dbController;  

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        memberApp = new MemberApplication(member);
    }

    @Test
    public void testEmpty() {
        MenuItem item1 = new MenuItem("item1", "description", 1.0f, null);
        MenuItem item2 = new MenuItem("item2", "description", 1.0f, null);

        member.addToCart(item1, 2);
        member.addToCart(item2, 1);
        memberApp.emptyCart();
        assertTrue(member.getCart().getItems().isEmpty());
        assertEquals(0, member.getCart().getTotal(), 0.0);
    }
}