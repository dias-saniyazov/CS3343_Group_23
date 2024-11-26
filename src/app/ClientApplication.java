package app;
import exception.InvalidInputException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import main.DBController;
import object.*;
import user.*;

public class ClientApplication extends Application {

    void register(String username, String password) {
        DBController db = DBController.getInstance();
        if (!db.checkIfMemberExist(username)) {
            //Member newMember = new Member(username, password, Standard.getInstance());
            db.createMember(username, password);
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public void viewMenu() {
        DBController db = DBController.getInstance();
        List<MenuItem> menu = db.viewMenu();
        System.out.println("Menu:");
        for (MenuItem item : menu) {
            System.out.println(item.getName() + ": " + item.getPrice());
        }
    }

    @Override
    public void start() {
        DBController dbController = DBController.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Restaurant Booking System");
        while (true) {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. View Menu");
            System.out.println("4. Exit");
            int choice;
            try{
                choice = getIntInput(scanner);
                scanner.nextLine();  // Consume newline
            } catch(Exception e){
                scanner.nextLine();  // Consume newline
                System.out.println(e.getMessage());
                continue;
            }
            
            if (choice == 1) {
                //login();
                while(true){
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    Member member = dbController.validateMemberCredentials(username, password);
                    if(member != null){
                        if (member.getMemberState() == MembershipState.ADMIN) {
                            Application adminApplication = ApplicationFactory.createAndGetApplication("Admin");
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
                        int option = scanner.nextInt();
                        scanner.nextLine();
                        if(option == 2){
                            break;
                        }
                    }
                }
            } else if (choice == 2) {
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
                        int option = scanner.nextInt();
                        scanner.nextLine();
                        if(option == 2){
                            break;
                        }
                    }
                }
            } else if (choice == 3) {
                viewMenu();
            }
             else if (choice == 4) {
                break;
            }
            
        }
        scanner.close();
    }
    
    private int getIntInput(Scanner scanner) throws InvalidInputException{
        try {
            int next = scanner.nextInt();
            if(next > 4 || next < 0) throw new InvalidInputException(4);
            return next;
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Input valid command number!");
        }
    }
    
}