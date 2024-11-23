import java.util.List;

class AdminApplication extends Application {
    @Override
    String login(String username, String password) {
        // Admin login logic
        return "Admin logged in";
    }

    boolean createMenuItem(DBController db, String name, String description, float price, List<String> tags) {
        //DBController db = new DBController();
        return db.addNewMenuItem(name, description, price, tags);
    }

}