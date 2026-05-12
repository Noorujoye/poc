package service;

import database.DBConnection;
import exception.InsufficientBalanceException;
import model.Account;
import repository.AccountRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public  class AccountService {
    private static  AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepo = accountRepository;

    }

    private static final AtomicInteger sequence = new AtomicInteger(1000); // thread safe h
    public static synchronized String generateAccountNumber() { // Atomic integer ensure no two threads get the same number ,
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
//        int seq = sequence.getAndIncrement(); // updateAndGet()
//        if (seq > 9999) {
//            sequence.set(1000);
//        }
        // it is more secure
        int seq = sequence.updateAndGet(s -> (s >= 9999) ? 1000 : s + 1); // this handles the reset logic automatically, prevents from multiple thread to get the same values
        final String accountNumber = timeStamp + seq;
        return accountNumber;
    }
    public Account getCurrentUserAccount(Long customerId) {
        try (Connection connection = DBConnection.getConnection()){
            return accountRepo.findAccountByCustomerId(connection , customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deposit(Long customerId , BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid deposit amount...");
        }
        try (Connection connection = DBConnection.getConnection()){
            connection.setAutoCommit(false);
            try {
                Account account = accountRepo.findAccountByCustomerId(connection , customerId);
                if (account == null) {
                    throw new RuntimeException("Account not found...");
                }
                BigDecimal currentBalance = account.getBalance();
                BigDecimal updateBalance = account.getBalance().add(amount);
                accountRepo.updateBalance(connection , account.getAccountNo() , updateBalance);
                connection.commit();
                System.out.println("Deposit successfully...");
                System.out.println("before deposit : ₹" + currentBalance);
                System.out.println("Updated Balance : ₹" + updateBalance);
            } catch (Exception e) {
                throw new RuntimeException("Deposit failed...",e);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Deposit failed...", e);
        }
    }
    public void withdrawBalance(Long customerId ,BigDecimal amount) {

        try (Connection connection = DBConnection.getConnection()) {
            Account account = accountRepo.findAccountByCustomerId(connection , customerId);
            if (account == null) {
                System.out.println("Account not found...");
                return;
            }
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid amount. Please enter more than 0.");
                return;
            }
            if (account.getBalance().compareTo(amount) < 0) {
                System.out.println("Insufficient funds!, Your balance is : " + account.getBalance());
                return;
            }
            connection.setAutoCommit(false);
            try {
                BigDecimal beforeWithdraw = account.getBalance();
                BigDecimal afterWithdraw = beforeWithdraw.subtract(amount);
                accountRepo.updateBalance(connection, account.getAccountNo(), afterWithdraw);
                connection.commit();
                System.out.println("Withdrawn successfully...");
                System.out.println("before withdraw : ₹" + beforeWithdraw);
                System.out.println("current Balance : ₹" + afterWithdraw);
            } catch (Exception e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transferMoney(Long customerId , String toAccountNumber , BigDecimal amount) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            Account senderAccount = accountRepo.findAccountByCustomerId(connection , customerId);
            Account recieverAccount = accountRepo.findCustomerByAccountNumber(connection , toAccountNumber);

//            if (recieverAccount == null) {
//                throw new RuntimeException("Receiver Account not found...");
//            }
//
//            if (senderAccount.getBalance().compareTo(amount) < 0) {
//                    throw new InsufficientBalanceException("Insufficient balance in your account.");
//            }
            System.out.println("current balance : " + senderAccount.getBalance());
            BigDecimal newBalanceOfSender = senderAccount.getBalance().subtract(amount);
            BigDecimal newBalanceOfReceiver = recieverAccount.getBalance().add(amount);
            accountRepo.updateBalance(connection , senderAccount.getAccountNo() , newBalanceOfSender);
            accountRepo.updateBalance(connection , recieverAccount.getAccountNo(), newBalanceOfReceiver);
            connection.commit();
            System.out.println("₹ " + amount + " transferred to " + recieverAccount.getAccountNo());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getCurrentAccountBalance(Long customerId) {
        try (Connection connection = DBConnection.getConnection()) {
            Account account = accountRepo.findAccountByCustomerId(connection , customerId);
            return account.getBalance();
        } catch (SQLException e) {
            throw new RuntimeException("DB Error...");
        }
    }
}