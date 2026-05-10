package auth;

public class Credentials {
    private String username;
    private String passwordHash;
    private Long customerId;

    public Credentials(String username,
                       String passwordHash,
                       Long customerId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public Long getCustomerId() {
        return customerId;
    }
}
