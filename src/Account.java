import java.io.Serializable;
import java.text.DecimalFormat;

abstract class Account implements Serializable {
    protected static final String[] accountTypes = {"checking", "saving", "securities"};
    protected String accountType;
    protected String accountId;
    protected double balance;

    DecimalFormat df = new DecimalFormat("0.00");

    Account(String accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public void setBalance(double amount) {
        balance = amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean able2Transaction(double amount) {
        return (amount <= balance);
    }

    @Override
    public String toString() { // for print account info
        String accountDetails = "Account ID: " + getAccountId();
        accountDetails += "\nAccount type: " + getAccountType();
        accountDetails += "\nAccount balance: $" + df.format(getBalance());
        return accountDetails;
    }

    abstract void withdraw(double amount);

    abstract void transfer(Account account, double amount);
}
