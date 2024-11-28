package main.java.app;

public class ApplicationFactory {
    private static ApplicationFactory instance;
    
    private ApplicationFactory() {
    }

    public static ApplicationFactory getInstance() {
        if (instance == null) {
            instance = new ApplicationFactory();
        }
        return instance;
    }
    public Application createAndGetApplication(String applicationType) {
        switch (applicationType) {
            case "Client":
                return new ClientApplication();
            case "Admin":
                return new AdminApplication();
            default:
                return null;
        }
    }
    
}