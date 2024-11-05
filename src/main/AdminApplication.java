import java.util.List;

class AdminApplication extends Application {
    @Override
    String login(String username, String password) {
        // Admin login logic
        return "Admin logged in";
    }

    boolean createMenuItem(String name, String description, float price, List<Tag> tags) {
        DBController db = new DBController();
        return db.addNewMenuItem(name, description, price, tags);
    }

}