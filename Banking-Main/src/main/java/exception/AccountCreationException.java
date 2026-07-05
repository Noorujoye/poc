package exception;

public class AccountCreationException extends BankingException {
    public AccountCreationException(String accountCreationFailed) {
        super(accountCreationFailed);
    }
}
