package Auth;

public class Credentials {
    String username;
    String passwordHash;
    Long customerId;

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
