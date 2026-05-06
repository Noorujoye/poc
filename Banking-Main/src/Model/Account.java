package Model;

import Model.enums.AccountStatus;
import Model.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    String accountNo; // strings because accountNumber can have leading zeroes
    Long customerId;
    AccountType type;
    BigDecimal balance;
    AccountStatus status;
    LocalDateTime createdAt;
}
