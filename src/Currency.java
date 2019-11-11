import java.util.ArrayList;
import java.util.List;

public class Currency {
    private static List<String> supportCurrencies;
    private static int supportCurrencyNumber = 3;
    private static double exchangeRate[][];

    Currency() {
        supportCurrencies = new ArrayList<>();
        supportCurrencies.add("USD");
        supportCurrencies.add("EUR");
        supportCurrencies.add("CNY");
        exchangeRate = new double[supportCurrencyNumber][supportCurrencyNumber];
        exchangeRate[0][0] = 1.00;
        exchangeRate[0][1] = 0.90;
        exchangeRate[0][2] = 7.08;
        exchangeRate[1][0] = 1 / exchangeRate[0][1];
        exchangeRate[1][1] = 1.00;
        exchangeRate[1][2] = exchangeRate[0][2] / exchangeRate[0][1];
        exchangeRate[2][0] = 1 / exchangeRate[0][2];
        exchangeRate[2][1] = 1 / exchangeRate[1][2];
        exchangeRate[2][2] = 1.00;
    }

    public List<String> getSupportCurrencies() {
        return supportCurrencies;
    }

    public double usd2eur(double amount) {
        return amount * exchangeRate[0][1];
    }

    public double usd2cny(double amount) {
        return amount * exchangeRate[0][2];
    }

    public double eur2usd(double amount) {
        return amount * exchangeRate[1][0];
    }

    public double eur2cny(double amount) {
        return amount * exchangeRate[1][2];
    }

    public double cny2usd(double amount) {
        return amount * exchangeRate[2][0];
    }

    public double cny2eur(double amount) {
        return amount * exchangeRate[2][1];
    }
}
