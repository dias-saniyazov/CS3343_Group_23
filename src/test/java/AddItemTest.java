package test.java;

import main.java.object.Cart;
import main.java.object.MenuItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

public class AddItemTest {
    private Cart cart;
    private MenuItem menuItem;

    @Before
    public void setUp() {
        cart = new Cart();
        menuItem = new MenuItem("Pizza", "Cheesy Italian pizza with tomatoes and salami", 15.99f, Arrays.asList("italian", "pizza", "salami", "cheese"));
    }

    @Test
    public void testAddItem() {
        cart.addItem(menuItem, 2);
        assertTrue(cart.getItems().containsKey(menuItem.getName()));
        assertEquals(2, (int) cart.getItems().get(menuItem.getName()));
        assertEquals(2 * menuItem.getPrice(), cart.getTotal(), 0.0);
    }

    @Test
    public void testAddItemMultipleTimes() {
        cart.addItem(menuItem, 1);
        cart.addItem(menuItem, 3);
        assertTrue(cart.getItems().containsKey(menuItem.getName()));
        assertEquals(4, (int) cart.getItems().get(menuItem.getName()));
        assertEquals(4 * menuItem.getPrice(), cart.getTotal(), 0.0);
    }

    @Test
    public void testAddDifferentItems() {
        MenuItem anotherItem = new MenuItem("Spaghetti", "Classic Italian pasta with bolognese sauce", 8.49f, Arrays.asList("italian", "pasta", "spaghetti", "bolognese"));
        cart.addItem(menuItem, 2);
        cart.addItem(anotherItem, 1);
        assertTrue(cart.getItems().containsKey(menuItem.getName()));
        assertTrue(cart.getItems().containsKey(anotherItem.getName()));
        assertEquals(2, (int) cart.getItems().get(menuItem.getName()));
        assertEquals(1, (int) cart.getItems().get(anotherItem.getName()));
        assertEquals(2 * menuItem.getPrice() + anotherItem.getPrice(), cart.getTotal(), 0.0);
    }
}