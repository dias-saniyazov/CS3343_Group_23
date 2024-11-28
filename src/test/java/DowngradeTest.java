package test.java;
import main.java.user.Member;
import main.java.app.DBController;
import main.java.app.MemberApplication;
import main.java.user.MembershipState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;



public class DowngradeTest {
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
    public void testDowngradeFromPremium() {
        member.setRole("Premium");
        memberApp.downgrade(dbController);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }

    @Test
    public void testDowngradeFromStandard() {
        member.setRole("Standard");
        memberApp.downgrade(dbController);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }
}