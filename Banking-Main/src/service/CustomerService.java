package service;

import auth.Credentials;
import database.DBConnection;
import exception.DuplicateUserException;
import model.Account;
import model.Customer;
import model.enums.AccountStatus;
import model.enums.AccountType;
import model.enums.KycStatus;
import repository.AccountRepository;
import repository.CredentialsRepository;
import repository.CustomerRepository;
import util.PasswordUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomerService {
    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final CredentialsRepository credentialsRepo;

    public CustomerService(AccountRepository accountRepository,
                           CustomerRepository customerRepository,
                           CredentialsRepository credentialsRepo) {
        this.accountRepo = accountRepository;
        this.customerRepo = customerRepository;
        this.credentialsRepo = credentialsRepo;
    }

    public String createInitialCustomerAndAccount(Customer customer) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                // save customer and getID
                Long customerId = customerRepo.save(connection, customer);
                customer.setCustomerId(customerId);

                // create account
                String accountNo = AccountService.generateAccountNumber();
                Account account = new Account();
                account.setCustomerId(customerId);
                account.setAccountNo(accountNo);
                account.setType(AccountType.SAVINGS); // by default from me
                account.setBalance(new BigDecimal("0.00"));
                account.setStatus(AccountStatus.ACTIVE);
                accountRepo.save(connection, account);
                connection.commit();
                return accountNo;
            } catch (Exception e) {
                connection.rollback();
                System.out.println("registration failed...");
            }
        } catch (SQLException e) {
            System.out.println("Database error during registration");
        }
        return null;
    }

    public void saveCredentials(Long customerId, String username , String password) {
        try (Connection connection = DBConnection.getConnection()) {
            if (credentialsRepo.findByUsername(connection , username) != null) {
                System.out.println("username already taken! ");
            }
            // credentials creation
             Credentials credentials = new Credentials(username, PasswordUtil.hashPassword(password), customerId);
             credentialsRepo.save(connection, credentials);
        } catch (SQLException e) {
            System.out.println("Failed to save credentials" + e);
        }
    }
}
