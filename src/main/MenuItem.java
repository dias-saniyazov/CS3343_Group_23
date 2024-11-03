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
    //private List<Tag> tags = new ArrayList<>();

    public MenuItem() {}

    public MenuItem(String name, float price) {
        this.menuItemId = itemIdCounter++;
        this.name = name;
        this.price = price;
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

    public float getPrice() {
        return price;
    }
}