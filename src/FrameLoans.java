import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FrameLoans extends JFrame {
    private List<Account> accounts;
    private List<Loan> loans;

    DecimalFormat df = new DecimalFormat("0.00");

    private JPanel panel = new JPanel();
    private JList listLoans = new JList();
    private JScrollPane scrollPaneLoans = new JScrollPane();
    private JTextArea textAreaLoanDetails = new JTextArea();
    private JList listAccounts = new JList();
    private JScrollPane scrollPaneAccounts = new JScrollPane();
    private JTextArea textAreaAccountDetails = new JTextArea();
    private JTextField textFieldAmount = new JTextField(12);
    private JButton buttonRequestLoan = new JButton("Request Loan");
    private JButton buttonRepayLoan = new JButton("Repay Loan");

    FrameLoans() {}
    FrameLoans(FrameATM frameATM, BankDataBase bankDataBase) {
        panel.setLayout(new GridLayout(4, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));
            panel_1.setBackground(Color.WHITE);

            JPanel panel_1_1 = new JPanel(); {
                panel_1_1.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
                panel_1_1.setBackground(Color.WHITE);
                JLabel title = new JLabel("<html>Loan Management - <font color='#8A2BE2'>FANCY BANK</font>™</html>", JLabel.CENTER);
                panel_1_1.add(title);
            }
            panel_1.add(panel_1_1);
            panel_1.add(new JLabel("  Your loans and accounts are listed below.", JLabel.LEFT));
            panel_1.add(new JLabel("  Select corresponding entries to manage loans.", JLabel.LEFT));
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(1, 2));
            panel_2.setBorder(BorderFactory.createEtchedBorder());

            String username = frameATM.getClient().getUsername();
            loans = bankDataBase.getClient(username).getLoans();
            List<String> loanDates = new ArrayList<>();
            for (int i = 0; i < loans.size(); i++) {
                Loan loan = loans.get(i);
                loanDates.add((i + 1) + "." + loan.getRequestDate().toString());
            }
            listLoans.setListData(loanDates.toArray());
            scrollPaneLoans = new JScrollPane(listLoans);
            scrollPaneLoans.setBorder(BorderFactory.createTitledBorder("Loans"));
            scrollPaneLoans.setBackground(Color.WHITE);
            panel_2.add(scrollPaneLoans);

            textAreaLoanDetails.setBorder(BorderFactory.createTitledBorder("Loan Details"));
            textAreaLoanDetails.setLineWrap(true);
            textAreaLoanDetails.setWrapStyleWord(true);
            panel_2.add(textAreaLoanDetails);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new GridLayout(1, 2));
            panel_3.setBorder(BorderFactory.createEtchedBorder());

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
            panel_3.add(scrollPaneAccounts);

            textAreaAccountDetails.setBorder(BorderFactory.createTitledBorder("Account Details"));
            textAreaAccountDetails.setLineWrap(true);
            textAreaAccountDetails.setWrapStyleWord(true);
            panel_3.add(textAreaAccountDetails);
        }
        panel.add(panel_3);

        JPanel panel_4 = new JPanel(); {
            panel_4.setLayout(new GridLayout(2, 1));


            JPanel panel_4_1 = new JPanel(); {
                panel_4_1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
                panel_4_1.setBorder(BorderFactory.createEtchedBorder());
                panel_4_1.add(buttonRepayLoan);
            }
            panel_4.add(panel_4_1);

            JPanel panel_4_2 = new JPanel(); {
                panel_4_2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
                panel_4_2.setBorder(BorderFactory.createEtchedBorder());
                textFieldAmount.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_4_2.add(textFieldAmount);
                panel_4_2.add(new JLabel(" USD  "));
                panel_4_2.add(buttonRequestLoan);
            }
            panel_4.add(panel_4_2);
        }
        panel.add(panel_4);

        add(panel);
        setTitle("MANAGE LOANS");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        listLoans.addListSelectionListener(e -> {
            if (!listLoans.isSelectionEmpty()) {
                Loan loan = loans.get(listLoans.getSelectedIndex());
                String loanDetails = "Loan request date: " + loan.getRequestDate();
                loanDetails += "\nLoan amount: $" + df.format(loan.getAmount());
                loanDetails += "\nRepayment: $" + df.format(loan.getRepayment());
                textAreaLoanDetails.setText(loanDetails);
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

        buttonRequestLoan.addActionListener(e -> {
            if (!listAccounts.isSelectionEmpty()) {
                String amountStr = textFieldAmount.getText();
                if (amountStr.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter loan amount.", "EMPTY CONTENT", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (isNumeric(amountStr)) {
                        String username = frameATM.getClient().getUsername();
                        int accountIndex = listAccounts.getSelectedIndex();
                        double amount = Double.parseDouble(amountStr);
                        bankDataBase.addLoan(username, accountIndex, amount);
                        frameATM.setClient(bankDataBase.getClient(username));
                        JOptionPane.showMessageDialog(this, "Request Loan Success.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Wrong amount form (number only).", "WRONG AMOUNT", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an account to receive your loan.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonRepayLoan.addActionListener(e -> {
            if (!listLoans.isSelectionEmpty()) {
                int loanIndex = listLoans.getSelectedIndex();
                double repayment = loans.get(loanIndex).getRepayment();
                if (repayment != 0) {
                    if (!listAccounts.isSelectionEmpty()) {
                        String username = frameATM.getClient().getUsername();
                        int accountIndex = listAccounts.getSelectedIndex();
                        if (bankDataBase.getClient(username).getAccounts().get(accountIndex).able2Transaction(repayment)) {
                            bankDataBase.repayLoan(username, accountIndex, loanIndex);
                            frameATM.setClient(bankDataBase.getClient(username));
                            JOptionPane.showMessageDialog(this, "Repayment Success.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Insufficient balance!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select the account you want to use to repay the loan.", "NO ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "You've already repaid this loan.", "ALREADY PAID", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select the loan you want to repay.", "NO LOAN", JOptionPane.INFORMATION_MESSAGE);
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
