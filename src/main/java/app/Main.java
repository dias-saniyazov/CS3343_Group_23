package main.java.app;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        ApplicationFactory appFactory = ApplicationFactory.getInstance();
        Application app = appFactory.createAndGetApplication("Client");

        app.start(scanner);
        
        scanner.close();
    }
}