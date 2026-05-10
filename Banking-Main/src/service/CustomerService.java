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

import static handler.LoginCard.userLoginCard;

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

    public void registerCustomer(Customer customer, String username, String password) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                // duplicate username check , before saving
                Credentials existingUser = credentialsRepo.findByUsername(connection, username);

                if (existingUser != null) {
                    throw new DuplicateUserException("Username already exists...");
                }
                // now the customer is saved to db
                Long customerId = customerRepo.save(connection, customer);

                // credentials creation
                Credentials credentials = new Credentials(username, PasswordUtil.hashPassword(password), customerId);
                credentialsRepo.save(connection, credentials);

                // create account
                Account account = new Account();
                account.setCustomerId(customerId);
                account.setAccountNo(AccountService.generateAccountNumber());
                account.setType(AccountType.SAVINGS); // by default from me
                account.setBalance(new BigDecimal("0.00"));
                account.setStatus(AccountStatus.ACTIVE);
                accountRepo.save(connection, account);
                customer.setKycStatus(KycStatus.VERIFIED);
                connection.commit();
                System.out.println("Account Created Successfully...");
                System.out.println("Your Account Number : " + account.getAccountNo());
                userLoginCard(username);
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("Registration failed...", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Registration failed...");
        }
    }
}
