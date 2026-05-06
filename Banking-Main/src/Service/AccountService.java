package Service;

import java.math.BigDecimal;

public  class AccountService {
    public void register(String name , String phone , String email , String password) {}
    public boolean isLogin(String username , String password){return true;}
    public BigDecimal viewBalance(String accountNo){
        return null;
    }
    public void deposit(String accountNo , BigDecimal amount){}
    public void withdraw(String accountNo , BigDecimal amount){}
    public void transferMoney(String fromAccount , String toAccount , BigDecimal amount){
        /*
    1. validate accounts exist
    2. validate amount > 0
    3. check balance
    4. debit sender
    5. credit receiver
    6. insert transaction record
         */
    }
    public void viewTransactionHistory(){}
}


/*
Account-service will handle
deposit
withdraw
balance
transfer
 */