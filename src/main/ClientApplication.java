import java.util.List;

class ClientApplication extends Application {
    @Override
    String login(String username, String password) {
        DBController db = new DBController();
        String result = db.validateMemberCredentials(username, password);
        return result.equals("valid") ? "Client logged in" : "Invalid credentials";
    }

    void register(String username, String password) {
        DBController db = new DBController();
        if (!db.checkIfMemberExist(username)) {
            //Member newMember = new Member(username, password, Standard.getInstance());
            db.createMember(username, password);
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    List<MenuItem> viewMenu() {
        DBController db = new DBController();
        return db.viewMenu();
    }
}