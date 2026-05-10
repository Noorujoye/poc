package handler;

import auth.Credentials;
import auth.SessionContext;
import model.Account;

import java.math.BigDecimal;

import static Input.ScannerUtil.*;
import static util.AppFactory.accountService;

public class LoginCard {
    public static void userLoginCard(String username) {
        while (SessionContext.isLoggedIn()) { // means current user is logged in, if it has credentials
            System.out.println("""
                    Welcome, %s
                    1. View Balance
                    2. Deposit
                    3. Withdraw
                    4. Transfer Money
                    5. Transaction History
                    6. Logout
                    """.formatted(username) // inPlace of space , username
            );
            int option = getIntInput("what would u like to do: ");
            switch (option) {
                case 1:
                    Credentials currentUser = SessionContext.getCurrentUser();
                    Account account = accountService().getCurrentUserAccount(currentUser.getCustomerId());
                    if (account == null) {
                        System.out.println("Account not found...");
                        break;
                    }
                    System.out.println("Current Balance : ₹" + account.getBalance());
                    break;
                case 2:
                    BigDecimal depositAmount = amount("Enter amount to deposit: ");
                    Credentials depositUser = SessionContext.getCurrentUser();
                    accountService().deposit(depositUser.getCustomerId(), depositAmount);
                    break;
                case 3:
                    BigDecimal withdrawAmount = amount("Enter amount to withdraw: ");
                    Credentials withdrawUser = SessionContext.getCurrentUser();
                    accountService().withdrawBalance(withdrawUser.getCustomerId() , withdrawAmount);
                    break;
                case 4:
                    System.out.println("Transfer comming soon...");
                    break;
                case 5:
                    System.out.println("Transaction History...");
                case 6:
                    SessionContext.logout();
                    System.out.println("Logged out successfully...");
                    return;
                default:
                    System.out.println("Invalid choice...");
            }
        }
    }
}
