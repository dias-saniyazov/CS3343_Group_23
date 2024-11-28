package test.java;

import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class DowngradeMemberTest {
    private DBController dbController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setRole("Premium"); // Set initial role to Premium
    }

    @Test
    public void testDowngradeMember() {
        boolean result = dbController.downgradeMember(member);
        assertTrue(result);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }

    @Test
    public void testDowngradeNonExistentMember() {
        Member nonExistentMember = new Member("nonExistentUser", "password");
        boolean result = dbController.downgradeMember(nonExistentMember);
        assertFalse(result);
    }
}