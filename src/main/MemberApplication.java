import java.util.List;

class MemberApplication extends Application {
    @Override
    String login(String username, String password) {
        DBController db = new DBController();
        String result = db.validateMemberCredentials(username, password);
        return result.equals("valid") ? "Member logged in" : "Invalid credentials";
    }

    boolean createOrder(Member member, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Cannot create order.");
            return false;
        }
        Order order = new Order(member, cart, new Payment("Credit Card"));
        DBController db = new DBController();
        boolean success = db.createTransaction(order.getPayment());
        if (success) {
            System.out.println("Order created successfully!");
        }
        return success;
    }

    void viewOrders(Member member) {
        List<Order> orders = member.viewOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderID());
            }
        }
    }
}