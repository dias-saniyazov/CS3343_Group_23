package test.java;

import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class UpgradeMemberTest {
    private DBController dbController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setRole("Standard"); // Set initial role to Standard
    }

    @Test
    public void testUpgradeMember() {
        boolean result = dbController.upgradeMember(member);
        assertTrue(result);
        assertEquals(MembershipState.PREMIUM, member.getMemberState());
    }

    @Test
    public void testUpgradeNonExistentMember() {
        Member nonExistentMember = new Member("nonExistentUser", "password");
        boolean result = dbController.upgradeMember(nonExistentMember);
        assertFalse(result);
    }
}