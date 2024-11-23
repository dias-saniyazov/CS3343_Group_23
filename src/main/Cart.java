import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Cart {
    private Map<MenuItem, Integer> items = new HashMap<>(); // Key: MenuItem, Value: Quantity
    private float total = 0;

    // Default constructor for an empty cart
    public Cart() {
        // No initialization required;
    }

    // Constructor to initialize the cart with items and quantities
    public Cart(Map<MenuItem, Integer> items) {
        this.items = new HashMap<>(items);
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            this.total += item.getPrice() * quantity;
        }
    }

    public void addItem(MenuItem item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
        total += item.getPrice();
    }

    public boolean removeItem(MenuItem item) {
        if (!items.containsKey(item)) {
            return false; // Item doesn't exist in the cart
        }
        int quantity = items.get(item);
        if (quantity > 1) {
            items.put(item, quantity - 1);
        } else {
            items.remove(item);
        }
        total -= item.getPrice();
        return true;
    }

    public Map<MenuItem, Integer> getItems() {
        return new HashMap<>(items); // Return a copy of the internal map
    }

    public float getTotal() {
        return total;
    }
}