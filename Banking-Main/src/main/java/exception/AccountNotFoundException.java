package exception;

public class AccountNotFoundException extends BankingException{
    public AccountNotFoundException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
