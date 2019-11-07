public class AccountSaving extends Account {
    private double savingInterestRate;

    AccountSaving(String accountId, double balance) {
        super(accountId, balance);
        accountType = accountTypes[1];
        savingInterestRate = BankDataBase.savingInterestRate;
    }

    public void transfer(Account account, double amount) {
        if (amount <= balance) {
            balance -= amount;
            account.balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (able2Transaction(amount)) {
            balance -= amount;
        }
    }

    public void applySavingInterest(int timePeriod) {
        for (int i = 0; i < timePeriod; i++) {
            balance *= (1 + savingInterestRate);
        }
    }
}
