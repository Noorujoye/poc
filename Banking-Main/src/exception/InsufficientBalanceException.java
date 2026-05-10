package exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
