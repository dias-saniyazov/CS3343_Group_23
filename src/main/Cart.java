import java.util.ArrayList;
import java.util.List;

class Cart {
    private List<MenuItem> items = new ArrayList<>();
    private float total;

    float loadCost() {
        total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    boolean addItem(MenuItem item) {
        return items.add(item);
    }

    boolean removeItem(MenuItem item) {
        return items.remove(item);
    }

    List<MenuItem> getItems() {
        return items;
    }
}