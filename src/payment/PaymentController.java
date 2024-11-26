package payment;
import exception.InvalidInputException;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.DBController;
import object.*;
import user.*;
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
        System.out.println("Cart total cost: " + totalCost);

        
        
        

        Payment payment = new Payment("Balance"); // Assuming default payment type is Credit Card

        // Discount logic based on membership state
        MembershipState state = member.getMemberState(); // Assuming 10 minutes for discount calculation
        if(MembershipState.PREMIUM.equals(state)) {
            totalCost = totalCost * 0.9f; // 10% discount for premium members
        } 
        System.out.println(member.getBalance());
        if(payment.makePaymentWithBalance(member, totalCost) == false) {
            return false;
        }
        // Create the order
        Order order = new Order(member, cart);

        // Save the order to the database via DBController
        boolean transactionStatus = dbController.createTransaction(order);

        if (transactionStatus) {
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
        } catch (Exception e){
            throw e;
        }
        //scanner.close();
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
        payment.makePaymentwithPaymentType(member, amount);

        System.out.println("Balance topped up successfully! Your new balance is: " + (member.getBalance()));
        return true;
    }

    public boolean topUpToPremium(Member member, float amount) {
        if (member.getMemberState() == MembershipState.PREMIUM) {
            System.out.println("You are already a premium member.");
            return false;
        }
        if (amount < 100) {
            System.out.println("Minimum top-up amount for premium membership is $100.");
            return false;
        }
        Payment payment = new Payment("Balance");
        payment.makePaymentWithBalance(member, amount);
        dbController.upgradeMember(member);
        System.out.println("Congratulations! You are now a premium member!");
        return true;
    }
    public boolean toDowngradeToStandard(Member member) {
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
            scanner.nextLine(); // Consume the invalid input
            throw new InputMismatchException("Input valid command number!");
        } 

    }
}