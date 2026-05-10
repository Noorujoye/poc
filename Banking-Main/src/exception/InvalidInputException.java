package exception;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
