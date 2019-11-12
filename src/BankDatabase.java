import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankDatabase {
    public static final double savingInterestRate = 0.002;
    public static final double withdrawFeeRate = 0.002;
    public static final double transferFeeRate = 0.002;
    public static final double loanInterestRate = 0.008;
    public static final double stockTradeFee = 4.99;

    private List<Client> clients;
    private List<TransactionRecord> records;
    private List<Stock> stocks;
    private MarketStock marketStock;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    /** database credentials including host, username, password */
    private static final String url = "jdbc:mysql://localhost:3306/stock?useSSL=false";
    private static final String user = "root";
    private static final String password = "pass";

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet res;

    BankDatabase() {
        clients = new ArrayList<>();
        records = new ArrayList<>();
        stocks = new ArrayList<>();
        // connect to a local database
        connectDatabase();
        clients = readClients();
        readStock();
        if (stocks.size() == 0) {
            marketStock = new MarketStock();
        } else {
            marketStock = new MarketStock(stocks);
        }
        // stock prices vary every "day"
        marketStock.updateStockPrice();
        saveDatabase(marketStock.getStocks());
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<TransactionRecord> getRecords() {
        String query = "select * from records";
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(query);
            while (res.next()) {
                String summary = res.getString("summary");
                String content = res.getString("content");
                records.add(new TransactionRecord(summary, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return records;
    }

    public MarketStock getMarketStock() {
        return marketStock;
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
        String summary = record.getRecordSummary();
        String content = record.getRecordContent();
        String query = "INSERT INTO records (summary, content) VALUES (\'" + summary + "\', \'" + content + "\');";
        try {
            // opening db connection to MySQL server
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
            try { res.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public void updateStockPrice() {
        marketStock.updateStockPrice();
        saveDatabase(marketStock.getStocks());
    }

    /**
     * Adds a client to database
     * @param client : Client
     */
    public void addClient(Client client) {
        clients.add(client);

        Date date = new Date();
        String username = client.getUsername();
        String recordSummary = "[" + date.toString() + "] REGISTRATION";
        String recordContent = "[" + date.toString() + "] New registered client: " + username + ".";
        saveClients();
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
        String recordContent = "[" + date.toString() + "] " + username + " added a new " + accountType + " account.";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void deposit(String username, int accountIndex, double amount) {
        getClient(username).deposit(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] DEPOSIT";
        String recordContent = "[" + date.toString() + "] " + username + " deposited a value of $" + decimalFormat.format(amount) + " into account: " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void withdraw(String username, int accountIndex, double amount) {
        getClient(username).withdraw(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] WITHDRAW";
        String recordContent = "[" + date.toString() + "] " + username + " withdrew a value of $" + decimalFormat.format(amount) + " from account: " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void transfer(String username, int accountIndex, String receiverAccountId, double amount) {
        getClient(username).transfer(accountIndex, getAccount(receiverAccountId), amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] TRANSFER";
        String recordContent = "[" + date.toString() + "] " + username + " transferred a value of $" + decimalFormat.format(amount) + " from account: " + accountId + " to account:" + receiverAccountId;
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void addLoan(String username, int accountIndex, double amount) {
        getClient(username).addLoan(accountIndex, amount);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] REQUEST_LOAN";
        String recordContent = "[" + date.toString() + "] " + username + " requested a loan of value $" + decimalFormat.format(amount) + " using account: " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void repayLoan(String username, int accountIndex, int loanIndex) {
        getClient(username).repayLoan(accountIndex, loanIndex);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        double repayment = getClient(username).getLoans().get(loanIndex).getRepayment();
        String recordSummary = "[" + date.toString() + "] REPAY_LOAN";
        String recordContent = "[" + date.toString() + "] " + username + " repaid a loan of value $" + decimalFormat.format(repayment) + " using account: " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void upgradeToSecurities(String username, int accountIndex) {
        getClient(username).upgradeToSecurities(accountIndex);

        Date date = new Date();
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] ACCOUNT_UPGRADE";
        String recordContent = "[" + date.toString() + "] " + username + " upgraded his saving account: " + accountId + " to a securities account.";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void buyStock(String username, int accountIndex, Stock stock, int buyAmount) {
        readStock();
        getClient(username).buyStock(accountIndex, stock, buyAmount);

        Date date = new Date();
        String stockInfo = "[" + stock.getStockName() + ", " + stock.getStockId() + "]";
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] BUY_STOCK";
        String recordContent = "[" + date.toString() + "] " + username + " bought " + buyAmount + " shares of " + stockInfo + " into account " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    public void sellStock(String username, int accountIndex, Stock stock, int sellAmount) {
        readStock();
        getClient(username).sellStock(accountIndex, stock, sellAmount);

        Date date = new Date();
        String stockInfo = "[" + stock.getStockName() + ", " + stock.getStockId() + "]";
        String accountId = getClient(username).getAccounts().get(accountIndex).getAccountId();
        String recordSummary = "[" + date.toString() + "] SELL_STOCK";
        String recordContent = "[" + date.toString() + "] " + username + " sold " + sellAmount + " shares of " + stockInfo + " from account " + accountId + ".";
        saveClients();
        addRecord(new TransactionRecord(recordSummary, recordContent));
    }

    /**
     * Connection to the local Database
     */
    public void connectDatabase() {
        try {
            // opening db connection to MySQL server
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect to the local Database
     */
    public void disconnectDatabase() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Stocks information from the local Database
     */
    public void readStock() {
        String query = "select * from stocks";
        try {
            // opening db connection to MySQL server
            stmt = conn.createStatement();
            res = stmt.executeQuery(query);
            while (res.next()) {
                String company = res.getString("name");
                String tick = res.getString("tick");
                Double price = res.getDouble("price");
                stocks.add(new Stock(company, tick, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save Stock prices to the local Database
     * @param stocks : List<Stock>
     */
    public void saveDatabase(List<Stock> stocks) {
        for (Stock stock: stocks) {
            Double price = stock.getPricePerShare();
            String tick = stock.getStockId();
            String query = "UPDATE stocks SET price = " + price + " WHERE tick = " + "\"" + tick + "\";";
            try {
                // opening db connection to MySQL server
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
                try { res.close(); } catch (SQLException e) {e.printStackTrace();}
            }
        }
    }

    //Save clients to the database
    public void saveClients() {
        try {
            String clearQuery = "TRUNCATE TABLE clientByte";
            stmt = conn.createStatement();
            stmt.executeUpdate(clearQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        for(Client c: clients){
            try {
                String userName = c.getUsername();
                String query = "Insert clientByte values(?,?)";
                PreparedStatement prest = conn.prepareStatement(query);
                byte [] b = obj2byte(c);
                prest.setString(1,userName);
                prest.setBytes(2,b);
                prest.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
            }
        }
    }

    //Read client data from database
    public List<Client> readClients() {
        String query = "select * from clientByte";
        List<Client> clients = new ArrayList<>();
        try {
            // opening db connection to MySQL server
            stmt = conn.createStatement();
            res = stmt.executeQuery(query);
            while (res.next()) {
                Client c = byte2obj(res.getBytes("javaObject"));
                clients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
            try { res.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return clients;
    }

    /**
    *Convert Java Object to Byte[]
     **/
    public static byte[] obj2byte(Object obj) throws Exception {
        byte[] ret = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(obj);
        out.close();
        ret = baos.toByteArray();
        baos.close();
        return ret;
    }

    //Convert byte[] to Java object
    public static Client byte2obj(byte[] bytes) throws Exception {
        Object ret = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bais);
        ret = in.readObject();
        in.close();
        return (Client)ret;
    }




}