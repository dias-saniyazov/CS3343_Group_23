import java.util.ArrayList;
import java.util.List;
class MenuItem implements Observable {
    private static int itemIdCounter = 1;
    private int menuItemId;
    private List<Observer> observers = new ArrayList<>();
    private String name;
    private float price;
    private List<String> tags = new ArrayList<>();
    private String description;
  
    
    public MenuItem(String name, String description, float price, List<String> tags) {
        this.menuItemId = itemIdCounter;
        this.name = name;
        this.price = price;
        this.description = description;
        this.tags = tags;
        MenuItem.itemIdCounter++;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, "Menu item updated: " + name);
        }
    }

    public String getMenuItemID() {
        return String.valueOf(menuItemId);
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}