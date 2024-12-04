package main.java.app;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import main.java.exception.InvalidInputException;
import main.java.object.MenuItem;
import main.java.service.CommandService;

public class ClientApplication extends Application {

    public ClientApplication() {}
    
    @Override
    public void start() {
        DBController dbController = DBController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Restaurant Booking System");
        CommandService command = new CommandService();
        while (true) {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. View Menu");
            System.out.println("4. Search items by tags");
            System.out.println("5. Exit");
            int choice;
            try{
                choice = command.getIntInput(scanner, 5);
            } catch(Exception e){
                scanner.nextLine();  // Consume newline
                System.out.println(e.getMessage());
                continue;
            }
            
            if (choice == 1) {
                command.login();
            } else if (choice == 2) {
                command.register();
            } else if (choice == 3) {
                command.viewMenu();
            } else if (choice == 4) {
                command.searchItemByTags(scanner, dbController);
            } else if (choice == 5) {
                break;
            }
            
        }
        scanner.close();
    }
}