package exception;

public class TransactionFailedException extends BankingException{
    public TransactionFailedException(String msg) {
        super(msg);
    }
    public TransactionFailedException(String msg, Throwable cause) {
        super(msg , cause);
    }
}
