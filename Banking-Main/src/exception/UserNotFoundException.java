package exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}
