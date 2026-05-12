package util;

import handler.LoginHandler;
import repository.AccountRepository;
import repository.CredentialsRepository;
import repository.CustomerRepository;
import repository.impl.AccountRepositoryDB;
import repository.impl.CredentialRepositoryDB;
import repository.impl.CustomerRepositoryDB;
import service.AccountService;
import service.AuthService;
import service.CustomerService;

public class AppFactory {
    private static final CustomerRepository customerRepository = new CustomerRepositoryDB();
    private static final AccountRepository accountRepository = new AccountRepositoryDB();
    private static final CredentialsRepository credentialsRepository = new CredentialRepositoryDB();
    private static final CustomerService customerService = new CustomerService(accountRepository , customerRepository , credentialsRepository);
    private static final AccountService accountService = new AccountService(accountRepository);
    private static final AuthService authService = new AuthService(credentialsRepository);
    private static final LoginHandler loginHandler = new LoginHandler(authService);

    public static AccountService accountService() {
        return accountService;
    }

    public static LoginHandler loginHandler() {
        return loginHandler;
    }
}
