package main.java.app;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.java.exception.InvalidInputException;
import main.java.object.*;
import main.java.service.CommandService;
import main.java.user.*;

public class AdminApplication extends Application {


    @Override
    public void start(Scanner scanner) {
        DBController db = DBController.getInstance();
        System.out.println("Admin panel");
        CommandService command = new CommandService();
        while(true) {
            System.out.println("****************************");
            System.out.println("1. Create menu item");
            System.out.println("2. View menu");
            System.out.println("3. View orders");
            System.out.println("4. View members");
            System.out.println("5. View analytics");
            System.out.println("6. Log out");
            System.out.println("****************************");
            int choice;
            try{
                choice = getIntInput(scanner);
            } catch(Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            scanner.nextLine();
            if (choice == 1) {
                createMenuItem(scanner, db);
            }
            else if (choice == 2) {
                command.viewMenu();
            }
            else if (choice == 3) { 
                viewOrders();
            }
            else if (choice == 4) {
                viewMembers();
            }
            else if (choice == 5) { 
                viewAnalytics(scanner);
            }
            else if (choice == 6) {
                break;
            }
        }

    }

    public void createMenuItem(Scanner scanner, DBController db) {
        System.out.println("Enter name of the item:");
        String name = scanner.nextLine();
        System.out.println("Enter description of the item:");
        String description = scanner.nextLine();
        System.out.println("Enter price of the item:");
        
        
        String priceInput = scanner.nextLine(); // Read price as a string to handle both "./," formats
        float price = 0;
        priceInput = priceInput.replace(",", ".");
        try {
            price = Float.parseFloat(priceInput);
            if(price < 0) throw new InvalidInputException("Price cannot be negative.");
            System.out.println("Enter tags of the item (comma separated):");
            String tagsStr = scanner.nextLine();
            String[] tagsArr = tagsStr.split(",");
            List<String> tags = Arrays.asList(tagsArr);
            if(db.addNewMenuItem(name, description, price, tags)) {
                System.out.println("Item added successfully");
            }
            else {
                System.out.println("Failed to add item, item with same name already exists");
            } 
        } catch(InvalidInputException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            //scanner.nextLine(); // Consume the invalid input
        }
        
        
    }

    private static int getIntInput(Scanner scanner) throws InvalidInputException {
        try {
            int n = scanner.nextInt();
            if(n < 0 || n > 6){
                throw new InvalidInputException(6);
            }
            return n;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consume the invalid input
            throw new InputMismatchException("Input valid command number!");
        } 

    }


    public void viewOrders() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-10s %-20s %-50s %-10s\n", "orderID", "memberID", "orderTime", "items", "Total"));
        DBController db = DBController.getInstance();
        List<Order> orders = db.viewOrders();
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
            sb.append(String.format("%-10d %-10d %-20s %-50s %-10.2f\n", order.getOrderID(), order.getMemberId(), order.getOrderTime().toString().replace("T", " "), itemsList.toString(), totalCost));
        }
        System.out.println(sb.toString());
    }

    public void viewMembers() {
        DBController db = DBController.getInstance();
        List<Member> menu = db.viewMembers();
        System.out.println("Members:");
        for (Member item : menu) {
            System.out.println(item.getUsername() + " | Membership: " + item.getMemberState());
        }
    }
    
    public void viewAnalytics(Scanner scanner) {
        System.out.println("Choose period for analytics:");
        System.out.println("1. Last day");
        System.out.println("2. Last week");
        System.out.println("3. Last month");
        int choice = scanner.nextInt();
        //scanner.nextLine();

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

        Map<String, Integer> itemSales = new HashMap<>();
        double totalRevenue = 0;
        int totalOrders = orders.size();

        for (Order order : orders) {
            for (Map.Entry<String, Integer> entry : order.getItems().entrySet()) {
                String item = entry.getKey();
                int quantity = entry.getValue();
                itemSales.put(item, itemSales.getOrDefault(item, 0) + quantity);
                totalRevenue += db.findPrice(item) * quantity;
            }
        }

        List<Map.Entry<String, Integer>> topItems = itemSales.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(5)
            .collect(Collectors.toList());

        double averageSpending = totalRevenue / totalOrders;

        System.out.println("Analytics for the selected period:");
        System.out.println(String.format("%-20s %-10s", "ItemName", "Quantity"));
        for (Map.Entry<String, Integer> entry : topItems) {
            System.out.println(String.format("%-20s %-10d", entry.getKey(), entry.getValue()));
        }
        System.out.println(String.format("Average amount of money spent by a customer: %.2f", averageSpending));
        System.out.println(String.format("Total revenue: %.2f", totalRevenue));
        System.out.println(String.format("Total orders: %d", totalOrders));
    }
}