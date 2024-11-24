import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.HashMap;

public class AdminApplication extends Application {


    @Override
    void start() {
        DBController db = DBController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Admin panel");
        while(true) {
            System.out.println("1. Create menu item");
            System.out.println("2. View menu");
            System.out.println("3. View orders");
            System.out.println("4. View members");
            System.out.println("5. View analytics");
            System.out.println("6. Log out");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter name of the item:");
                String name = scanner.nextLine();
                System.out.println("Enter description of the item:");
                String description = scanner.nextLine();
                System.out.println("Enter price of the item:");
                float price = scanner.nextFloat();
                System.out.println("Enter tags of the item (comma separated):");
                String tagsStr = scanner.nextLine();
                String[] tagsArr = tagsStr.split(",");
                List<String> tags = List.of(tagsArr);
                if(db.addNewMenuItem(name, description, price, tags)) {
                    System.out.println("Item added successfully");
                }
                else {
                    System.out.println("Failed to add item, item with same name already exists");
                } 
            }
            else if (choice == 2) {
                viewMenu();
            }
            else if (choice == 3) { 
                viewOrders();
            }
            else if (choice == 4) {
                viewMembers();
            }
            else if (choice == 5) { 
                viewAnalytics();
            }
            else if (choice == 6) {
                break;
            }
        }

    }

    void viewMenu() {
        DBController db = DBController.getInstance();
        List<MenuItem> menu = db.viewMenu();
        System.out.println("Menu:");
        for (MenuItem item : menu) {
            System.out.println(item.getName() + ": " + item.getPrice());
        }
    }

    void viewOrders() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-10s %-20s %-10s %-10s %-20s\n", "orderID", "memberID", "ItemName", "quantity", "cost", "orderTime"));
        DBController db = DBController.getInstance();
        List<Order> orders = db.viewOrders();
        System.out.println("Orders:");
        for (Order order : orders) {
            Map<MenuItem, Integer> items = order.getItems();
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                float cost = item.getPrice() * quantity;
                sb.append(String.format("%-10d %-10d %-20s %-10d %-10.2f %-20s\n", order.getOrderID(), order.getMemberId(), item.getName(), quantity, cost, order.getOrderTime().toString()));
            }
        }
    }

    void viewMembers() {
        DBController db = DBController.getInstance();
        List<Member> menu = db.viewMembers();
        System.out.println("Members:");
        for (Member item : menu) {
            System.out.println(item.getUsername() + " | Membership: " + item.getMemberState());
        }
    }
    
    void viewAnalytics() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose period for analytics:");
        System.out.println("1. Last day");
        System.out.println("2. Last week");
        System.out.println("3. Last month");
        int choice = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime startTime;

        switch (choice) {
            case 1:
                startTime = now.minus(1, ChronoUnit.DAYS);
                break;
            case 2:
                startTime = now.minus(1, ChronoUnit.WEEKS);
                break;
            case 3:
                startTime = now.minus(1, ChronoUnit.MONTHS);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        DBController db = DBController.getInstance();
        List<Order> orders = db.viewOrders().stream()
                .filter(order -> order.getOrderTime().isAfter(startTime))
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            System.out.println("No orders found for the selected period.");
            return;
        }

        Map<MenuItem, Integer> itemSales = new HashMap<>();
        double totalRevenue = 0;
        int totalOrders = orders.size();

        for (Order order : orders) {
            for (Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                itemSales.put(item, itemSales.getOrDefault(item, 0) + quantity);
                totalRevenue += item.getPrice() * quantity;
            }
        }

        List<Map.Entry<MenuItem, Integer>> topItems = itemSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .collect(Collectors.toList());

        double averageSpending = totalRevenue / totalOrders;

        System.out.println("Analytics for the selected period:");
        System.out.println(String.format("%-20s %-10s", "ItemName", "Quantity"));
        for (Map.Entry<MenuItem, Integer> entry : topItems) {
            System.out.println(String.format("%-20s %-10d", entry.getKey().getName(), entry.getValue()));
        }
        System.out.println(String.format("Average amount of money spent by a customer: %.2f", averageSpending));
        System.out.println(String.format("Total revenue: %.2f", totalRevenue));
        System.out.println(String.format("Total orders: %d", totalOrders));
    }
}