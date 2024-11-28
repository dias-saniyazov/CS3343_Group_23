package main.java.app;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import main.java.exception.InvalidInputException;
import main.java.object.*;
import main.java.payment.PaymentController;
import main.java.service.CommandService;
import main.java.user.*;

public class MemberApplication extends ClientApplication{
    private Member member;

    public MemberApplication(Member member) {
        this.member = member;
    }
    @Override
    public void start() {
        DBController dbController = DBController.getInstance();
        Scanner scanner = new Scanner(System.in);

        CommandService command = new CommandService();
        System.out.println("Welcome " + member.getUsername());
        while (true) {
            System.out.println("****************************");
            System.out.println("1. View Menu");
            System.out.println("2. Search item by tags");
            System.out.println("3. Add Item to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Empty Cart");
            System.out.println("6. Complete Order");
            System.out.println("7. View Notifications");
            System.out.println("8. View order history");
            System.out.println("9. Top up Balance");
            System.out.println("10. Upgrade Account to Premium");
            System.out.println("11. Dowgrade Account to Standard Membership");
            System.out.println("12. View Premium Membership Information");
            System.out.println("13. Your Profile");
            System.out.println("14. Logout");
            System.out.println("****************************");
            int choice;
            try{
                choice = getIntInput(scanner, 14);
            } catch(InvalidInputException e){
                System.out.println(e.getMessage());
                continue;
            }

            if (choice == 1) {
                command.viewMenu();
            } else if (choice == 2) {
                searchItemByTags(scanner, dbController);
            
            } else if (choice == 3) {
                addItemToCart(scanner, dbController);
                
            } else if (choice == 4) {
                viewCart(dbController);
            } else if (choice == 5) {
                emptyCart();
            } else if (choice == 6) {
                makeOrder(dbController);
                
            } else if (choice == 7) {
                getNotifications();
                
            } else if (choice == 8) {
                viewOrders(member);
            }
            else if(choice == 9){
                topUp(scanner, dbController);
            }
            else if (choice == 10) {
                upgrade(dbController);
            }
            else if (choice == 11){
                downgrade(dbController);
            }
            else if(choice == 12){
                printPrimiumInfo(); 
            }
            else if(choice == 13){
                printMemberInfo();
            }
            else if (choice == 14) {
                break;
            }
        }
    }
    public void topUp(Scanner scanner, DBController dbController){
        System.out.println("Enter amount to top up:");
        try {
            float amount = getFloatInput(scanner);
            PaymentController paymentController = new PaymentController(dbController);
            boolean isOrderCompleted = paymentController.topUpBalance(member, amount);

            if(!isOrderCompleted){
                System.out.println("Failed to top up balance.");
            }
        } catch(InvalidInputException e){
            System.out.println(e.getMessage());
        }
    }
    public void downgrade(DBController dbController){
        if (member.getMemberState() == MembershipState.STANDARD) {
            System.out.println("You are already a standard member.");
        } else {
            PaymentController paymentController = new PaymentController(dbController);
            boolean isOrderCompleted = paymentController.downgradeToStandard(member);
            if(!isOrderCompleted){
                System.out.println("Failed to downgrade account.");
            }
        }
    }
    void makeOrder(DBController dbController){
        PaymentController paymentController = new PaymentController(dbController);
        boolean isOrderCompleted = paymentController.checkout(member, member.getCart());
        if (isOrderCompleted) {
            System.out.println("Order completed successfully.");
        } else {
            System.out.println("Failed to complete order.");
        }
    }
    void viewCart(DBController dbController){
        System.out.println("********** CART ************");
        Cart cart = member.getCart();
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
            System.out.println("Card Total: " + cart.getTotal());
            if (member.getMemberState() == MembershipState.PREMIUM) {
                System.out.println("Discount: 10%");
                System.out.println("Total: " + cart.getTotal() * 0.9);
            }else{
                System.out.println("Total: " + cart.getTotal());
            }
            System.out.println("****************************");
        }
    }
    void printMemberInfo(){
        System.out.println("****************************");
        System.out.println("Your Profile:");
        System.out.println("Username: " + member.getUsername());
        System.out.println("Balance: " + member.getBalance());
        System.out.println("Membership: " + member.getMemberState());
        System.out.println("****************************");
    }
    void printPrimiumInfo(){
        System.out.println("****************************");
        System.out.println("Premium Membership Information:");
        System.out.println("1. Premium members get 10% off on all orders.");
        System.out.println("2. Premium membership costs $100.");
        System.out.println("****************************");
    }
    public void getNotifications(){
        List<String> notifications = member.getNotifications();
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("No new notifications.");
        } else {
            System.out.println("Notifications:");
            for (String notification : notifications) {
                System.out.println(notification);
            }
        }
    }

    public void emptyCart(){
        member.getCart().emptyCart();
        System.out.println("Cart emptied.");
    }

    public void upgrade(DBController dbController){
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

    void searchItemByTags(Scanner scanner, DBController dbController) {
        System.out.println("Enter tags (comma separated):");
        scanner.nextLine(); // Consume newline
        String tagsStr = scanner.nextLine();
        String[] tagsArr = tagsStr.split(",");
        List<String> tags = Arrays.asList(tagsArr);
        List<MenuItem> menu = dbController.viewMenu();
        System.out.println("Items with tags: " + tags);
        boolean found = false;
        for (MenuItem item : menu) {
            if (item.getTags().containsAll(tags)) {
                System.out.println(item.getName() + " - Price: " + item.getPrice());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found with tags: " + tags);
        }
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
    private int getIntInput(Scanner scanner, int max) throws InvalidInputException{
        try {
            int next = scanner.nextInt();
            if(max != 0){
                if(next > max || next <= 0) throw new InvalidInputException(max);
            }
            return next;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
            return -1;
        }
    }

    private float getFloatInput(Scanner scanner) throws InvalidInputException{
        try {
            float amount = scanner.nextFloat();
            if(amount < 0) throw new InvalidInputException("Amount cannot be negative.");
            return amount; 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            //scanner.nextLine(); // Consume the invalid input
            throw new InvalidInputException("Invalid input. Please enter a valid amount.");
        }
    }

    public void addItemToCart(Scanner scanner, DBController dbController){
        while(true){
            System.out.println("Enter item name:");
            scanner.nextLine(); // Consume newline
            String itemName = scanner.nextLine();
            System.out.println("Enter quantity:");
            int quantity;
            try{
                quantity = getIntInput(scanner, 0);
                if(quantity <= 0) throw new InvalidInputException("Quantity must be greater than 0.");
            } catch(InvalidInputException e){
                System.out.println(e.getMessage());
                continue;
            } // Consume newline
            List<MenuItem> menu = dbController.viewMenu();
            MenuItem selectedItem = null;
            for (MenuItem item : menu) {
                if (item.getName().equals(itemName)) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem != null) {
                member.addToCart(selectedItem, quantity);
                System.out.println("Item added to cart.");
                break;
            } else {
                System.out.println("Item not found.");
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                int option;
                try{
                    option = getIntInput(scanner, 2);
                } catch(InvalidInputException e){
                    System.out.println(e.getMessage());
                    continue;
                }                        
                if(option == 2){
                    break;
                }
            }
        }
    }
}