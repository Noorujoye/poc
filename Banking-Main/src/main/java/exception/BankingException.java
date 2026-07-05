package exception;

public class BankingException extends RuntimeException {

    public BankingException(String msg) {
        super(msg);
    }
    public BankingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
