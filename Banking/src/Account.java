public class Account {
    String accNo;
    String name;
    double balance;

    public Account(String accNo , String name , double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }
    public double getBalance() {
        return balance;
    }

    public String getAccNo() {
        return accNo;
    }
}
