package util;

import auth.Credentials;
import handler.AdminDashBoard;
import handler.LoginHandler;
import handler.RegisterHandler;
import handler.UserDashBoard;
import repository.AccountRepository;
import repository.CredentialsRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import repository.impl.AccountRepositoryDB;
import repository.impl.CredentialRepositoryDB;
import repository.impl.CustomerRepositoryDB;
import repository.impl.TransactionRepositoryDB;
import service.AccountService;
import service.AuthService;
import service.CustomerService;
import service.TransactionService;

public class AppFactory {

    private static final CustomerRepository customerRepository = new CustomerRepositoryDB();
    private static final AccountRepository accountRepository = new AccountRepositoryDB();
    private static final CredentialsRepository credentialsRepository = new CredentialRepositoryDB();
    private static final TransactionRepository transactionRepository = new TransactionRepositoryDB();
    private static final AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();

    private static final TransactionService transactionService = new TransactionService(transactionRepository, accountRepository);
    private static final CustomerService customerService =
            new CustomerService(
            accountRepository,
            customerRepository,
            credentialsRepository,accountNumberGenerator
    );

    private static final AccountService accountService = new AccountService(accountRepository, transactionRepository, transactionService);
    private static final AuthService authService = new AuthService(credentialsRepository);
    private static final LoginHandler loginHandler = new LoginHandler(authService, accountService, transactionService);

    private static final RegisterHandler registerHandler =
            new RegisterHandler(customerService ,loginHandler);

    public static RegisterHandler registerHandler() {
        return registerHandler;
    }

    public static AccountService accountService() {
        return accountService;
    }

    public static TransactionService transactionService() {
        return transactionService;
    }

    public static LoginHandler loginHandler() {
        return loginHandler;
    }

    public static UserDashBoard userDashBoard(Credentials user) {
        return new UserDashBoard(accountService, user , transactionService);
    }
    public static AdminDashBoard adminDashBoard() {
        return new AdminDashBoard();
    }
}
