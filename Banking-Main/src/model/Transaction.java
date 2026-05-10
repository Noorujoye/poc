package model;

import model.enums.TransactionStatus;
import model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String txnId;
    private String fromAccount;
    private String toAccount;
    private TransactionType type;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;
}
