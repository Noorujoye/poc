package handler;

import auth.Credentials;
import auth.Session;
import exception.BankingException;
import model.Account;
import model.Transaction;
import service.AccountService;
import service.TransactionService;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Input.ScannerUtil.*;

public class LoginCard {

    private final Credentials user; // this is the instance which remember the user , no need to ask who logged in
    private final AccountService accountService;
    private final TransactionService transactionService;

    public LoginCard(Credentials credentials) {
        user = credentials;
        accountService = null;
        transactionService = null;
    }

    public LoginCard(Credentials user, AccountService accountService, TransactionService transactionService) {
        this.user = user;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void displayUserMenu() {
        while (Session.isLoggedIn()) {
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
                    Account acc = Session.getCurrentAccount();
                    if(acc == null) {
                        System.out.println("Account not available");
                        return;
                    } else {
                        System.out.println("Current Balance : " + acc.getBalance());
                    }
                }
                case 2 -> {
                    try {
                        BigDecimal depositAmount = amount("Enter amount to deposit: ");
                        accountService.deposit(user.getCustomerId(), depositAmount);

                        // refresh after transaction
                        Account updated = accountService.getCurrentUserAccount(user.getCustomerId());
                        Session.login(Session.getCurrentUser(), updated);
                        System.out.println("₹"+depositAmount + " : Deposit Successfully");
                    } catch (BankingException e) {
                        System.out.println("unable to deposit, try again later: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        BigDecimal withdrawAmount = amount("Enter amount to withdraw: ");
                        if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
                            System.out.println("please enter an amount greater than ₹0.");
                            return;
                        }
                        accountService.withdrawBalance(user.getCustomerId(), withdrawAmount);

                        // refresh after transaction
                        Account updated = accountService.getCurrentUserAccount(user.getCustomerId());
                        Session.login(Session.getCurrentUser(), updated);
                        System.out.println("Withdrawn successfully...");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Transaction Failed: " + e.getMessage());
                    } catch (BankingException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        String toAccountNumber = getStringInput("Enter receiver account number : ");
                        BigDecimal amountToSend = amount("Enter amount to transfer");
                        accountService.transferMoney(user.getCustomerId(), toAccountNumber, amountToSend);

                        // refresh after transaction
                        Account updated = accountService.getCurrentUserAccount(user.getCustomerId());
                        if (updated != null) {
                            Session.login(Session.getCurrentUser(), updated);
                        }
                        System.out.println("Transaction successful");
                    } catch (BankingException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        showTransactionHistory();
                    } catch (BankingException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 6 -> {
                    Session.logout();
                    System.out.println("Logged out successfully...");
                    return;
                }
                default ->
                    System.out.println("Invalid choice...");
            }
        }
    }

    private void showTransactionHistory() {
        int page = 1;
        int pageSize = 20;
        while (true) {
            List<Transaction> transactions = transactionService.getTransactionHistory(user.getCustomerId(), page, pageSize);
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }
            System.out.println("\n========== TRANSACTION HISTORY ==========");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
            for (Transaction ts : transactions) {
                System.out.println("-----------------------------------------");
                System.out.println("Txn ID      : " + ts.getTransactionId());
                System.out.println("Type        : " + ts.getType());
                System.out.printf("Amount      : ₹%.2f%n", ts.getAmount());
                System.out.printf("Balance     : ₹%.2f%n", ts.getBalanceAfterTransaction());
                System.out.println("Status      : " + ts.getStatus());
                System.out.println("Remarks     : " + ts.getRemarks());
                System.out.println("Date        : " + ts.getTimestamp().format(formatter));
            }
            if (transactions.size() < pageSize) {
                System.out.println("\nAll transaction loaded.");
                return;
            }
            System.out.println("""
                    1. Load More
                    0. Back
                    """);
            int choice = getIntInput("Choice: ");
            if (choice != 1) {
                return;
            }
            page++;
        }
    }


    public void displayAdminDashBoard() {

    }
}
