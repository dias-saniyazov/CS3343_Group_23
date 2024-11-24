import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MemberApplication extends ClientApplication{
    private Member member;

    MemberApplication() {
    
    }

    MemberApplication(Member member) {
        this.member = member;
    }
    @Override
    public void start() {
        DBController dbController = DBController.getInstance();
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();
        System.out.println("Welcome " + member.getUsername());
        while (true) {
            System.out.println("1. View Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Empty Cart");
            System.out.println("5. Complete Order");
            System.out.println("6. View Notifications");
            System.out.println("7. View order history");
            System.out.println("8. Top up Balance");
            System.out.println("9. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                super.viewMenu();
            } else if (choice == 2) {
                while(true){
                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();
                    System.out.println("Enter quantity:");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    List<MenuItem> menu = dbController.viewMenu();
                    MenuItem selectedItem = null;
                    for (MenuItem item : menu) {
                        if (item.getName().equals(itemName)) {
                            selectedItem = item;
                            break;
                        }
                    }
    
                    if (selectedItem != null) {
                        cart.addItem(selectedItem, quantity);
                        System.out.println("Item added to cart.");
                        break;
                    } else {
                        System.out.println("Item not found.");
                        System.out.println("1. Try again");
                        System.out.println("2. Go back");
                        int option = scanner.nextInt();
                        scanner.nextLine();
                        if(option == 2){
                            break;
                        }
                    }
                }
            } else if (choice == 3) {
                System.out.println("Cart:");
                if (cart.getItems().isEmpty()) {
                    System.out.println("Cart is empty.");
                }
                else {
                    for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {
                        MenuItem item = entry.getKey();
                        int quantity = entry.getValue();
                        System.out.println(item.getName() + " - Quantity: " + quantity + ", Price: " + item.getPrice() * quantity);
                    }
                }
            } else if (choice == 4) {
                cart.emptyCart();
                System.out.println("Cart emptied.");
            } else if (choice == 5) {
                PaymentController paymentController = new PaymentController(dbController);
                boolean isOrderCompleted = paymentController.checkout(member, cart);
                if (isOrderCompleted) {
                    System.out.println("Order completed successfully.");
                } else {
                    System.out.println("Failed to complete order.");
                }
            } else if (choice == 6) {
                
                List<String> notifications = member.getNotifications();
                if (notifications == null || notifications.isEmpty()) {
                    System.out.println("No new notifications.");
                } else {
                    System.out.println("Notifications:");
                    for (String notification : notifications) {
                        System.out.println(notification);
                    }
                }
            } else if (choice == 7) {
                viewOrders(member);
            }
            else if(choice == 8){
                System.out.println("Enter amount to top up:");
                float amount = scanner.nextFloat();
                scanner.nextLine();
                PaymentController paymentController = new PaymentController(dbController);
                boolean isOrderCompleted = paymentController.topUpBalance(member, amount);
                if(!isOrderCompleted){
                    System.out.println("Failed to top up balance.");
                }
            }
            else if (choice == 9) {
                break;
            }
        }
    }

    boolean createOrder(Member member, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Cannot create order.");
            return false;
        }
        Order order = new Order(member, cart, new Payment("Credit Card"));
        DBController db = DBController.getInstance();
        boolean success = db.createTransaction(order);
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