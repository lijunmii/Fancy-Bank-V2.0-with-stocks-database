import java.util.ArrayList;
import java.util.List;

public class Client {
    private String username;
    private String password;
    private List<Account> accounts;
    private double loanInterestRate;
    private List<Loan> loans;

    Client(String name, String pwd) {
        username = name;
        password = pwd;
        accounts = new ArrayList<>();
        loanInterestRate = BankDatabase.loanInterestRate;
        loans = new ArrayList<>();
    }

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pwd) {
        password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (Account account : accounts) {
            totalBalance += account.balance;
        }
        return totalBalance;
    }

    public void setAccounts(List<Account> accountList) {
        accounts = accountList;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void deposit(int accountIndex, double amount) {
        accounts.get(accountIndex).deposit(amount);
    }

    public void withdraw(int accountIndex, double amount) {
        accounts.get(accountIndex).withdraw(amount);
    }

    public void transfer(int accountIndex, Account account, double amount) {
        accounts.get(accountIndex).transfer(account, amount);
    }

    public Account getAccount(String Id) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(Id)) {
                return account;
            }
        }
        return null;
    }

    public void addLoan(int accountIndex, double amount) {
        Loan loan = new Loan(amount, loanInterestRate);
        loans.add(loan);
        accounts.get(accountIndex).balance += loan.getAmount();
    }

    public void repayLoan(int accountIndex, int loanIndex) {
        double repayment = loans.get(loanIndex).getRepayment();
        if (repayment <= accounts.get(accountIndex).balance) {
            accounts.get(accountIndex).balance -= repayment;
            loans.get(loanIndex).setRepayment(0);
        }
    }

    public void applyLoanInterest(int loanIndex, int timePeriod) {
        Loan loan = loans.get(loanIndex);
        loan.applyInterest(timePeriod);
    }

    public void upgradeToSecurities(int accountIndex) {
        if (accounts.get(accountIndex) instanceof AccountSaving) {
            AccountSaving accountSaving = (AccountSaving)accounts.get(accountIndex);
            AccountSecurities accountSecurities = new AccountSecurities(accountSaving);
            accounts.set(accountIndex, accountSecurities);
        }
    }

    public void buyStock(int accountIndex, Stock stock, int buyAmount) {
        if (accounts.get(accountIndex) instanceof AccountSecurities) {
            ((AccountSecurities) accounts.get(accountIndex)).buyStock(stock, buyAmount);
        }
    }

    public void sellStock(int accountIndex, Stock stock, int sellAmount) {
        if (accounts.get(accountIndex) instanceof AccountSecurities) {
            ((AccountSecurities) accounts.get(accountIndex)).sellStock(stock, sellAmount);
        }
    }
}
