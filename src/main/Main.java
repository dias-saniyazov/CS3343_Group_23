
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// Main class to demonstrate the application
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApplicationFactory appFactory = new ApplicationFactory();
        DBController dbController = new DBController();
        
        System.out.println("Welcome to the Restaurant Booking System");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            Application clientApp = appFactory.createAndGetApplication("Client");
            Application admineApp   = appFactory.createAndGetApplication("Admin");
            boolean loginResult = dbController.loginMember(username, password);
            System.out.println(loginResult);

            if (loginResult) {

                //ClientApplication clientApplication = (ClientApplication) clientApp;
                AdminApplication adminApplication = (AdminApplication) admineApp;
                System.out.println("Do you want to add a new menu item? (yes/no)");
                String addMenuItemChoice = scanner.nextLine();

                if (addMenuItemChoice.equalsIgnoreCase("yes")) {
                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();
                    System.out.println("Enter item price:");
                    float itemPrice = scanner.nextFloat();
                    scanner.nextLine(); // Consume newline
                    String description = "This is description of a menu product";
                    List<Tag> tags = new ArrayList<>();

                    boolean isAdded = adminApplication.createMenuItem(itemName, description, itemPrice, tags);

                    if (isAdded) {
                        System.out.println("Menu item added successfully.");
                    } else {
                        System.out.println("Failed to add menu item.");
                    }
                }
                List<MenuItem> menu = dbController.viewMenu();
                System.out.println("Menu:");
                for (MenuItem item : menu) {
                    System.out.println(item.getMenuItemID() + ": " + item.getPrice());
                }
            }
        } else if (choice == 2) {
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            ClientApplication clientApp = new ClientApplication();
            clientApp.register(username, password);
        }
    }
}