package exception;

public class InsufficientBalanceException extends BankingException{
    public InsufficientBalanceException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
