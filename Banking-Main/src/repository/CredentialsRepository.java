package repository;

import Auth.Credentials;

public interface CredentialsRepository {
    void save(Credentials credentials);
    Credentials findByUsername(String username);
}
