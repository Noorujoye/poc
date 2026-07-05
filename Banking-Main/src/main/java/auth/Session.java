package auth;

import model.Account;

final public class Session {
    private static Credentials currentUser;
    private static Account currentAccount;
    private Session() {

    }
    public static void login(Credentials credentials , Account account) {
        currentUser = credentials;
        currentAccount = account;
    }
    public static void login(Credentials credentials) {
        currentUser = credentials;
    }
    public static void logout() {
        currentUser = null;
        currentAccount = null;
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
