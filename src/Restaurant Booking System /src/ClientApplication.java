import java.util.ArrayList;

class ClientApplication extends Application {
    @Override
    String login(String username, String password) {
        DBController db = new DBController();
        String result = db.validateMemberCredentials(username, password);
        return result.equals("valid") ? "Client logged in" : "Invalid credentials";
    }

    void register(String username, String password) {
        DBController db = new DBController();
        if (!db.checkMemberExist(username)) {
            Member newMember = new Member(username, password, Standard.getInstance());
            db.createMember(newMember);
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    ArrayList<MenuItem> viewMenu() {
        DBController db = new DBController();
        return db.getAvailableDisplayCount();
    }
}