import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrameBankManager extends JFrame {
    private BankDataBase bankDataBase;
    private List<Account> accounts = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private List<TransactionRecord> records = new ArrayList<>();

    DecimalFormat df = new DecimalFormat("0.00");

    private JPanel panel = new JPanel();

    private JList listClients = new JList();
    private JScrollPane scrollPaneClients = new JScrollPane();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaAccountDetails = new JTextArea();
    private JList listLoans = new JList();
    private JScrollPane scrollPaneLoans = new JScrollPane();
    private JTextArea textAreaLoanDetails = new JTextArea();

    private JList listRecords = new JList();
    private JScrollPane scrollRecords = new JScrollPane();
    private JTextArea textAreaRecordDetails = new JTextArea();

    FrameBankManager() {}
    FrameBankManager(BankDataBase currentBankDataBase) {
        bankDataBase = currentBankDataBase;

        panel.setLayout(new GridLayout(3, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(4, 1));
            panel_1.setBorder(BorderFactory.createEtchedBorder());
            panel_1.setBackground(Color.WHITE);
            JLabel title = new JLabel("<html>Bank Management Page - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
            title.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
            panel_1.add(title);

            panel_1.add(new JLabel("   Hello manager, welcome to bank management page.", JLabel.LEFT));
            panel_1.add(new JLabel("   Everything is going well at this time.", JLabel.LEFT));
            panel_1.add(new JLabel("   You can view clients and all records below.", JLabel.LEFT));
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(1, 3));
            panel_2.setBorder(BorderFactory.createEtchedBorder());

            List<String> usernameList = new ArrayList<>();
            for (Client client : bankDataBase.getClients()) {
                usernameList.add(client.getUsername());
            }
            listClients.setListData(usernameList.toArray());
            scrollPaneClients = new JScrollPane(listClients);
            scrollPaneClients.setBorder(BorderFactory.createTitledBorder("Clients"));
            scrollPaneClients.setBackground(Color.WHITE);
            panel_2.add(scrollPaneClients);

            JPanel panel_2_2 = new JPanel(); {
                panel_2_2.setLayout(new GridLayout(1, 2));
                panel_2_2.setBorder(BorderFactory.createEtchedBorder());

                scrollPaneAccounts = new JScrollPane(listAccounts);
                scrollPaneAccounts.setBorder(BorderFactory.createTitledBorder("Accounts"));
                scrollPaneAccounts.setBackground(Color.WHITE);
                panel_2_2.add(scrollPaneAccounts);

                textAreaAccountDetails.setBorder(BorderFactory.createTitledBorder("Details"));
                textAreaAccountDetails.setLineWrap(true);
                textAreaAccountDetails.setWrapStyleWord(true);
                panel_2_2.add(textAreaAccountDetails);
            }
            panel_2.add(panel_2_2);

            JPanel panel_2_3 = new JPanel(); {
                panel_2_3.setLayout(new GridLayout(1, 2));
                panel_2_3.setBorder(BorderFactory.createEtchedBorder());

                scrollPaneLoans = new JScrollPane(listLoans);
                scrollPaneLoans.setBorder(BorderFactory.createTitledBorder("Loans"));
                scrollPaneLoans.setBackground(Color.WHITE);
                panel_2_3.add(scrollPaneLoans);

                textAreaLoanDetails.setBorder(BorderFactory.createTitledBorder("Details"));
                textAreaLoanDetails.setLineWrap(true);
                textAreaLoanDetails.setWrapStyleWord(true);
                panel_2_3.add(textAreaLoanDetails);
            }
            panel_2.add(panel_2_3);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new GridLayout(1, 2));
            panel_3.setBorder(BorderFactory.createEtchedBorder());

            records = bankDataBase.getRecords();
            List<String> recordList = new ArrayList<>();
            for (TransactionRecord record : records) {
                recordList.add(record.getRecordSummary());
            }
            listRecords.setListData(recordList.toArray());
            scrollRecords = new JScrollPane(listRecords);
            scrollRecords.setBorder(BorderFactory.createTitledBorder("Records"));
            scrollRecords.setBackground(Color.WHITE);
            panel_3.add(scrollRecords);

            textAreaRecordDetails.setBorder(BorderFactory.createTitledBorder("Record Details"));
            textAreaRecordDetails.setLineWrap(true);
            textAreaRecordDetails.setWrapStyleWord(true);
            panel_3.add(textAreaRecordDetails);
        }
        panel.add(panel_3);

        add(panel);
        setTitle("MANAGE BANK");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        listClients.addListSelectionListener(e -> {
            if (!listClients.isSelectionEmpty()) {
                accounts = bankDataBase.getClients().get(listClients.getSelectedIndex()).getAccounts();
                List<String> accountIds = new ArrayList<>();
                for (Account account : accounts) {
                    accountIds.add(account.getAccountId());
                }
                listAccounts.setListData(accountIds.toArray());

                loans = bankDataBase.getClients().get(listClients.getSelectedIndex()).getLoans();
                List<String> loanDates = new ArrayList<>();
                for (int i = 0; i < loans.size(); i++) {
                    Loan loan = loans.get(i);
                    loanDates.add((i + 1) + "." + loan.getRequestDate().toString());
                }
                listLoans.setListData(loanDates.toArray());

                textAreaAccountDetails.setText("");

                textAreaLoanDetails.setText("");
            }
        });

        listAccounts.addListSelectionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                Account account = accounts.get(listAccounts.getSelectedIndex());
                String accountDetails = "Account ID: " + account.getAccountId();
                accountDetails += "\nAccount type: " + account.getAccountType();
                accountDetails += "\nAccount balance: $" + df.format(account.getBalance());
                textAreaAccountDetails.setText(accountDetails);
            }
        });

        listLoans.addListSelectionListener(e -> {
            if (!listLoans.isSelectionEmpty()) {
                Loan loan = loans.get(listLoans.getSelectedIndex());
                String loanDetails = "Loan request date: " + loan.getRequestDate();
                loanDetails += "\nLoan amount: $" + df.format(loan.getAmount());
                loanDetails += "\nRepayment: $" + df.format(loan.getRepayment());
                textAreaLoanDetails.setText(loanDetails);
            }
        });

        listRecords.addListSelectionListener(e -> {
            if (!listRecords.isSelectionEmpty()) {
                TransactionRecord record = records.get(listRecords.getSelectedIndex());
                textAreaRecordDetails.setText(record.getRecordContent());
            }
        });
    }
}
