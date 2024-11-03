import java.util.ArrayList;
import java.util.List;

class Order {
    private static int orderCount = 1;
    private int orderID;
    private int memberId;
    private List<MenuItem> items = new ArrayList<>();
    //private DateTime orderTime;
    private Payment payment;

    public Order(Member member, Cart cart, Payment payment) {
        this.orderID = orderCount++;
        this.memberId = member.getMemberId();
        this.items.addAll(cart.getItems());
        // this.orderTime = orderTime;
        this.payment = payment;
        member.viewOrderHistory().add(this);
    }

    public int getOrderID() {
        return orderID;
    }

    public Payment getPayment() {
        return payment;
    }
}