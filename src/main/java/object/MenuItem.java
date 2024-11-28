package main.java.object;
import java.util.ArrayList;
import java.util.List;
import main.java.user.Member;

public class MenuItem implements Observable {
    private static int itemIdCounter = 1;
    private int menuItemId;
    private String name;
    private float price;
    private List<String> tags = new ArrayList<>();
    private String description;
    public String getDescription() {
        return description;
    }

    private List<Observer> observers = new ArrayList<>();
  
    
    public MenuItem(String name, String description, float price, List<String> tags) {
        this.menuItemId = itemIdCounter;
        this.name = name;
        this.price = price;
        this.description = description;
        this.tags = tags;
        MenuItem.itemIdCounter++;
    }

    public static void setCounter(int num) {
        MenuItem.itemIdCounter = num;
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
            observer.update(this, "Menu item was added: " + name);
        }
    }

    public List<String> getTags() {
        return tags;
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

    public boolean isObserver(Member member){
        return observers.contains(member);
    }
}