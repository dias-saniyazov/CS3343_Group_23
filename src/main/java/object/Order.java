package main.java.object;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import main.java.user.Member;

public class Order {
    private static int orderCount = 1;
    private int orderID;
    private int memberId;
    private LocalDateTime orderTime;
    private Map<String, Integer> items = new HashMap<>();
    private float totalCost;
    public int getMemberId() {
        return memberId;
    }

    public static void setCounter(int num) {
        Order.orderCount = num;
    }


    public Order(Member member, Cart cart) {
        this.orderID = Order.orderCount;
        Order.orderCount += 1;
        this.memberId = member.getMemberId();
        this.items = cart.getItems();
        this.orderTime = LocalDateTime.now().withNano(0);
        this.totalCost = cart.getTotal();
        member.addOrder(this);
    }

    public void changeOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderID() {
        return orderID;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }
    interface Observable {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }
}