package repository;

import auth.Credentials;
import java.sql.Connection;

public interface CredentialsRepository {
    void save(Connection connection, Credentials credentials);
    Credentials findByUsername(Connection connection, String username);
}
