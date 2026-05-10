package handler;

import auth.Credentials;
import auth.SessionContext;
import service.AuthService;

import static Input.ScannerUtil.getStringInput;

public class LoginHandler {
    private final AuthService authService;

    public LoginHandler(AuthService authService) {
        this.authService = authService;
    }
    public void login() {
        System.out.println("\nLogin into your account");
        String username = getStringInput("username: ");
        String password = getStringInput("password: ");
        Credentials credentials = authService.login(username , password);

        if (credentials == null) {
            System.out.println("Invalid username or password...");
            return;
        }
        SessionContext.login(credentials);
        System.out.println("you are logged in...");
        LoginCard.userLoginCard(username);
    }
}
