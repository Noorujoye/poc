package exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
