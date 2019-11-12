import java.util.ArrayList;
import java.util.List;

public class AccountSecurities extends AccountSaving {
    private double stockTradeFee; // apply only when selling
    private List<StockHold> stockHoldList = new ArrayList<>();

    AccountSecurities(String accountId, double balance) {
        super(accountId, balance);
        accountType = accountTypes[2];
        stockTradeFee = BankDatabase.stockTradeFee;
    }
    AccountSecurities(AccountSaving accountSaving) {
        super(accountSaving.getAccountId(), accountSaving.getBalance());
        accountType = accountTypes[2];
        stockTradeFee = BankDatabase.stockTradeFee;
    }

    public void buyStock(Stock stock, int shareNum) {
        if (balance < stock.getPricePerShare() * shareNum) {
            return;
        }
        for (StockHold stockHold : stockHoldList) { // if already bought this stock
            if (stock.getStockId().equals(stockHold.getStockId())) {
                balance -= stock.getPricePerShare() * shareNum;
                stockHold.setShareNum(stockHold.getShareNum() + shareNum);
                stockHold.updateStockPrice(stock);
                return;
            }
        }
        balance -= stock.getPricePerShare() * shareNum;
        stockHoldList.add(new StockHold(stock, shareNum));
        return;
    }

    public void sellStock(Stock stock, int shareNum) {
        for (StockHold stockHold : stockHoldList) {
            if (stock.getStockId().equals(stockHold.getStockId())) {
                stockHold.updateStockPrice(stock);
                if (stockHold.getShareNum() < shareNum) {
                    return;
                }
                stockHold.setShareNum(stockHold.getShareNum() - shareNum);
                balance += stock.getPricePerShare() * shareNum - stockTradeFee;
                if (stockHold.getShareNum() == 0) {
                    stockHoldList.remove(stockHold);
                }
                return;
            }
        }
        return;
    }

    @Override
    public String toString() { // for print account info
        String accountDetails = super.toString();
        accountDetails += "\nAccount stocks:";
        for (StockHold stockHold : stockHoldList) {
            accountDetails += "\n";
            accountDetails += "[" + stockHold.toString() + "]";
        }
        return accountDetails;
    }

    public boolean keepStock(Stock stock) {
        for (StockHold stockHold : stockHoldList) {
            if (stock.getStockId().equals(stockHold.getStockId())) {
                return true;
            }
        }
        return false;
    }

    public boolean keepEnoughStock(Stock stock, int shareNum) {
        for (StockHold stockHold : stockHoldList) {
            if (stock.getStockId().equals(stockHold.getStockId())) {
                stockHold.updateStockPrice(stock);
                if (stockHold.getShareNum() >= shareNum) {
                    return true;
                }
            }
        }
        return false;
    }
}