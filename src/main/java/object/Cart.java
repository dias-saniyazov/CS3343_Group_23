package main.java.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String, Integer> items = new HashMap<>(); // Key: MenuItem, Value: Quantity
    private float total = 0;

    // Default constructor for an empty cart
    public Cart() {
        // No initialization required;
    }

    // // Constructor to initialize the cart with items and quantities
    // public Cart(Map<MenuItem, Integer> items) {
    //     this.items = new HashMap<>(items);
    //     for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
    //         MenuItem item = entry.getKey();
    //         int quantity = entry.getValue();
    //         this.total += item.getPrice() * quantity;
    //     }
    // }

    public void addItem(MenuItem item, int quantity) {
        if (items.containsKey(item)) {
            items.put(item.getName(), items.get(item) + quantity);
        } else {
            items.put(item.getName(), quantity);
        }
        total += item.getPrice() * quantity;
    }

    public void emptyCart() {
        items = new HashMap<>();
        total = 0;
    }

    public Map<String, Integer> getItems() {
        return items; // Return a copy of the internal map
    }

    public float getTotal() {
        return total;
    }
}