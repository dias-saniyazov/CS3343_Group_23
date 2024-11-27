package user;
import java.util.ArrayList;
import java.util.List;
import main.DBController;
import object.*;

public class Member implements Observer {
    private static int memberIdCounter = 1;
    private int memberId;
    private MembershipState memberState;
    private String username;
    private String password;    
    private float balance;
    private List<Order> orderHistory = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    public static void setCounter(int num) {
        Member.memberIdCounter = num;
    }

    public void loadOrders() {
        orderHistory.clear();
        DBController db = DBController.getInstance();
        for (Order order : db.viewOrders()) {
            if (order.getMemberId() == this.memberId) {
                orderHistory.add(order);
            }
        }
    }

    public Member(String username, String password) {
        this.memberId = Member.memberIdCounter;
        Member.memberIdCounter += 1;
        this.username = username;
        this.password = password;
        this.memberState = MembershipState.STANDARD;
        this.balance = 0;
    }

    public List<Order> viewOrderHistory() {
        return orderHistory;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public MembershipState getMemberState() {
        return memberState;
    }

    @Override
    public void update(Observable observable, String content) {
        notifications.add(content);
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setRole(String string) {
        if (string.equals("Premium")) {
            this.memberState = MembershipState.PREMIUM;
        } else {
            this.memberState = MembershipState.STANDARD;
        }
    }
    public float getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void topUpBalance(float amount) {
        this.balance = this.balance + amount;
        if(amount >= 0){
            System.out.println("Balance topped up successfully!");
        }
        else{
            System.out.println("Balance deducted successfully!");
        }
        
    }
}