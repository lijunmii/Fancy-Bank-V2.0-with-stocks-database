import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrameAccounts extends JFrame {
    private List<Account> accounts;

    private JPanel panel = new JPanel();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaDetails = new JTextArea();
    private JComboBox comboBoxSelectType = new JComboBox();
    private JButton buttonAddAccount = new JButton("HERE");

    FrameAccounts() {}
    FrameAccounts(FrameATM frameATM, BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(3, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));

            JPanel panel_1_1 = new JPanel(); {
                panel_1_1.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
                panel_1_1.setBackground(Color.WHITE);
                JLabel title = new JLabel("<html>Account Management - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
                panel_1_1.add(title);
            }
            panel_1.add(panel_1_1);
            panel_1.add(new JLabel("  Your accounts are listed below.", JLabel.LEFT));
            panel_1.add(new JLabel("  Click to see account details.", JLabel.LEFT));
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
            JScrollPane scrollPaneDetails = new JScrollPane(textAreaDetails);
            panel_2.add(scrollPaneDetails);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
            panel_3.add(new JLabel("To add a new "));

            comboBoxSelectType.addItem("checking");
            comboBoxSelectType.addItem("saving");
            panel_3.add(comboBoxSelectType);

            panel_3.add(new JLabel(" account, click "));

            panel_3.add(buttonAddAccount);
        }
        panel.add(panel_3);

        add(panel);
        setTitle("MANAGE ACCOUNTS");
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

        buttonAddAccount.addActionListener(e -> {
            String username = frameATM.getClient().getUsername();
            Client client = bankDatabase.getClient(username);
            Account account;
            switch (comboBoxSelectType.getSelectedIndex()) {
                case 0: {
                    account = new AccountChecking(username + "Acc" + (client.getAccounts().size() + 1), 0);
                    bankDatabase.addAccount(username, account);
                    frameATM.setClient(bankDatabase.getClient(username));
                } break;
                case 1: {
                    account = new AccountSaving(username + "Acc" + (client.getAccounts().size() + 1), 0);
                    bankDatabase.addAccount(username, account);
                    frameATM.setClient(bankDatabase.getClient(username));
                } break;
            }
            JOptionPane.showMessageDialog(this, "New account added!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }
}
