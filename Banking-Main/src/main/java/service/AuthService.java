package service;

import auth.Credentials;
import database.DBConnection;
import exception.AuthenticationFailedException;
import exception.DatabaseException;
import repository.CredentialsRepository;
import util.PasswordUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {
    private final CredentialsRepository credentialsRepository;
    public AuthService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }
    public Credentials login(String username , String password) {
        try(Connection connection = DBConnection.getConnection()) {

                Credentials credentials = credentialsRepository.findByUsername(connection, username);
                if (credentials == null) {
                    throw new AuthenticationFailedException("Invalid username or password");
                }
                boolean valid = PasswordUtil.verifyPassword(password, credentials.getPasswordHash());
                if (!valid) {
                    throw new AuthenticationFailedException("Invalid username or password");
                }
                return credentials;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to login right now", e);
        }
    }
    public boolean verifiyAccount(String accountNumber) {
        return false;
    }
}
