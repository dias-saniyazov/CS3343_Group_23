package test.java;

import main.java.user.Member;
import main.java.app.DBController;
import main.java.user.MembershipState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;   

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assertions.*;

public class ChangeBalanceTest {
    private DBController dbController;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        //member = new Member("testUser", "password");
        dbController.createMember("testUser", "password");
    } 

    @Test
    public void testSuccess() {
        Member member = dbController.validateMemberCredentials("testUser", "password");
        boolean result = dbController.changeBalance(member, 100.0f);
        assertEquals(100.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testNullUser() {
        Member member = dbController.validateMemberCredentials("wrongUser", "wrongPassword");
        boolean result = dbController.changeBalance(member, 100.0f);
        assertFalse(result);
    }

    @Test
    public void testUserNotExitst() {
        Member member = new Member("wrongUser", "wrongPassword");
        boolean result = dbController.changeBalance(member, 100.0f);
        assertFalse(result);
    }
}