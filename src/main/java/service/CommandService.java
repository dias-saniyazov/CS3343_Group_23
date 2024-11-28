package main.java.service;

import main.java.app.*;
import java.util.Scanner;
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
    }

    public void register(){
        while (true) {
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
    }
}
