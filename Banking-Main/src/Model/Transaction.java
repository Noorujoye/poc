package Model;

import Model.enums.AccountStatus;
import Model.enums.AccountType;
import Model.enums.TransactionStatus;
import Model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    Long txnId;
    String fromAccount;
    String toAccount;
    TransactionType type;
    BigDecimal amount;
    TransactionStatus status;
    LocalDateTime timestamp;
}
