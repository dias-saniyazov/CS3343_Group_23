package test.java;

import main.java.payment.PaymentController;
import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.app.DBController;
import main.java.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TopUpBalanceTest {
    private DBController dbController;
    private PaymentController paymentController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        paymentController = new PaymentController(dbController);
        dbController.createMember("testUser", "password");
        member = dbController.validateMemberCredentials("testUser", "password");
        member.setBalance(50.0f); // Set initial balance
    }

    @Test
    public void testTopUpBalanceCreditCard() throws InvalidInputException {
        String input = "1\n"; // Simulate user selecting Credit Card
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        boolean result = paymentController.topUpBalance(member, 20.0f);
        assertTrue(result);
        assertEquals(70.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpBalanceApplePay() throws InvalidInputException {
        String input = "2\n"; // Simulate user selecting Apple Pay
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        boolean result = paymentController.topUpBalance(member, 20.0f);
        assertTrue(result);
        assertEquals(70.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpBalanceGooglePay() throws InvalidInputException {
        String input = "3\n"; // Simulate user selecting Google Pay
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        boolean result = paymentController.topUpBalance(member, 20.0f);
        assertTrue(result);
        assertEquals(70.0f, member.getBalance(), 0.0);
    }

    @Test
    public void testTopUpBalanceInvalidChoice() throws InvalidInputException {
        String input = "4\n"; // Simulate user selecting an invalid option
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        boolean result = paymentController.topUpBalance(member, 20.0f);
        assertFalse(result);
        assertEquals(50.0f, member.getBalance(), 0.0);
    }
}