public class Payment {

    private String paymentType;
    Order order;
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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
}