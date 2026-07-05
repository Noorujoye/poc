package service;

import auth.Credentials;
import database.DBConnection;
import dto.RegistrationRequest;
import exception.BankingException;
import exception.DatabaseException;
import exception.DuplicateUserException;
import exception.RegistrationFailedException;
import model.Account;
import model.Customer;
import model.enums.AccountStatus;
import model.enums.AccountType;
import repository.AccountRepository;
import repository.CredentialsRepository;
import repository.CustomerRepository;
import util.AccountNumberGenerator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static util.PasswordUtil.hashPassword;

public class CustomerService {
    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final CredentialsRepository credentialsRepo;
    private final AccountNumberGenerator accountNumberGenerator;

    public CustomerService(AccountRepository accountRepository,
                           CustomerRepository customerRepository,
                           CredentialsRepository credentialsRepo,
                           AccountNumberGenerator accountNumberGenerator) {
        this.accountRepo = accountRepository;
        this.customerRepo = customerRepository;
        this.credentialsRepo = credentialsRepo;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    public String registerCustomer(RegistrationRequest request) {

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Customer customer = request.getCustomer();
                String username = request.getUsername();
                String password = request.getPassword();

                // username validation
                Credentials existingUser = credentialsRepo.findByUsername(connection , username);

                if (existingUser != null) {
                    throw new DuplicateUserException("Username already exists");
                }

                // save customer
                Long customerId = customerRepo.save(connection, customer);
                customer.setCustomerId(customerId);

                // save account
                String accountNo = accountNumberGenerator.generateAccountNumber();
                Account account = new Account();
                account.setCustomerId(customerId);
                account.setAccountNo(accountNo);
                account.setType(AccountType.SAVINGS); // by default from me
                account.setBalance(new BigDecimal("0.00"));
                account.setStatus(AccountStatus.ACTIVE);
                accountRepo.save(connection, account);

                // save credentials
                Credentials credentials = new Credentials(username , hashPassword(password), customerId);
                credentialsRepo.save(connection , credentials);
                connection.commit();

                return accountNo;
            } catch (BankingException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new RegistrationFailedException("registration failed...",e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error: ",e);
        }
    }
}
