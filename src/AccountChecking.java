public class AccountChecking extends Account {
    private double withdrawFeeRate;
    private double transferFeeRate;

    AccountChecking(String accountId, double balance) {
        super(accountId, balance);
        accountType = accountTypes[0];
        withdrawFeeRate = BankDataBase.withdrawFeeRate;
        transferFeeRate = BankDataBase.transferFeeRate;
    }

    public void transfer(Account account, double amount) {
        double transactionFee;
        if (amount <= balance) {
            transactionFee = amount * transferFeeRate;
            double payment = amount - transactionFee;
            balance -= amount;
            account.balance += payment;
        }
    }

    public void withdraw(double amount) {
        if (able2Transaction(amount)) {
            balance -= amount;
        }
    }
}
