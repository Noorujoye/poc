package repository.impl;

import database.DBConnection;
import exception.DataAccessException;
import model.Account;
import model.enums.AccountStatus;
import model.enums.AccountType;
import repository.AccountRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepositoryDB implements AccountRepository {

    @Override
    public Long save(Connection connection , Account account) {
        String saveAccountToDB = """
                INSERT INTO accounts (account_no, customer_id, type, balance, status) 
                VALUES (? , ? , ? , ? ,?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(saveAccountToDB)) {
            ps.setString(1, account.getAccountNo());
            //    System.out.println("account Number is : " + account.getAccountNo());
            ps.setLong(2, account.getCustomerId());
            ps.setString(3, account.getType().name());
            ps.setBigDecimal(4, account.getBalance());
            ps.setString(5, account.getStatus().name());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Account creation failed...");
            }
            return account.getCustomerId();
        } catch (SQLException e) {
            throw new RuntimeException("DB Error: Could not save account", e);
        }
    }
    @Override
    public Account findAccountByCustomerId(Connection connection, Long customerId) {
        String findAccountById = "SELECT account_no, customer_id, type, balance, status FROM accounts WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(findAccountById)){
            ps.setLong(1 , customerId);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching account for customer: " + customerId, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Data corruption: Invalid Account Type in database" , e); // enum
        }
        return null;
    }

    @Override
    public void updateBalance(Connection connection, String accountNo, BigDecimal newBalance) {
        String updateAccountBalance = "UPDATE accounts SET balance = ? WHERE account_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateAccountBalance)) {
            ps.setBigDecimal(1, newBalance);
            ps.setString(2 ,accountNo);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Balance update failed...");
            }
        } catch (SQLException e) {
            throw new RuntimeException("failed to update balance...");
        }

    }
    @Override
    public Account findCustomerByAccountNumber(Connection connection, String toAccountNumber) {
        String getAccount = "SELECT * FROM accounts WHERE account_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(getAccount)) {
            ps.setString(1 , toAccountNumber);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Account account = new Account();
                    account.setAccountNo(rs.getString("account_no"));
                    account.setCustomerId(rs.getLong("customer_id"));
                    account.setType(AccountType.valueOf(rs.getString("type").toUpperCase()));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setStatus(AccountStatus.valueOf(rs.getString("status").toUpperCase()));
                    return account;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch account by account number");
        }
        return null;
    }

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountNo(rs.getString("account_no"));
        account.setCustomerId(rs.getLong("customer_id"));

        account.setType(AccountType.valueOf(rs.getString("type").toUpperCase()));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setStatus(AccountStatus.valueOf(rs.getString("status").toUpperCase()));
        return account;
    }
}
