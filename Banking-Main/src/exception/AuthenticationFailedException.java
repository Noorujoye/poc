package exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
