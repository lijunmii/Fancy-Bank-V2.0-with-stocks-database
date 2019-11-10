import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MarketStock extends MarketSecurities {
    private List<Stock> stocks;

    MarketStock() {
        marketType = marketTypes[0];
        stocks = new ArrayList<>();
        stocks.add(new Stock("Apple", "AAPL", 260.14));
        stocks.add(new Stock("Microsoft", "MSFT", 145.96));
        stocks.add(new Stock("Amazon", "AMZN", 1785.88));
        stocks.add(new Stock("Google", "GOOGL", 1309.00));
        stocks.add(new Stock("Facebook", "FB", 190.84));
    }
    MarketStock(List<Stock> stocks) {
        marketType = marketTypes[0];
        this.stocks = new ArrayList<>();
        this.stocks.addAll(stocks);
        // todo: read stock data from database here;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void updateStockPrice() {
        for (Stock stock : stocks) {
            Random random = new Random();
            double priceUpDown = (99.0 + 2 * random.nextDouble()) / 100.0;
            stock.setPricePerShare(stock.getPricePerShare() * priceUpDown);
        }
    }
}
