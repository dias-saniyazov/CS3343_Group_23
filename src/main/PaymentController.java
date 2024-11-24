import java.util.Date;
import java.util.Scanner;

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
        if(payment.makePaymentWithBalance(member, totalCost) == false) {
            return false;
        }
        // Create the order
        Order order = new Order(member, cart, payment);

        // Save the order to the database via DBController
        boolean transactionStatus = dbController.createTransaction(order);

        if (transactionStatus) {
            return true;
        } else {
            return false;
        }
    }
    public boolean topUpBalance(Member member, float amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Apple Pay");
        System.out.println("3. Google Pay");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        scanner.close();
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

        System.out.println("Balance topped up successfully! Your new balance is: " + (member.getBalance() + amount));
        return true;
    }
}