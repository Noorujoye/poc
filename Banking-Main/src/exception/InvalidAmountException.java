package exception;

public class InvalidAmountException extends Exception{
    public InvalidAmountException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
