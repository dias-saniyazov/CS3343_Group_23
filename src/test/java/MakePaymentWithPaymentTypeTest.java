package test.java;

import main.java.payment.Payment;
import main.java.user.Member;
import main.java.app.DBController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class MakePaymentWithPaymentTypeTest {
    private DBController dbController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setBalance(50.0f); // Set initial balance
    }

    @Test
    public void testMakePaymentWithPaymentType() {
        Payment payment = new Payment("Credit Card");
        boolean result = payment.makePaymentwithPaymentType(member, 20.0f);
        assertTrue(result);
        assertEquals(70.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testMakePaymentWithPaymentTypeInsufficientBalance() {
        Payment payment = new Payment("Credit Card");
        boolean result = payment.makePaymentwithPaymentType(member, 100.0f);
        assertTrue(result);
        assertEquals(150.0f, member.getBalance(), 0.0);
    }
}