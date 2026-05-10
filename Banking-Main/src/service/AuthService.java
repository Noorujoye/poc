package service;

import auth.Credentials;
import database.DBConnection;
import exception.AuthenticationFailedException;
import repository.CredentialsRepository;
import repository.impl.CredentialRepositoryDB;
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
            Credentials credentials = credentialsRepository.findByUsername(connection , username);

            if (credentials == null) {
                return null;
            }
            boolean valid = PasswordUtil.verifyPassword(password , credentials.getPasswordHash());
            if (!valid) return null;
            return credentials;
        } catch (SQLException e) {
            throw new AuthenticationFailedException("Enter correct username or password");
        }
    }
}
