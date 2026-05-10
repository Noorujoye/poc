package exception;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
