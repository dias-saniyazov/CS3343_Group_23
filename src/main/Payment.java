public class Payment {

    private String paymentType;
   

    // Constructor
    public Payment(String paymentType) {
        this.paymentType = paymentType;
    }

    // Getters and setters
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    @Override
    public String toString() {
        return "Payment{" +
                "paymentType='" + paymentType + '\'' +
                '}';
    }
    public boolean makePaymentWithBalance(Member member, float totalCost) {
        DBController dbController = DBController.getInstance();
        if (member.getBalance() >= totalCost) {
            dbController.changeBalance(member, -totalCost);
            System.out.println("Payment successful! Your new balance is: " + (member.getBalance() - totalCost));
            return true;
        } else {
            System.out.println("Insufficient balance! Please top up your account.");
            return false;
        }
    }
    public boolean makePaymentwithPaymentType(Member member, float amount) {
        System.out.println("Payment proceeding....");
        DBController dbController = DBController.getInstance();
        dbController.changeBalance(member, amount);
        System.out.println("Payment successful! ");
        return true;
    }
}