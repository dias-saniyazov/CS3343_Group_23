class LoginController {
    Member isLoggedIn() {
        return new Member();
    }

    String generateToken() {
        return "token";
    }
}