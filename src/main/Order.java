import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

class Order {
    private static int orderCount = 1;
    private int orderID;
    private int memberId;
    private LocalDateTime orderTime;
    private Map<MenuItem, Integer> items = new HashMap<>();
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    private Payment payment;

    public Order(Member member, Cart cart, Payment payment) {
        this.orderID = orderCount++;
        this.memberId = member.getMemberId();
        this.items = cart.getItems();
        this.orderTime = LocalDateTime.now();
        this.payment = payment;
        member.viewOrderHistory().add(this);
    }

    public int getOrderID() {
        return orderID;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public Payment getPayment() {
        return payment;
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