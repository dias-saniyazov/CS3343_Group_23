package test.java;

import main.java.app.DBController;
import main.java.object.MenuItem;
import main.java.user.Member;
import main.java.user.MembershipState;
import main.java.app.MemberApplication;
import main.java.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddItemToCartTest {
    private DBController dbController;
    private Member member;
    private MemberApplication memberApplication;

    @Before
    public void setUp() {
        dbController = DBController.getInstance();
        member = dbController.validateMemberCredentials("testUser", "password");
        memberApplication = new MemberApplication(member);
    }

    @Test
    public void testAddItemToCart() throws InvalidInputException {
        MenuItem menuItem = new MenuItem("Pizza", "Cheesy Italian pizza with tomatoes and salami", 15.99f, Arrays.asList("italian", "pizza", "salami", "cheese"));
        dbController.addNewMenuItem(menuItem);

        String input = "Pizza\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        memberApplication.addItemToCart(scanner, dbController);

        assertTrue(member.getCart().getItems().containsKey(menuItem.getName()));
        assertEquals(2, (int) member.getCart().getItems().get(menuItem.getName()));
    }

    @Test
    public void testAddItemToCartItemNotFound() throws InvalidInputException {
        String input = "NonExistentItem\n2\n1\nNonExistentItem\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        memberApplication.addItemToCart(scanner, dbController);

        assertFalse(member.getCart().getItems().containsKey("NonExistentItem"));
    }

    @Test
    public void testAddItemToCartInvalidQuantity() throws InvalidInputException {
        MenuItem menuItem = new MenuItem("Pizza", "Cheesy Italian pizza with tomatoes and salami", 15.99f, Arrays.asList("italian", "pizza", "salami", "cheese"));
        dbController.addNewMenuItem(menuItem);

        String input = "Pizza\n-1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        memberApplication.addItemToCart(scanner, dbController);

        assertTrue(member.getCart().getItems().containsKey(menuItem.getName()));
        assertEquals(2, (int) member.getCart().getItems().get(menuItem.getName()));
    }
}