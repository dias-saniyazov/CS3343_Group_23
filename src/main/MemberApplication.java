import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println("****************************");
            System.out.println("1. View Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Empty Cart");
            System.out.println("5. Complete Order");
            System.out.println("6. View Notifications");
            System.out.println("7. View order history");
            System.out.println("8. Top up Balance");
            System.out.println("9. Logout");
            System.out.println("****************************");
            int choice = getIntInput(scanner);

            if (choice == 1) {
                super.viewMenu();
            } else if (choice == 2) {
                while(true){
                    System.out.println("Enter item name:");
                    scanner.nextLine(); // Consume newline
                    String itemName = scanner.nextLine();
                    System.out.println("Enter quantity:");
                    int quantity = getIntInput(scanner);
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
                        int option = getIntInput(scanner);
                        if(option == 2){
                            break;
                        }
                    }
                }
            } else if (choice == 3) {
                System.out.println("********** CART ************");
                if (cart.getItems().isEmpty()) {
                    System.out.println("Cart is empty.");
                }
                else {
                    for (Map.Entry<String, Integer> entry : cart.getItems().entrySet()) {
                        String item = entry.getKey();
                        int quantity = entry.getValue();
                        System.out.println(item + " - Quantity: " + quantity + ", Price: " + dbController.findPrice(item) * quantity);
                    }
                    System.out.println("____________________________");
                    System.out.println("Total: " + cart.getTotal());
                    System.out.println("****************************");
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
                try {
                    float amount = getFloatInput(scanner);
                    PaymentController paymentController = new PaymentController(dbController);
                    boolean isOrderCompleted = paymentController.topUpBalance(member, amount);
                    if(!isOrderCompleted){
                        System.out.println("Failed to top up balance.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid floating-point number.");
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
        Order order = new Order(member, cart);
        DBController db = DBController.getInstance();
        boolean success = db.createTransaction(order);
        if (success) {
            System.out.println("Order created successfully!");
        }
        return success;
    }

    void viewOrders(Member member) {
        List<Integer> orders = member.viewOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Integer order : orders) {
                System.out.println("Order ID: " + order);
            }
        }
    }
    private int getIntInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            //scanner.nextLine(); // Consume the invalid input
            return -1;
        }
    }

    private float getFloatInput(Scanner scanner) {
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            //scanner.nextLine(); // Consume the invalid input
            return -1;
        }
    }
}