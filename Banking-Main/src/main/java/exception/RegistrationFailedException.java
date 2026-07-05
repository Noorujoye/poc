package exception;

public class RegistrationFailedException extends BankingException {
    public RegistrationFailedException(String msg) {
        super(msg);
    }
    public RegistrationFailedException(
            String msg,
            Throwable cause) {

        super(msg, cause);
    }
}
