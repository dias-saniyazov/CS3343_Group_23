package test.java;
import main.java.user.Member;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import main.java.user.MembershipState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;



public class UpgradeTest {
    private DBController dbController;
    private Member member;
    private MemberApplication memberApp;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        memberApp = new MemberApplication(member);
    }

    @Test
    public void testUpgradeWithNoBalance() {
        member.setBalance(0.0f);
        member.setRole("Standard");
        memberApp.upgrade(dbController);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }

    @Test
    public void testUpgradeSuccess() {
        member.setBalance(100f);
        member.setRole("Standard");
        memberApp.upgrade(dbController);
        assertEquals(MembershipState.PREMIUM, member.getMemberState());
    }

    @Test
    public void testUpgradeFromPremium() {
        member.setRole("Premium");
        memberApp.upgrade(dbController);
        assertEquals(MembershipState.PREMIUM, member.getMemberState());
    }
}