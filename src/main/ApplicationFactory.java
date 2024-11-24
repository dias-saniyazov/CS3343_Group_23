public class ApplicationFactory {
    public static Application createAndGetApplication(String applicationType) {
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