package main.java.service;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import main.java.app.*;
import main.java.exception.InvalidInputException;
import main.java.object.*;
import main.java.user.*;


public class CommandService{
    Scanner scanner = new Scanner(System.in);
    DBController dbController = DBController.getInstance();

    public void login(){
        while(true){
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            Member member = dbController.validateMemberCredentials(username, password);
            if(member != null){
                if (member.getMemberState() == MembershipState.ADMIN) {
                    ApplicationFactory appFactory = ApplicationFactory.getInstance();
                    Application adminApplication = appFactory.createAndGetApplication("Admin");
                    adminApplication.start();
                    break;
                }
                else {
                    MemberApplication memberApplication = new MemberApplication(member);
                    memberApplication.start();
                    break;
                }
            } else {
                System.out.println("Invalid login credentials.");
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                int option;
                try{
                    option = getIntInput(scanner, 2);
                    scanner.nextLine();  // Consume newline
                } catch(Exception e){
                    scanner.nextLine();  // Consume newline
                    System.out.println(e.getMessage());
                    continue;
                }
                if(option == 2){
                    break;
                }
            }
        }
    }

    public void register(){
        while (true) {
            System.out.println("Registration");
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            boolean isRegistered = dbController.createMember(username, password);
            if (isRegistered) {
                System.out.println("Registration successful. You can now log in.");
                break;
            } else {
                System.out.println("Username already exists.");
                System.out.println("1. Try again");
                System.out.println("2. Go back");
                int option;
                try{
                    option = getIntInput(scanner, 2);
                    scanner.nextLine();  // Consume newline
                } catch(Exception e){
                    scanner.nextLine();  // Consume newline
                    System.out.println(e.getMessage());
                    continue;
                }
                if(option == 2){
                    break;
                }
            }
        }
    }

    public void viewMenu() {
        List<MenuItem> menu = dbController.viewMenu();

        // Determine the maximum length of the lines to be printed
        int maxLength = 0;
        for (MenuItem item : menu) {
            int length = Math.max(item.getName().length(), item.getDescription().length());
            length = Math.max(length, ("Price: $" + item.getPrice()).length());
            length = Math.max(length, ("Tags: " + String.join(", ", item.getTags())).length());
            maxLength = Math.max(maxLength, length);
        }

        // Create a separator line of the determined length
        String separator = "-".repeat(maxLength * 2);

        System.out.println("Menu:");
        System.out.println(separator);
        for (MenuItem item : menu) {
            System.out.println("Name: " + item.getName());
            System.out.println("Description: " + item.getDescription());
            System.out.println("Price: $" + item.getPrice());
            System.out.println("Tags: " + String.join(", ", item.getTags()));
            System.out.println(separator);
        }
    }

    public int getIntInput(Scanner scanner, int num) throws InvalidInputException{
        try {
            int next = scanner.nextInt();
            if(next > num || next <= 0) throw new InvalidInputException(num);
            return next;
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Input valid command number!");
        }
    }

    public void searchItemByTags(Scanner scanner) {
        DBController dbController = DBController.getInstance();
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
}
