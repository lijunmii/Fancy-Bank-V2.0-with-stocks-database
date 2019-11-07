import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrameDeposit extends JFrame {
    private List<Account> accounts;
    private Currency currency = new Currency();

    DecimalFormat df = new DecimalFormat("0.00");

    private JPanel panel = new JPanel();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaDetails = new JTextArea();
    private JComboBox comboBoxCurrency = new JComboBox();
    private JTextField textFieldAmount = new JTextField(10);
    private JButton buttonDeposit = new JButton("Make Deposit");

    FrameDeposit() {}
    FrameDeposit(FrameATM frameATM, BankDataBase bankDataBase) {
        panel.setLayout(new GridLayout(3, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));

            JPanel panel_1_1 = new JPanel(); {
                panel_1_1.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
                panel_1_1.setBackground(Color.WHITE);
                JLabel title = new JLabel("<html>Deposit - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
                panel_1_1.add(title);
            }
            panel_1.add(panel_1_1);
            panel_1.add(new JLabel("  Your accounts are listed below.", JLabel.LEFT));
            panel_1.add(new JLabel("  Select an account you want to use for deposit.", JLabel.LEFT));
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(1, 2));
            panel_2.setBorder(BorderFactory.createEtchedBorder());

            String username = frameATM.getClient().getUsername();
            accounts = bankDataBase.getClient(username).getAccounts();
            List<String> accountIds = new ArrayList<>();
            for (Account account : accounts) {
                accountIds.add(account.getAccountId());
            }
            listAccounts.setListData(accountIds.toArray());
            scrollPaneAccounts = new JScrollPane(listAccounts);
            scrollPaneAccounts.setBorder(BorderFactory.createTitledBorder("Accounts"));
            scrollPaneAccounts.setBackground(Color.WHITE);
            panel_2.add(scrollPaneAccounts);

            textAreaDetails.setBorder(BorderFactory.createTitledBorder("Details"));
            textAreaDetails.setLineWrap(true);
            textAreaDetails.setWrapStyleWord(true);
            panel_2.add(textAreaDetails);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new GridLayout(2, 1));
            panel_3.add(new JLabel("Choose a currency, enter amount, then click Make Deposit.", JLabel.CENTER));

            JPanel panel_3_1 = new JPanel(); {
                textFieldAmount.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_3_1.add(textFieldAmount);

                comboBoxCurrency.addItem(currency.getSupportCurrencies().get(0));
                comboBoxCurrency.addItem(currency.getSupportCurrencies().get(1));
                comboBoxCurrency.addItem(currency.getSupportCurrencies().get(2));
                panel_3_1.add(comboBoxCurrency);

                panel_3_1.add(buttonDeposit);
            }
            panel_3.add(panel_3_1);
        }
        panel.add(panel_3);

        add(panel);
        setTitle("DEPOSIT");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        listAccounts.addListSelectionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                Account account = accounts.get(listAccounts.getSelectedIndex());
                String accountDetails = "Account ID: " + account.getAccountId();
                accountDetails += "\nAccount type: " + account.getAccountType();
                accountDetails += "\nAccount balance: $" + df.format(account.getBalance());
                textAreaDetails.setText(accountDetails);
            }
        });

        buttonDeposit.addActionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                String amountStr = textFieldAmount.getText();
                if (amountStr.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter amount.", "EMPTY CONTENT", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (isNumeric(amountStr)) {
                        String username = frameATM.getClient().getUsername();
                        int accountIndex = listAccounts.getSelectedIndex();
                        double amount = Double.parseDouble(amountStr);
                        switch (comboBoxCurrency.getSelectedIndex()) {
                            case 0: break;
                            case 1: amount = currency.eur2usd(amount); break;
                            case 2: amount = currency.cny2usd(amount); break;
                        }
                        bankDataBase.deposit(username, accountIndex, amount);
                        frameATM.setClient(bankDataBase.getClient(username));
                        JOptionPane.showMessageDialog(this, "Deposit Success.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Wrong amount form (number only).", "WRONG AMOUNT", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
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
