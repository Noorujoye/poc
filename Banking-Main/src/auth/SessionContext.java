package auth;

import model.Account;

public class SessionContext {
    private static Credentials currentUser;
    private static Account currentAccount;
    private SessionContext() {

    }
    public static void login(Credentials credentials , Account account) {
        currentUser = credentials;
        currentAccount = account;
    }
    public static void logout() {
        currentUser = null;
    }
    public static Credentials getCurrentUser() {
        return currentUser;
    }
    public static Account getCurrentAccount() {
        return currentAccount;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
