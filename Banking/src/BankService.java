public class BankService {

    public void transferMoney(Account fromAccount , Account toAccount , double amount) throws InsufficientBalance , AccountNotFoundException{

        if (fromAccount.getAccNo() == "null") {
            throw new AccountNotFoundException("Account is not available...");
        }
        if (!(fromAccount.getBalance() >= amount)) {
                throw new InsufficientBalance("insufficient balance...");
            }
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
    }

    public static void main(String[] args) throws Exception {
        Account account1 = new Account("" , "Noorain" , 1000);
        Account account2 = new Account("102" , "Tashviq" , 2000);
        Account account3 = new Account("103" , "joyal" , 3000);

        //I'm depositing 100 rupees to my account
        account1.deposit(100);
        account1.withdraw(1000);
        System.out.println("current balance : " + account1.getBalance());

        BankService bank = new BankService();
        try {
            bank.transferMoney(account1 , account2 , 1000);
        } catch (InsufficientBalance i) {
            System.out.println(i.getMessage());
        } catch (AccountNotFoundException a) {
            System.out.println(a.getMessage());
        }
        System.out.println(account1.getBalance()); // 0
        System.out.println(account2.getBalance()); //2100
        System.out.println("work done...");
    }
}
