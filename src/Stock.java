import java.io.Serializable;
import java.text.DecimalFormat;

public class Stock implements Serializable {
    protected String stockName;
    protected String stockId;
    protected double pricePerShare;

    DecimalFormat df = new DecimalFormat("0.00");

    Stock(String stockName, String stockId, double pricePerShare) {
        this.stockName = stockName;
        this.stockId = stockId;
        this.pricePerShare = pricePerShare;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockId() {
        return stockId;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    @Override
    public String toString() {
        String stockDetails = "Company name: " + getStockName();
        stockDetails += "\nNASDAQ: " + getStockId();
        stockDetails += "\nPrice: $" + df.format(getPricePerShare());
        return stockDetails;
    }
}
