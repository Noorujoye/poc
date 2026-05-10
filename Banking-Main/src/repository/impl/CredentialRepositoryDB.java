package repository.impl;

import auth.Credentials;
import model.Customer;
import repository.CredentialsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialRepositoryDB implements CredentialsRepository {

    @Override
    public void save(Connection connection, Credentials credentials) {
        String saveCredentials = """ 
                                    INSERT INTO credentials
                                    (username , password_hash , customer_id)
                                    VALUes (? , ? , ?)
                                    """;
        try(PreparedStatement ps = connection.prepareStatement(saveCredentials)) {
            ps.setString(1 , credentials.getUsername());
            ps.setString(2 , credentials.getPasswordHash());
            ps.setLong(3 , credentials.getCustomerId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Credentials creation failed...");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save credentials",e);
        }
    }

    @Override
    public Credentials findByUsername(Connection connection, String username) {
        String findByUsername = " SELECT * FROM credentials WHERE username = ? ";
        try (PreparedStatement ps = connection.prepareStatement(findByUsername)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Credentials(
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getLong("customer_id")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to fetch credentials", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
