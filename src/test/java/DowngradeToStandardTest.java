package test.java;

import main.java.payment.PaymentController;
import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class DowngradeToStandardTest {
    private DBController dbController;
    private PaymentController paymentController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        paymentController = new PaymentController(dbController);
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setRole("Premium"); // Set initial role to Premium
    }

    @Test
    public void testDowngradeToStandard() {
        boolean result = paymentController.downgradeToStandard(member);
        assertTrue(result);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }

    @Test
    public void testDowngradeToStandardAlreadyStandard() {
        member.setRole("Standard"); // Set role to Standard
        boolean result = paymentController.downgradeToStandard(member);
        assertFalse(result);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
    }
}