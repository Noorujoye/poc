package exception;

public class CustomerCreationException extends BankingException {
    public CustomerCreationException(String failedToSaveCustomer) {
        super(failedToSaveCustomer);

    }
}
