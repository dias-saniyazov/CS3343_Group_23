import java.util.Date;

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

        // Create a payment instance
        Payment payment = new Payment("Credit Card"); // Assuming default payment type is Credit Card

        // Discount logic based on membership state
        MembershipState state = member.getMemberState(); // Assuming 10 minutes for discount calculation
        if(MembershipState.PREMIUM.equals(state)) {
            totalCost = totalCost * 0.9f; // 10% discount for premium members
            System.out.println("Discount applied: 10%");
        } 
        
        System.out.println("Final cost after discount: " + totalCost);

        // Create the order
        Order order = new Order(member, cart, payment);

        // Save the order to the database via DBController
        boolean transactionStatus = dbController.createTransaction(order);

        if (transactionStatus) {
            System.out.println("Order created successfully. Payment processed.");
            return true;
        } else {
            System.out.println("Transaction failed. Please try again.");
            return false;
        }
    }
}