package exception;

public class DatabaseException extends BankingException {
    public DatabaseException(String msg, Throwable cause) {
        super(msg , cause);
    }
    public DatabaseException(String msg) {
        super(msg);
    }
}
