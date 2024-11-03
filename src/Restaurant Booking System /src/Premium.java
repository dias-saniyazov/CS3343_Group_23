class Premium implements MembershipState {
    private static Premium instance = new Premium();

    private Premium() {}

    public static Premium getInstance() {
        return instance;
    }

    @Override
    public int discount(int minutes) {
        return minutes * 2; // Example discount logic
    }
}