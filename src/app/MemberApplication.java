package app;
import exception.InvalidInputException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import main.DBController;
import object.*;
import payment.PaymentController;
import user.*;

public class MemberApplication extends ClientApplication{
    private Member member;

    MemberApplication() {
    
    }

    public MemberApplication(Member member) {
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
            System.out.println("9. Upgrade Account to Premium");
            System.out.println("10. Dowgrade Account to Standard Membership");
            System.out.println("11. View Premium Membership Information");
            System.out.println("12. Your Profile");
            System.out.println("13. Logout");
            System.out.println("****************************");
            int choice;
            try{
                choice = getIntInput(scanner);
            } catch(InvalidInputException e){
                System.out.println(e.getMessage());
                continue;
            }

            if (choice == 1) {
                super.viewMenu();
            } else if (choice == 2) {
                while(true){
                    System.out.println("Enter item name:");
                    scanner.nextLine(); // Consume newline
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
                        int option;
                        try{
                            option = getIntInput(scanner);
                        } catch(InvalidInputException e){
                            System.out.println(e.getMessage());
                            continue;
                        }                        
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
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else if (choice == 9) {
                if (member.getMemberState() == MembershipState.PREMIUM) {
                    System.out.println("You are already a premium member.");
                } else {
                    try {
                        PaymentController paymentController = new PaymentController(dbController);
                        boolean isOrderCompleted = paymentController.topUpToPremium(member, 100);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            else if (choice == 10){
                if (member.getMemberState() == MembershipState.STANDARD) {
                    System.out.println("You are already a standard member.");
                } else {
                    PaymentController paymentController = new PaymentController(dbController);
                    boolean isOrderCompleted = paymentController.toDowngradeToStandard(member);
                    if(!isOrderCompleted){
                        System.out.println("Failed to downgrade account.");
                    }
                }
            }
            else if(choice == 11){
                System.out.println("****************************");
                System.out.println("Premium Membership Information:");
                System.out.println("1. Premium members get 10% off on all orders.");
                System.out.println("2. Premium membership costs $100.");
                System.out.println("****************************");
            }
            else if(choice == 12){
                System.out.println("****************************");
                System.out.println("Your Profile:");
                System.out.println("Username: " + member.getUsername());
                System.out.println("Balance: " + member.getBalance());
                System.out.println("Membership: " + member.getMemberState());
                System.out.println("****************************");
            }
            else if (choice == 13) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-10s %-20s %-50s %-10s\n", "orderID", "memberID", "orderTime", "items", "Total"));
        DBController db = DBController.getInstance();
        List<Order> orders = member.viewOrderHistory();
        System.out.println("Number of orders: " + orders.size()); // Debug statement
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("Orders:");
            for (Order order : orders) {
                System.out.println("Processing order ID: " + order.getOrderID()); // Debug statement
                Map<String, Integer> items = order.getItems();
                float totalCost = 0;
                StringBuilder itemsList = new StringBuilder();
                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    String item = entry.getKey();
                    int quantity = entry.getValue();
                    float cost = db.findPrice(item) * quantity;
                    totalCost += cost;
                    itemsList.append(item).append(" (").append(quantity).append("), ");
                }
                if (itemsList.length() > 0) {
                    itemsList.setLength(itemsList.length() - 2); // Remove the trailing comma and space
                }
                sb.append(String.format("%-10d %-10d %-20s %-50s %-10.2f\n", 
                    order.getOrderID(), order.getMemberId(), 
                    order.getOrderTime().toString().replace("T", " "), 
                    itemsList.toString(), totalCost));
            }
            System.out.println(sb.toString());
        }
    }
    private int getIntInput(Scanner scanner) throws InvalidInputException{
        try {
            int next = scanner.nextInt();
            if(next > 13 || next < 0) throw new InvalidInputException(13);
            return next;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
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