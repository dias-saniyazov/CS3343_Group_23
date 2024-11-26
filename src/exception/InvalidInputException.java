package exception;

// InvalidInputException.java
public class InvalidInputException extends Exception {
    // Constructor with a default message
    public InvalidInputException() {
        super("Invalid input provided");
    }

    // Constructor that accepts a custom message
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(int maxAmount) {
        super("Invalid input. Please enter a number between 1 and " + maxAmount);
    }
}