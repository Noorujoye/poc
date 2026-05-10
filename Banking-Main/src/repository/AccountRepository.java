package repository;

import model.Account;

import java.math.BigDecimal;
import java.sql.Connection;

public interface AccountRepository {
    Long save(Connection connection , Account account);
    Account findCustomerById(Connection connection , Long customerId);
    void updateBalance(Connection connection , String accountNo ,  BigDecimal newBalance);

}
