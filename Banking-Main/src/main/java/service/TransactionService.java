package service;

import database.DBConnection;
import exception.AccountNotFoundException;
import exception.BankingException;
import exception.DatabaseException;
import exception.EmptyException;
import model.Account;
import model.Transaction;
import model.enums.TransactionStatus;
import model.enums.TransactionType;
import repository.AccountRepository;
import repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public  class TransactionService {
    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository transactionRepo, AccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }
    public Transaction createTransaction(
            Account account,
            BigDecimal amount,
            BigDecimal balanceAfter,
            TransactionType type,
            String remarks
    ) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setAccountNo(account.getAccountNo());
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balanceAfter);
        transaction.setType(type);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setRemarks(remarks);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }
    public List<Transaction> getTransactionHistory(Long customerId, int pageNumber, int pageSize) {
        try (Connection connection = DBConnection.getConnection()){
            try {
                Account account = accountRepo.findAccountByCustomerId(connection, customerId);
                if (account == null) {
                    throw new AccountNotFoundException("Account not found");
                }
                int offset = (pageNumber -1) * pageSize;
                List<Transaction> transactionList = transactionRepo.findByAccountNo(connection, account.getAccountNo() , pageSize , offset);
                if (transactionList.isEmpty()) {
                    throw new EmptyException("No history available, make transactions");
                }
                return transactionList;
            } catch (BankingException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new BankingException("technical issue while fetching history",e);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new DatabaseException("unable to connect to database",e);
        }
    }
    public String generateTransactionId() {
        return "TXN-" + UUID.randomUUID()
                .toString()
                .substring(0 , 10)
                .toUpperCase();
    }
}
