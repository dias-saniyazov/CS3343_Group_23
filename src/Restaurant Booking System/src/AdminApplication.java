class AdminApplication extends Application {
    @Override
    String login(String username, String password) {
        // Admin login logic
        return "Admin logged in";
    }

    boolean createMenuItem(MenuItem item) {
        DBController db = new DBController();
        return db.addNewMenuItem(item);
    }

    boolean removeVehicle(MenuItem item) {
        DBController db = new DBController();
        return db.removeNewMenuItem(item);
    }
}