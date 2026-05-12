package handler;

import auth.Credentials;
import auth.SessionContext;
import model.Account;

import java.math.BigDecimal;
import static Input.ScannerUtil.*;
import static util.AppFactory.accountService;

public class LoginCard {

    private final Credentials user; // this is the instance which remember the user , no need to ask who logged in
    public LoginCard(Credentials user) {
        this.user = user;
    }
    public void displayMenu() { // now , no need to check for sessioncontext
        while (SessionContext.isLoggedIn()) { // means current user is logged in, if it has credentials
            System.out.println("""
                    Welcome, %s
                    1. View Balance
                    2. Deposit
                    3. Withdraw
                    4. Transfer Money
                    5. Transaction History
                    6. Logout
                    """.formatted(user.getUsername()) // inPlace of space , username
            );
            int option = getIntInput("what would u like to do: ");
            switch (option) {
                case 1 -> {
                    Account acc = SessionContext.getCurrentAccount();
                    if (acc != null) System.out.printf("Current Balance: ₹%.2f%n" , acc.getBalance());
                }
                case 2 -> {
                    BigDecimal depositAmount = amount("Enter amount to deposit: ");
                    accountService().deposit(user.getCustomerId() , depositAmount);

                    // refresh after transaction
                    Account updated = accountService().getCurrentUserAccount(user.getCustomerId());
                    SessionContext.login(SessionContext.getCurrentUser() , updated);
                }
                case 3 -> {
                    BigDecimal withdrawAmount = amount("Enter amount to withdraw: ");
                    accountService().withdrawBalance(user.getCustomerId(), withdrawAmount);

                    // refresh after transaction
                    Account updated = accountService().getCurrentUserAccount(user.getCustomerId());
                    SessionContext.login(SessionContext.getCurrentUser() , updated);
                }
                case 4 -> {
                    String toAccountNumber = getStringInput("Enter receiver account number : ");
                    BigDecimal amountToSend = amount("Enter amount to transfer");
                    accountService().transferMoney(user.getCustomerId(), toAccountNumber, amountToSend);

                    // refresh after transaction
                    Account updated = accountService().getCurrentUserAccount(user.getCustomerId());
                    SessionContext.login(SessionContext.getCurrentUser() , updated);
                }
                case 5 -> {
                    System.out.println("Transaction History...");
                }
                case 6 -> {
                    SessionContext.logout();
                    System.out.println("Logged out successfully...");
                    return;
                }
                default ->
                    System.out.println("Invalid choice...");
            }
        }
    }
}
