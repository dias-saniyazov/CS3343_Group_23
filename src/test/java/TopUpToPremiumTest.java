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

public class TopUpToPremiumTest {
    private DBController dbController;
    private PaymentController paymentController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        paymentController = new PaymentController(dbController);
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setBalance(150.0f); // Set initial balance
        member.setRole("Standard"); // Set role to Standard
    }

    @Test
    public void testTopUpToPremium() {
        boolean result = paymentController.topUpToPremium(member, 100);
        assertTrue(result);
        assertEquals(MembershipState.PREMIUM, member.getMemberState());
        assertEquals(50.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpToPremiumAlreadyPremium() {
        member.setRole("Premium"); // Set role to Premium
        boolean result = paymentController.topUpToPremium(member, 100.0f);
        assertFalse(result);
        assertEquals(MembershipState.PREMIUM, member.getMemberState());
        assertEquals(150.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpToPremiumInsufficientBalance() {
        member.setBalance(50.0f); // Set balance to 50
        member.setRole("Standard"); // Set role to Standard
        boolean result = paymentController.topUpToPremium(member, 100.0f);
        assertFalse(result);
        assertEquals(MembershipState.STANDARD, member.getMemberState());
        assertEquals(50.0f, member.getBalance(), 0.0);
    }
}