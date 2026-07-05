package repository;

import model.Account;

import java.math.BigDecimal;
import java.sql.Connection;

public interface AccountRepository {
    Long save(Connection connection , Account account);
    Account findAccountByCustomerId(Connection connection , Long customerId);
    void updateBalance(Connection connection , String accountNo ,  BigDecimal newBalance);
    Account findCustomerByAccountNumber(Connection connection , String toAccountNumber);
}
