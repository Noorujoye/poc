package model;

import model.enums.TransactionStatus;
import model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;
    private String transactionId;
    private String accountNo;
    private String referenceAccount;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private TransactionType type;
    private TransactionStatus status;
    private String remarks;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String transactionId,
                       String accountNo,
                       BigDecimal amount,
                       BigDecimal balanceAfterTransaction,
                       TransactionType type,
                       TransactionStatus status,
                       String remarks,
                       LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountNo = accountNo;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.type = type;
        this.status = status;
        this.remarks = remarks;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getReferenceAccount() {
        return referenceAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getRemarks() {
        return remarks;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setReferenceAccount(String referenceAccount) {
        this.referenceAccount = referenceAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
