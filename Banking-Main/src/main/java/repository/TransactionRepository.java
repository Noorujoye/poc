package repository;

import model.Transaction;

import java.sql.Connection;
import java.util.List;

public interface TransactionRepository {
    void save(Connection connection , Transaction transaction);
    List<Transaction> findByAccountNo(
            Connection connection,
            String accountNo,
            int limit,
            int offset
    );
}
