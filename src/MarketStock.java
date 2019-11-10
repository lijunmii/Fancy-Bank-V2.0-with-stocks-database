import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author CS 591 Fall 19: Group 21
 */
public class MarketStock extends MarketSecurities {
    private List<Stock> stocks;

    private static final String url = "jdbc:mysql://localhost:3306/stock?useSSL=false";
    private static final String user = "root";
    private static final String password = "pass";

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet res;

    MarketStock() {

        String query = "select * from stocks";

        try {
            // opening db connection to MySQL server
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            res = stmt.executeQuery(query);
            stocks = new ArrayList<>();
            while (res.next()) {
                String company = res.getString("name");
                String tick = res.getString("tick");
                Double price = res.getDouble("price");
                System.out.println(company + " " + tick + " " + price);
                marketType = marketTypes[0];
                stocks.add(new Stock(company, tick, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (SQLException e) {e.printStackTrace();}
            try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
            try { res.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        // Leave these as comments for now (reference)
//        marketType = marketTypes[0];
//        stocks = new ArrayList<>();
//        stocks.add(new Stock("Apple", "AAPL", 260.14));
//        stocks.add(new Stock("Microsoft", "MSFT", 145.96));
//        stocks.add(new Stock("Amazon", "AMZN", 1785.88));
//        stocks.add(new Stock("Google", "GOOGL", 1309.00));
//        stocks.add(new Stock("Facebook", "FB", 190.84));
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
