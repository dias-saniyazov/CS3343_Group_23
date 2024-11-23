import java.util.ArrayList;
import java.util.List;

class Cart {
    private List<MenuItem> items = new ArrayList<>();
    private float total = 0;

    public Cart(List<MenuItem> items, float total) {
        this.items = items;
    }

    public boolean addItem(MenuItem item) {
        total += item.getPrice();
        return items.add(item);
    }

    public boolean removeItem(MenuItem item) {
        total -= item.getPrice();
        return items.remove(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public float getTotal() {
        return total;
    }
}