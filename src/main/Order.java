import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

class Order {
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

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }


    public Order(Member member, Cart cart) {
        this.orderID = Order.orderCount;
        Order.orderCount += 1;
        this.memberId = member.getMemberId();
        this.items = cart.getItems();
        this.orderTime = LocalDateTime.now();
        this.totalCost = cart.getTotal();
        member.addOrder(this);
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