package test.java;

import main.java.payment.Payment;
import main.java.user.Member;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class MakePaymentWithBalanceTest {
    private DBController dbController;
    private Member member;  

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setBalance(50.0f); // Set initial balance
    }

    @Test
    public void testMakePaymentWithBalance() {
        Payment payment = new Payment("Balance");
        boolean result = payment.makePaymentWithBalance(member, 20.0f);
        assertTrue(result);
        assertEquals(30.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testMakePaymentWithBalanceInsufficientBalance() {
        Payment payment = new Payment("Balance");
        boolean result = payment.makePaymentWithBalance(member, 100.0f);
        assertFalse(result);
        assertEquals(50.0f, member.getBalance(), 0.0);
    }
}