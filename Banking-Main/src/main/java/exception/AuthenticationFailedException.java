package exception;

public class AuthenticationFailedException extends BankingException {
    public AuthenticationFailedException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
