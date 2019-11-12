public class StockHold extends Stock {
    private int shareNum;

    StockHold(Stock stock, int shareNum) {
        super(stock.getStockName(), stock.getStockId(), stock.getPricePerShare());
        this.shareNum = shareNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void updateStockPrice(Stock stock) {
        setPricePerShare(stock.getPricePerShare());
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    @Override
    public String toString() {
        return (getStockName() + ", " + getStockId() + ", " + shareNum + " shares");
    }
}
