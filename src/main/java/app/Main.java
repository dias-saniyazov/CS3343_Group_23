package main.java.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Food Delivery System!");
        Scanner scanner = new Scanner(System.in);
       
        Application app = ApplicationFactory.createAndGetApplication("Client");

        app.start();
        
        scanner.close();
    }

    // private static void handleAdminActions(AdminApplication adminApp, Scanner scanner) {
        // while (true) {
        //     System.out.println("Admin Menu:");
        //     System.out.println("1. View Menu");
        //     System.out.println("2. Add Menu Item");
        //     System.out.println("3. Logout");
        //     int choice = scanner.nextInt();
        //     scanner.nextLine(); // Consume newline

        //     if (choice == 1) {
        //         List<MenuItem> menu = adminApp.viewMenu();
        //         System.out.println("Menu:");
        //         for (MenuItem item : menu) {
        //             System.out.println(item.getName() + ": " + item.getPrice());
        //         }
        //     } else if (choice == 2) {
        //         System.out.println("Enter item name:");
        //         String itemName = scanner.nextLine();
        //         System.out.println("Enter item description:");
        //         String itemDescription = scanner.nextLine();
        //         System.out.println("Enter item price:");
        //         float itemPrice = scanner.nextFloat();
        //         scanner.nextLine(); // Consume newline

        //         MenuItem newItem = new MenuItem(itemName, itemDescription, itemPrice, new ArrayList<>());
        //         boolean isAdded = adminApp.createMenuItem(newItem);

        //         if (isAdded) {
        //             System.out.println("Menu item added successfully.");
        //         } else {
        //             System.out.println("Failed to add menu item.");
        //         }
        //     } else if (choice == 3) {
        //         break;
        //     }
        // }
    // }

    // private static void handleClientActions(ClientApplication clientApp, Scanner scanner) {
        // while (true) {
        //     System.out.println("Client Menu:");
        //     System.out.println("1. View Menu");
        //     System.out.println("2. Add Item to Cart");
        //     System.out.println("3. View Cart");
        //     System.out.println("4. Complete Order");
        //     System.out.println("5. Logout");
        //     int choice = scanner.nextInt();
        //     scanner.nextLine(); // Consume newline

        //     if (choice == 1) {
        //         List<MenuItem> menu = clientApp.viewMenu();
        //         System.out.println("Menu:");
        //         for (MenuItem item : menu) {
        //             System.out.println(item.getName() + ": " + item.getPrice());
        //         }
        //     } else if (choice == 2) {
        //         System.out.println("Enter item name:");
        //         String itemName = scanner.nextLine();
        //         System.out.println("Enter quantity:");
        //         int quantity = scanner.nextInt();
        //         scanner.nextLine(); // Consume newline

        //         List<MenuItem> menu = clientApp.viewMenu();
        //         MenuItem selectedItem = null;
        //         for (MenuItem item : menu) {
        //             if (item.getName().equals(itemName)) {
        //                 selectedItem = item;
        //                 break;
        //             }
        //         }

        //         if (selectedItem != null) {
        //             clientApp.addToCart(selectedItem, quantity);
        //             System.out.println("Item added to cart.");
        //         } else {
        //             System.out.println("Item not found.");
        //         }
        //     } else if (choice == 3) {
        //         List<MenuItem> cart = clientApp.viewCart();
        //         System.out.println("Cart:");
        //         for (MenuItem item : cart) {
        //             System.out.println(item.getName() + ": " + item.getPrice());
        //         }
        //     } else if (choice == 4) {
        //         boolean isOrderCompleted = clientApp.completeOrder();
        //         if (isOrderCompleted) {
        //             System.out.println("Order completed successfully.");
        //         } else {
        //             System.out.println("Failed to complete order.");
        //         }
        //     } else if (choice == 5) {
        //         break;
        //     }
        // }
    // }
}