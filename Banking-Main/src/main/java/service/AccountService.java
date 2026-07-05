package service;

import database.DBConnection;
import exception.AccountNotFoundException;
import exception.BankingException;
import exception.DatabaseException;
import exception.InsufficientBalanceException;
import model.Account;
import model.Transaction;
import model.enums.TransactionType;
import repository.AccountRepository;
import repository.TransactionRepository;
import util.AmountValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public  class AccountService {
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;
    private final TransactionService transactionService;


    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepo,
                          TransactionService transactionService) {
        this.accountRepo = accountRepository;
        this.transactionRepo = transactionRepo;
        this.transactionService = transactionService;
    }

    public BigDecimal deposit(Long customerId, BigDecimal amount) {
        AmountValidator.validateAmount(amount);
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Account account = accountRepo.findAccountByCustomerId(connection, customerId);
                if (account == null) {
                    throw new AccountNotFoundException("Account not found");
                }
                BigDecimal newBalance = account.getBalance().add(amount);

                accountRepo.updateBalance(connection, account.getAccountNo(), newBalance);
                saveTransaction(
                        connection,
                        account,
                        amount,
                        newBalance,
                        TransactionType.CREDIT,
                        "Cash Deposit"
                );
                connection.commit();
                return newBalance;
            } catch (BankingException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new BankingException("Deposit failed...", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database", e);
        }
    }

    public void withdrawBalance(Long customerId, BigDecimal amount) {
        AmountValidator.validateAmount(amount);
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Account account = accountRepo.findAccountByCustomerId(connection, customerId);
                if (account == null) {
                    throw new AccountNotFoundException("Account Not found");
                } else if (account.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientBalanceException("Insufficient balance");
                }
                BigDecimal beforeWithdraw = account.getBalance();
                BigDecimal afterWithdraw = beforeWithdraw.subtract(amount);
                accountRepo.updateBalance(connection, account.getAccountNo(), afterWithdraw);
                saveTransaction(
                        connection,
                        account,
                        amount,
                        afterWithdraw,
                        TransactionType.DEBIT,
                        "Cash Withdrawal"
                );
                connection.commit();
            } catch (BankingException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new BankingException("Withdraw failed", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database", e);
        }
    }

    public void transferMoney(Long customerId, String toAccountNumber, BigDecimal amount) {
        AmountValidator.validateAmount(amount);

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Account senderAccount = accountRepo.findAccountByCustomerId(connection, customerId);
                Account recieverAccount = accountRepo.findCustomerByAccountNumber(connection, toAccountNumber);

                if (recieverAccount == null) {
                    throw new AccountNotFoundException("Receiver Account not found");
                } else if (senderAccount == null) {
                    throw new AccountNotFoundException("Sender account not found");
                } else if (senderAccount.getAccountNo().equals(recieverAccount.getAccountNo())) {
                    throw new BankingException("Cannot transfer to same account");
                } else if (senderAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientBalanceException("Insufficient balance in your account.");
                }
                BigDecimal newBalanceOfSender = senderAccount.getBalance().subtract(amount);
                BigDecimal newBalanceOfReceiver = recieverAccount.getBalance().add(amount);
                accountRepo.updateBalance(connection, senderAccount.getAccountNo(), newBalanceOfSender);
                accountRepo.updateBalance(connection, recieverAccount.getAccountNo(), newBalanceOfReceiver);
                // sender transaction create + save
                saveTransaction(
                        connection,
                        senderAccount,
                        amount,
                        newBalanceOfSender,
                        TransactionType.DEBIT,
                        "Transfer to " + recieverAccount.getAccountNo()
                );
                // receiver transaction create + save
                saveTransaction(
                        connection,
                        recieverAccount,
                        amount,
                        newBalanceOfReceiver,
                        TransactionType.CREDIT,
                        "Received from " + senderAccount.getAccountNo()
                );
                connection.commit();
            } catch (BankingException e) {
                connection.rollback();
                throw e;
            } catch (Exception e) {
                connection.rollback();
                throw new BankingException("Transfer failed", e);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to database", e);
        }
    }

    public BigDecimal getCurrentAccountBalance(Long customerId) {
        try (Connection connection = DBConnection.getConnection()) {
            Account account = accountRepo.findAccountByCustomerId(connection , customerId);
            return account.getBalance();
        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch balance");
        }
    }

    public Account getCurrentUserAccount(Long customerId) {
        try (Connection connection = DBConnection.getConnection()){
            return accountRepo.findAccountByCustomerId(connection , customerId);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to fetch user account", e);
        }
    }

    // helper method to save transaction for all the operations
    private void saveTransaction(
            Connection connection,
            Account account,
            BigDecimal amount,
            BigDecimal balance,
            TransactionType type,
            String remarks
    ) {
        Transaction transaction = transactionService.createTransaction(
                account,amount,balance,type,remarks
        );
        transactionRepo.save(connection,transaction);
    }
}