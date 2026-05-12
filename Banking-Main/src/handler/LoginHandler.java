package handler;

import auth.Credentials;
import auth.SessionContext;
import com.mysql.cj.log.Log;
import model.Account;
import service.AuthService;

import static Input.ScannerUtil.amount;
import static Input.ScannerUtil.getStringInput;
import static util.AppFactory.accountService;

public class LoginHandler {
    private final AuthService authService;

    public LoginHandler(AuthService authService) {
        this.authService = authService;
    }
    public void login() {
        System.out.println("Login into your account");
        String username = getStringInput("username: ");
        String password = getStringInput("password: ");
        Credentials credentials = authService.login(username , password);

        if (credentials == null) {
            System.out.println("Invalid username or password...");
            return;
        }
        Account account = accountService().getCurrentUserAccount(credentials.getCustomerId());

        SessionContext.login(credentials , account);
        System.out.println("you are logged in...");
        LoginCard loginCard = new LoginCard(credentials);
        loginCard.displayMenu();
    }
}
