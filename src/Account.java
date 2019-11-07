abstract class Account {
    protected static final String[] accountTypes = {"checking", "saving"};
    protected String accountType;
    protected String accountId;
    protected double balance;

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

    abstract void withdraw(double amount);

    abstract void transfer(Account account, double amount);
}
