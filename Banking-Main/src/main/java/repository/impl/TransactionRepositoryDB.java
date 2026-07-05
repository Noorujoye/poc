package repository.impl;

import exception.DatabaseException;
import exception.TransactionFailedException;
import model.Transaction;
import model.enums.TransactionStatus;
import model.enums.TransactionType;
import repository.TransactionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryDB implements TransactionRepository {
    @Override
    public void save(
            Connection connection,
            Transaction transaction) {
        String query = """
        INSERT INTO transactions(
            transaction_id,
            account_no,
            transaction_type,
            amount,
            balance_after,
            status,
            remarks
        )
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getAccountNo());
            ps.setString(3, transaction.getType().name());
            ps.setBigDecimal(4, transaction.getAmount());
            ps.setBigDecimal(5, transaction.getBalanceAfterTransaction());
            ps.setString(6, transaction.getStatus().name());
            ps.setString(7, transaction.getRemarks());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new TransactionFailedException("Transaction was not saved");
            }
        } catch (SQLException e) {
            throw new TransactionFailedException("Failed to save transaction", e);
        }
    }
    @Override
    public List<Transaction> findByAccountNo(Connection connection, String accountNo , int limit , int offset) {
        String query = """
                SELECT *FROM transactions WHERE account_no = ? ORDER BY created_at DESC LIMIT ? OFFSET ?
                """;
        List<Transaction> transactionsList = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1 , accountNo);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getLong("id"));
                    transaction.setTransactionId(rs.getString("transaction_id"));
                    transaction.setAccountNo(rs.getString("account_no"));
                    transaction.setAmount(rs.getBigDecimal("amount"));
                    transaction.setBalanceAfterTransaction(rs.getBigDecimal("balance_after"));
                    transaction.setType(TransactionType.valueOf(rs.getString("transaction_type")));
                    transaction.setStatus(TransactionStatus.valueOf(rs.getString("status")));
                    transaction.setRemarks(rs.getString("remarks"));
                    transaction.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
                    transactionsList.add(transaction);
                }
            }
            return transactionsList;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch transactions for account: " + accountNo, e);
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Invalid transaction type or status in database for account: " + accountNo, e);
        }
    }
}
