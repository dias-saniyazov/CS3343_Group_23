import java.util.ArrayList;
import java.util.List;

class Member implements Observer {
    private static int memberIdCounter = 1;
    private int memberId;
    private MembershipState memberState;
    private String username;
    private String password;
    private List<Order> orderHistory = new ArrayList<>();

    public Member() {}

    public Member(String username, String password, MembershipState memberState) {
        this.memberId = memberIdCounter++;
        this.username = username;
        this.password = password;
        this.memberState = memberState;
    }

    List<Order> viewOrderHistory() {
        return orderHistory;
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

    @Override
    public void update(Observable observable) {
        System.out.println("Member updated");
    }
}