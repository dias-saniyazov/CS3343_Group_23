package test.java;
import main.java.object.Cart;
import main.java.object.MenuItem;
import static org.junit.Assertions.*;
import org.junit.BeforeEach;
import org.junit.Test;

public class AddItemTest {

    private Cart cart;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void testAddItem() {
        Item item = new Item("Apple", 1.0);
        cart.addItem(item);
        assertEquals(1, cart.getItemCount());
        assertTrue(cart.getItems().contains(item));
    }

    @Test
    public void testAddMultipleItems() {
        Item item1 = new Item("Apple", 1.0);
        Item item2 = new Item("Banana", 0.5);
        cart.addItem(item1);
        cart.addItem(item2);
        assertEquals(2, cart.getItemCount());
        assertTrue(cart.getItems().contains(item1));
        assertTrue(cart.getItems().contains(item2));
    }

    @Test
    public void testAddDuplicateItem() {
        Item item = new Item("Apple", 1.0);
        cart.addItem(item);
        cart.addItem(item);
        assertEquals(2, cart.getItemCount());
        assertEquals(2, cart.getItems().stream().filter(i -> i.equals(item)).count());
    }

    @Test
    public void testAddItemWithNegativePrice() {
        Item item = new Item("Apple", -1.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(item);
        });
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    public void testAddNullItem() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            cart.addItem(null);
        });
        assertEquals("Item cannot be null", exception.getMessage());
    }
}