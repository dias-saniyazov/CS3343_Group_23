package test.java;

import main.java.app.DBController;
import main.java.object.Cart;
import main.java.object.MenuItem;
import main.java.payment.PaymentController;
import main.java.user.Member;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

public class CheckoutTest {
    private DBController dbController;
    private PaymentController paymentController;
    private Member member;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        paymentController = new PaymentController(dbController);
        member = new Member("testUser", "password");

        List<String> tags = new ArrayList<>();
        tags.add("Tag1");
        MenuItem item1 = new MenuItem("TestItem1", "Description1", 10.0f, tags);
        MenuItem item2 = new MenuItem("TestItem2", "Description2", 15.0f, tags);

        dbController.addNewMenuItem(item1);
        dbController.addNewMenuItem(item2);

        member.addToCart(item1, 1);
        member.addToCart(item2, 2);
    }

    @Test
    public void testCheckoutSuccess() {
        member.topUpBalance(50.0f); // Ensure the member has enough balance
        boolean result = paymentController.checkout(member, member.getCart());
        assertTrue(result);
    }

    @Test
    public void testCheckoutInsufficientBalance() {
        member.topUpBalance(10.0f); // Ensure the member does not have enough balance
        boolean result = paymentController.checkout(member, member.getCart());
        assertFalse(result);
    }

    @Test
    public void testCheckoutEmptyCart() {
        Cart emptyCart = new Cart();
        boolean result = paymentController.checkout(member, emptyCart);
        assertFalse(result);
    }

    @After
    public void removeTestData() {
        dbController.removeNewMenuItem("TestItem1");
        dbController.removeNewMenuItem("TestItem2");
    }
}