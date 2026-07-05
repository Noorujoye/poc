package model;

import model.enums.AccountStatus;
import model.enums.AccountType;
import java.math.BigDecimal;

public class Account {

    private String accountNo;// strings because accountNumber can have leading zeroes
    private Long customerId;
    private AccountType type;
    private BigDecimal balance;
    private AccountStatus status;

    public Account(){}

    public Account(String accountNo,
                   Long customerId,
                   AccountType type) {
        this.accountNo = accountNo;
        this.customerId = customerId;
        this.type = type;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
