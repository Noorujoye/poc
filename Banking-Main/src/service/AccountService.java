package service;

import database.DBConnection;
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
    public static synchronized String generateAccountNumber() {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        int seq = sequence.getAndIncrement();
        if (seq > 9999) {
            sequence.set(1000);
        }
        final String accountNumber = timeStamp + seq;
        return accountNumber;
    }
    public Account getCurrentUserAccount(Long customerId) {
        try (Connection connection = DBConnection.getConnection()){
            return accountRepo.findCustomerById(connection , customerId);
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
                Account account = accountRepo.findCustomerById(connection , customerId);
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
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            Account account = accountRepo.findCustomerById(connection , customerId);
            BigDecimal beforeWithdraw  = account.getBalance();
            BigDecimal afterWithdraw = beforeWithdraw.subtract(amount);
            accountRepo.updateBalance(connection , account.getAccountNo() , afterWithdraw);
            connection.commit();
            System.out.println("Withdrawn successfully...");
            System.out.println("before withdraw : ₹" + beforeWithdraw);
            System.out.println("current Balance : ₹" + afterWithdraw);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}