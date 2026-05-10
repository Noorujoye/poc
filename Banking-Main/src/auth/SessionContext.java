package auth;

public class SessionContext {
    private static Credentials currentUser;
    private SessionContext() {

    }
    public static void login(Credentials credentials) {
        currentUser = credentials;
    }
    public static void logout() {
        currentUser = null;
    }
    public static Credentials getCurrentUser() {
        return currentUser;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
