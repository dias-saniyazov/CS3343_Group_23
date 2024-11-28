package test.java;

import main.java.user.Member;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;   



public class ChangeBalanceTest {
    private DBController dbController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
    } 

    @Test
    public void testSuccess() {
        float initialBalance = member.getBalance();
        boolean result = dbController.changeBalance(member, 100.0f);
        assertEquals(initialBalance + 100, member.getBalance(), 0.0);
    }

    @Test
    public void testUserNotExitst() {
        Member member = new Member("wrongUser", "wrongPassword");
        boolean result = dbController.changeBalance(member, 100.0f);
        assertFalse(result);
    }
}