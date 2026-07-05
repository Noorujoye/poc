package exception;

public class DuplicateUserException extends BankingException {
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
