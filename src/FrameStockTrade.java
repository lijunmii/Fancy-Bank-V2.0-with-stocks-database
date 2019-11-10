import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FrameStockTrade extends JFrame {
    private List<Stock> stocks;
    private List<Account> accounts;

    private JPanel panel = new JPanel();
    private JButton buttonUpdatePrice = new JButton("Update Stock Price");
    private JList listStocks = new JList();
    private JScrollPane scrollPaneStocks = new JScrollPane();
    private JTextArea textAreaStockDetails = new JTextArea();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaAccountDetails = new JTextArea();
    private JTextField textFieldBuyAmount = new JTextField(15);
    private JButton buttonBuyStock = new JButton("Buy");
    private JTextField textFieldSellAmount = new JTextField(15);
    private JButton buttonSellStock = new JButton("Sell");

    FrameStockTrade() {}
    FrameStockTrade(FrameATM frameATM, BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(5, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));

            JPanel panel_1_1 = new JPanel(); {
                panel_1_1.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
                panel_1_1.setBackground(Color.WHITE);
                JLabel title = new JLabel("<html>Stock Trade - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
                panel_1_1.add(title);
            }
            panel_1.add(panel_1_1);
            panel_1.add(new JLabel("Welcome to the stock market, You can manage your stocks here.", JLabel.CENTER));

            JPanel panel_1_3 = new JPanel(); {
                panel_1_3.add(new JLabel("To simulate price changes, click", JLabel.CENTER));
                panel_1_3.add(buttonUpdatePrice);
            }
            panel_1.add(panel_1_3);
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(1, 2));
            panel_2.setBorder(BorderFactory.createEtchedBorder());

            stocks = bankDatabase.getMarketStock().getStocks();
            List<String> stockInfoList = new ArrayList<>();
            for (Stock stock : stocks) {
                String stockInfo = stock.getStockName() + ", " + stock.getStockId();
                stockInfoList.add(stockInfo);
            }
            listStocks.setListData(stockInfoList.toArray());
            scrollPaneStocks = new JScrollPane(listStocks);
            scrollPaneStocks.setBorder(BorderFactory.createTitledBorder("Stocks"));
            scrollPaneStocks.setBackground(Color.WHITE);
            panel_2.add(scrollPaneStocks);

            textAreaStockDetails.setBorder(BorderFactory.createTitledBorder("Stock Details"));
            textAreaStockDetails.setLineWrap(true);
            textAreaStockDetails.setWrapStyleWord(true);
            panel_2.add(textAreaStockDetails);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new GridLayout(1, 2));
            panel_3.setBorder(BorderFactory.createEtchedBorder());

            String username = frameATM.getClient().getUsername();
            accounts = bankDatabase.getClient(username).getAccounts();
            List<String> accountIds = new ArrayList<>();
            for (Account account : accounts) {
                accountIds.add(account.getAccountId());
            }
            listAccounts.setListData(accountIds.toArray());
            scrollPaneAccounts = new JScrollPane(listAccounts);
            scrollPaneAccounts.setBorder(BorderFactory.createTitledBorder("Accounts"));
            scrollPaneAccounts.setBackground(Color.WHITE);
            panel_3.add(scrollPaneAccounts);

            textAreaAccountDetails.setBorder(BorderFactory.createTitledBorder("Account Details"));
            textAreaAccountDetails.setLineWrap(true);
            textAreaAccountDetails.setWrapStyleWord(true);
            JScrollPane scrollPaneDetails = new JScrollPane(textAreaAccountDetails);
            panel_3.add(scrollPaneDetails);
        }
        panel.add(panel_3);

        JPanel panel_4 = new JPanel(); {
            panel_4.setLayout(new GridLayout(3, 1));
            panel_4.setBorder(BorderFactory.createEtchedBorder());

            panel_4.add(new JLabel("To buy stock, choose a stock and a securities account,", JLabel.CENTER));

            panel_4.add(new JLabel("enter how many shares you want to buy, then click Buy.", JLabel.CENTER));

            JPanel panel_4_3 = new JPanel(); {
                textFieldBuyAmount.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_4_3.add(textFieldBuyAmount);
                panel_4_3.add(buttonBuyStock);
            }
            panel_4.add(panel_4_3);
        }
        panel.add(panel_4);

        JPanel panel_5 = new JPanel(); {
            panel_5.setLayout(new GridLayout(3, 1));
            panel_5.setBorder(BorderFactory.createEtchedBorder());

            panel_5.add(new JLabel("To sell, choose a securities account and a stock you keep,", JLabel.CENTER));

            panel_5.add(new JLabel("enter how many shares you want to sell, then click Sell.", JLabel.CENTER));

            JPanel panel_5_3 = new JPanel(); {
                textFieldSellAmount.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_5_3.add(textFieldSellAmount);
                panel_5_3.add(buttonSellStock);
            }
            panel_5.add(panel_5_3);
        }
        panel.add(panel_5);

        add(panel);
        setTitle("STOCK TRADE");
        setSize(400, 540);
        setLocationRelativeTo(null);
        setResizable(false);

        listStocks.addListSelectionListener(e -> {
            if (!listStocks.isSelectionEmpty()) {
                Stock stock = stocks.get(listStocks.getSelectedIndex());
                String stockDetails = stock.toString();
                textAreaStockDetails.setText(stockDetails);
            }
        });

        listAccounts.addListSelectionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                Account account = accounts.get(listAccounts.getSelectedIndex());
                String accountDetails = account.toString();
                textAreaAccountDetails.setText(accountDetails);
            }
        });

        buttonUpdatePrice.addActionListener(e -> {
            bankDatabase.updateStockPrice();
            stocks = bankDatabase.getMarketStock().getStocks();
            List<String> stockInfoList = new ArrayList<>();
            for (Stock stock : stocks) {
                String stockInfo = stock.getStockName() + ", " + stock.getStockId();
                stockInfoList.add(stockInfo);
            }
            listStocks.setListData(stockInfoList.toArray());
            listAccounts.clearSelection();
            textAreaStockDetails.setText(null);
            textAreaAccountDetails.setText(null);
        });

        buttonBuyStock.addActionListener(e -> {
            if (!listStocks.isSelectionEmpty()) {
                int stockIndex = listStocks.getSelectedIndex();
                if (!listAccounts.isSelectionEmpty()) {
                    int accountIndex = listAccounts.getSelectedIndex();
                    if (accounts.get(accountIndex) instanceof AccountSecurities) {
                        String buyAmountStr = textFieldBuyAmount.getText();
                        if (buyAmountStr.length() == 0 || !isNumeric(buyAmountStr)) {
                            JOptionPane.showMessageDialog(this, "Please enter valid amount.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            Stock stock = stocks.get(stockIndex);
                            String username = frameATM.getClient().getUsername();
                            int buyAmount = Integer.parseInt(buyAmountStr);
                            if (bankDatabase.getClient(username).getAccounts().get(accountIndex).getBalance() >= stock.getPricePerShare() * buyAmount) {
                                bankDatabase.buyStock(username, accountIndex, stock, buyAmount);
                                frameATM.setClient(bankDatabase.getClient(username));
                                JOptionPane.showMessageDialog(this, "Stock Bought.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Insufficient balance!", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select a securities account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select an account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a stock.", "NO STOCK", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonSellStock.addActionListener(e -> {
            if (!listStocks.isSelectionEmpty()) {
                int stockIndex = listStocks.getSelectedIndex();
                if (!listAccounts.isSelectionEmpty()) {
                    int accountIndex = listAccounts.getSelectedIndex();
                    if (accounts.get(accountIndex) instanceof AccountSecurities) {
                        String sellAmountStr = textFieldSellAmount.getText();
                        if (sellAmountStr.length() == 0 || !isNumeric(sellAmountStr)) {
                            JOptionPane.showMessageDialog(this, "Please enter valid amount.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            Stock stock = stocks.get(stockIndex);
                            String username = frameATM.getClient().getUsername();
                            int sellAmount = Integer.parseInt(sellAmountStr);
                            if (((AccountSecurities) accounts.get(accountIndex)).keepStock(stock)) {
                                if (((AccountSecurities) accounts.get(accountIndex)).keepEnoughStock(stock, sellAmount)) {
                                    bankDatabase.sellStock(username, accountIndex, stock, sellAmount);
                                    frameATM.setClient(bankDatabase.getClient(username));
                                    JOptionPane.showMessageDialog(this, "Stock Sold.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                                    dispose();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Insufficient shares!", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Please select a stock that you keep.", "WRONG STOCK", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select a securities account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select an account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a stock.", "NO STOCK", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private boolean isNumeric(String str) {
        String testStr;
        try {
            testStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
