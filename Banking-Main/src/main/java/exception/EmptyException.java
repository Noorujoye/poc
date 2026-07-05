package exception;

public class EmptyException extends BankingException{
    public EmptyException(String msg) {
        super(msg);
    }
}
