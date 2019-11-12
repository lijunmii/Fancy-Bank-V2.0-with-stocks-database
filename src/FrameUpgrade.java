import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrameUpgrade extends JFrame {
    private List<Account> accounts;

    private JPanel panel = new JPanel();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaDetails = new JTextArea();
    private JButton buttonUpgrade = new JButton("Upgrade to securities account");

    FrameUpgrade() {}
    FrameUpgrade(FrameATM frameATM, BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(3, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));

            JPanel panel_1_1 = new JPanel(); {
                panel_1_1.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
                panel_1_1.setBackground(Color.WHITE);
                JLabel title = new JLabel("<html>Account Upgrade - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
                panel_1_1.add(title);
            }
            panel_1.add(panel_1_1);
            panel_1.add(new JLabel("  Your accounts are listed below. A saving account with balance", JLabel.LEFT));
            panel_1.add(new JLabel("  more than $3000 can be upgraded to a securities account.", JLabel.LEFT));
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(1, 2));
            panel_2.setBorder(BorderFactory.createEtchedBorder());

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
            panel_2.add(scrollPaneAccounts);

            textAreaDetails.setBorder(BorderFactory.createTitledBorder("Details"));
            textAreaDetails.setLineWrap(true);
            textAreaDetails.setWrapStyleWord(true);
            panel_2.add(textAreaDetails);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
            panel_3.add(buttonUpgrade);
        }
        panel.add(panel_3);

        add(panel);
        setTitle("UPGRADE ACCOUNTS");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        listAccounts.addListSelectionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                Account account = accounts.get(listAccounts.getSelectedIndex());
                String accountDetails = account.toString();
                textAreaDetails.setText(accountDetails);
            }
        });

        buttonUpgrade.addActionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                String username = frameATM.getClient().getUsername();
                Client client = bankDatabase.getClient(username);
                int accountIndex = listAccounts.getSelectedIndex();
                Account account = client.getAccounts().get(accountIndex);
                if (account.getBalance() >= 3000 && account.getAccountType() .equals("saving")) {
                    bankDatabase.upgradeToSecurities(username, accountIndex);
                    frameATM.setClient(bankDatabase.getClient(username));
                    JOptionPane.showMessageDialog(this, "Upgrade Success.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Only saving account with balance ≥ $3000 can be upgraded.", "WRONG ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
