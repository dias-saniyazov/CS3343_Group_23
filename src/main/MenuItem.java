import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

class MenuItem implements Observable {
    private static int itemIdCounter = 1;
    @JsonProperty("menuItemId")
    private int menuItemId;
    private List<Observer> observers = new ArrayList<>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private float price;
    private List<Tag> tags = new ArrayList<>();
    private String description;
    
    public MenuItem() {}
    
    public MenuItem(String name, String description, float price, List<Tag> tags) {
        this.menuItemId = itemIdCounter++;
        this.name = name;
        this.price = price;
        this.description = description;
        this.tags = tags;
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
            observer.update(this);
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