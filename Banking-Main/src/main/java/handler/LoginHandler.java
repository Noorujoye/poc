package handler;

import auth.Credentials;
import auth.Session;
import exception.AccountNotFoundException;
import exception.AuthenticationFailedException;
import exception.BankingException;
import model.Account;
import service.AccountService;
import service.AuthService;
import service.TransactionService;
import util.AppFactory;

import static Input.ScannerUtil.getStringInput;
import static Input.ScannerUtil.inputPassword;

public class LoginHandler {
    private final AuthService authService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public LoginHandler(AuthService authService, AccountService accountService, TransactionService transactionService) {
        this.authService = authService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void loginAsUser() {

        System.out.println("Login into your account");

        String username = getStringInput("username: ");
        String password = inputPassword("password: ");

        try {
            Credentials credentials = authService.login(username, password);
            Account account = accountService.getCurrentUserAccount(credentials.getCustomerId());
            if (account == null) {
                throw new AccountNotFoundException("Account Not found");
            }
            Session.login(credentials, account);
            System.out.println("\nyou are logged in...");
            UserDashBoard userDashBoard = AppFactory.userDashBoard(credentials);
            userDashBoard.displayUserMenu();
        } catch (AuthenticationFailedException e) {
            System.out.println(e.getMessage());
        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loginAsAdmin() {
        System.out.println("""
                                             Login as ADMIN
                        Please enter your credentials to login into your dashboard.
                        """
        );
        String username = getStringInput("username: ");
        String password = inputPassword("password: ");

        try {
            Credentials credentials = authService.login(username , password);
            Session.login(credentials);
            AdminDashBoard adminDashBoard = AppFactory.adminDashBoard();
            adminDashBoard.displayAdminDash();
        } catch (BankingException e) {
            System.out.println(e.getMessage());
        }
    }
}
