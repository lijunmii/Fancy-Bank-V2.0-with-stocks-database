import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankDataBase {
    public static final double savingInterestRate = 0.002;
    public static final double withdrawFeeRate = 0.002;
    public static final double transferFeeRate = 0.002;
    public static final double loanInterestRate = 0.008;

    private List<Client> clients;
    private List<TransactionRecord> records;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    BankDataBase() { // interest are calculated per month
        clients = new ArrayList<>();
        records = new ArrayList<>();
    }

    public Client getClient(String username) {
        for (Client client : clients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    public Client getClient(String username, String password) {
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }

    public Account getAccount(String accountId) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getAccountId().equals(accountId)) {
                    return account;
                }
            }
        }
        return null;
    }

    public boolean usernameExist(String username) {
        for (Client client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean accountIdExist(String accountId) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getAccountId().equals(accountId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addRecord(TransactionRecord record) {
        records.add(record);
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<TransactionRecord> getRecords() {
        return records;
    }

    // functions below this comment will generate a record
    public void addClient(Client client) {
        clients.add(client);

        Date date = new Date();
        String username = client.getUsername();
        String recordSummary = "[" + date.toString() + "] REGISTRATION";
        String recordContent = "[" + date.toString() + "] New registered client: " + username + ".";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void addAccount(String username, Account account) {
        for (Client client : clients) {
            if (client.getUsername().equals(username)) {
                client.addAccount(account);
                break;
            }
        }

        Date date = new Date();
        String accountType = account.accountType;
        String recordSummary = "[" + date.toString() + "] ADD_ACCOUNT";
        String recordContent = "[" + date.toString() + "] " + username + " add a new " + accountType + " account.";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void deposit(String username, int accountIndex, double amount) {
        getClient(username).deposit(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] DEPOSIT";
        String recordContent = "[" + date.toString() + "] " + username + " deposit a value of $" + decimalFormat.format(amount) + " into account: " + accountId + ".";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void withdraw(String username, int accountIndex, double amount) {
        getClient(username).withdraw(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] WITHDRAW";
        String recordContent = "[" + date.toString() + "] " + username + " withdraw a value of $" + decimalFormat.format(amount) + " from account: " + accountId + ".";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void transfer(String username, int accountIndex, String receiverAccountId, double amount) {
        getClient(username).transfer(accountIndex, getAccount(receiverAccountId), amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] TRANSFER";
        String recordContent = "[" + date.toString() + "] " + username + " transfer a value of $" + decimalFormat.format(amount) + " from account: " + accountId + " to account:" + receiverAccountId;
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void addLoan(String username, int accountIndex, double amount) {
        getClient(username).addLoan(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] REQUEST_LOAN";
        String recordContent = "[" + date.toString() + "] " + username + " request a loan of value $" + decimalFormat.format(amount) + " using account: " + accountId + ".";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void repayLoan(String username, int accountIndex, int loanIndex) {
        getClient(username).repayLoan(accountIndex, loanIndex);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        double repayment = getClient(username).getLoans().get(loanIndex).getRepayment();
        String recordSummary = "[" + date.toString() + "] REPAY_LOAN";
        String recordContent = "[" + date.toString() + "] " + username + " repay a loan of value $" + decimalFormat.format(repayment) + " using account: " + accountId + ".";
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }
}
