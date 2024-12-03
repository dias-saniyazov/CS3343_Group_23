package main.java.app;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.exception.InvalidInputException;
import main.java.service.CommandService;

public class ClientApplication extends Application {

    @Override
    public void start(Scanner scanner) {
        System.out.println("Welcome to the Food Delivery System!");
        CommandService command = new CommandService();
        while (true) {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. View Menu");
            System.out.println("4. Exit");
            int choice;
            try{
                choice = getIntInput(scanner, 4);
                scanner.nextLine();  // Consume newline
            } catch(Exception e){
                scanner.nextLine();  // Consume newline
                System.out.println(e.getMessage());
                continue;
            }
            
            if (choice == 1) {
                command.login(scanner);
            } else if (choice == 2) {
                command.register(scanner);
            } else if (choice == 3) {
                command.viewMenu();
            }
             else if (choice == 4) {
                break;
            }
            
        }
        scanner.close();
    }
    
    private int getIntInput(Scanner scanner, int num) throws InvalidInputException{
        try {
            int next = scanner.nextInt();
            if(next > num || next <= 0) throw new InvalidInputException(num);
            return next;
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Input valid command number!");
        }
    }
    
}