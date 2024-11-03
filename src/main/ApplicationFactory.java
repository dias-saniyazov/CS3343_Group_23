class ApplicationFactory {
    public Application createAndGetApplication(String applicationType) {
        switch (applicationType) {
            case "Client":
                return new ClientApplication();
            case "Member":
                return new MemberApplication();
            case "Admin":
                return new AdminApplication();
            default:
                return null;
        }
    }
}