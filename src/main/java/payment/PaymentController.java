package main.java.payment;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.app.DBController;
import main.java.exception.InvalidInputException;
import main.java.object.*;
import main.java.user.*;
public class PaymentController {

    private DBController dbController;

    public PaymentController(DBController dbController) {
        this.dbController = dbController;
    }

    // Checkout method for a specific order using the cart and member details
    public boolean checkout(Member member, Cart cart) {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty. Cannot proceed with checkout.");
            return false;
        }

        // Load the cart's total cost
        float totalCost = cart.getTotal();

        
        
        

        Payment payment = new Payment("Balance"); // Assuming default payment type is Credit Card

        // Discount logic based on membership state
        MembershipState state = member.getMemberState(); // Assuming 10 minutes for discount calculation
        if(MembershipState.PREMIUM.equals(state)) {
            totalCost = totalCost * 0.9f; // 10% discount for premium members
        } 
        System.out.println("Cart total cost: " + totalCost);
        if(payment.makePaymentWithBalance(member, totalCost) == false) {
            return false;
        }
        // Create the order
        Order order = new Order(member, cart);

        // Save the order to the database via DBController
        boolean transactionStatus = dbController.createTransaction(order);

        if (transactionStatus) {
            cart.emptyCart();
            return true;
        } else {
            return false;
        }
    }
    public boolean topUpBalance(Member member, float amount) throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Apple Pay");
        System.out.println("3. Google Pay");
        int choice;
        try{
            choice = getIntInput(scanner);
            String paymentType = "";
            switch (choice) {
                case 1:
                    paymentType = "Credit Card";
                    break;
                case 2:
                    paymentType = "Apple Pay";
                    break;
                case 3:
                    paymentType = "Google Pay";
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return false;
            }
            Payment payment = new Payment(paymentType);
            boolean paymentSuccess = payment.makePaymentwithPaymentType(member, amount);

            if (paymentSuccess) {
                System.out.println("Balance topped up successfully! Your new balance is: " + (member.getBalance()));
                return true;
            } else {
                System.out.println("Failed to top up balance.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("An error occurred during the top-up process: " + e.getMessage());
            return false;
        }
        //scanner.close();
        
    }

    public boolean topUpToPremium(Member member, float amount) {
        if (member.getMemberState() == MembershipState.PREMIUM) {
            System.out.println("You are already a premium member.");
            return false;
        }
        if (member.getBalance() < 100) {
            System.out.println("Minimum top-up amount for premium membership is $100.");
            return false;
        }
        Payment payment = new Payment("Balance");
        payment.makePaymentWithBalance(member, amount);
        dbController.upgradeMember(member);
        System.out.println("Congratulations! You are now a premium member!");
        return true;
    }
    public boolean downgradeToStandard(Member member) {
        if (member.getMemberState() == MembershipState.STANDARD) {
            System.out.println("You are already a standard member.");
            return false;
        }
        dbController.downgradeMember(member);
        System.out.println("You have been downgraded to a standard member.");
        return true;
    }

    private static int getIntInput(Scanner scanner) throws InvalidInputException {
        try {
            int n = scanner.nextInt();
            if(n < 0 || n > 3){
                throw new InvalidInputException(3);
            }
            return n;
        } catch (InputMismatchException e) {
            //sscanner.nextLine(); // Consume the invalid input
            throw new InvalidInputException("Invalid input. Please enter a valid number.");
        } 

    }
}