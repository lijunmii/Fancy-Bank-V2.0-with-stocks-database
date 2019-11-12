import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrameATM extends JFrame {
    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase();
        FrameATM frameATM = new FrameATM(bankDatabase);
        frameATM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameATM.setVisible(true);
    }

    private Client client = null;
    private boolean login = false;

    private JPanel panel = new JPanel();
    private JLabel labelWelcome = new JLabel("Welcome! Please", JLabel.CENTER);
    private JButton buttonLogin = new JButton("Login");
    private JLabel labelRegister = new JLabel("Not enrolled yet?", JLabel.CENTER);
    private JButton buttonRegister = new JButton("Register Now");

    private JButton buttonDeposit = new JButton("Deposit");
    private JButton buttonWithdraw = new JButton("Withdraw");
    private JButton buttonTransfer = new JButton("Transfer");
    private JButton buttonManageLoan = new JButton("Manage Loans");
    private JButton buttonUpgrade = new JButton("Upgrade Accounts");
    private JButton buttonTradeStock = new JButton("Stock Trade");
    private JButton buttonManageAccount = new JButton("View Accounts");

    private FrameLogin frameLogin = new FrameLogin();
    private FrameRegister frameRegister = new FrameRegister();
    private FrameDeposit frameDeposit = new FrameDeposit();
    private FrameWithdraw frameWithdraw = new FrameWithdraw();
    private FrameTransfer frameTransfer = new FrameTransfer();
    private FrameLoans frameLoans = new FrameLoans();
    private FrameUpgrade frameUpgrade = new FrameUpgrade();
    private FrameStockTrade frameStockTrade = new FrameStockTrade();
    private FrameAccounts frameAccounts = new FrameAccounts();

    FrameATM() {}
    FrameATM(BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(5, 1));

        JPanel panel_1 = new JPanel(); {
            panel_1.setLayout(new GridLayout(3, 1));
            panel_1.setBorder(BorderFactory.createEtchedBorder());
            panel_1.setBackground(Color.WHITE);
            JLabel title = new JLabel("<html><font color='#8A2BE2'>FANCY BANK</font>™ ATM</html>", JLabel.CENTER);
            title.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
            panel_1.add(title);

            JPanel panel_1_2 = new JPanel(); {
                panel_1_2.add(labelWelcome);
                panel_1_2.add(buttonLogin);
                panel_1_2.add(new JLabel("here.", JLabel.CENTER));
            }
            panel_1.add(panel_1_2);

            JPanel panel_1_3 = new JPanel(); {
                panel_1_3.add(labelRegister);
                panel_1_3.add(buttonRegister);
            }
            panel_1.add(panel_1_3);
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setLayout(new GridLayout(2, 1));

            JPanel panel_2_1 = new JPanel(); {
                buttonDeposit.setPreferredSize(new Dimension(150, 45));
                panel_2_1.add(buttonDeposit);
            }
            panel_2.add(panel_2_1);

            JPanel panel_2_2 = new JPanel(); {
                buttonWithdraw.setPreferredSize(new Dimension(150, 45));
                panel_2_2.add(buttonWithdraw);
            }
            panel_2.add(panel_2_2);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new GridLayout(2, 1));

            JPanel panel_3_1 = new JPanel(); {
                buttonTransfer.setPreferredSize(new Dimension(150, 45));
                panel_3_1.add(buttonTransfer);
            }
            panel_3.add(panel_3_1);

            JPanel panel_3_2 = new JPanel(); {
                buttonManageLoan.setPreferredSize(new Dimension(150, 45));
                panel_3_2.add(buttonManageLoan);
            }
            panel_3.add(panel_3_2);
        }
        panel.add(panel_3);

        JPanel panel_4 = new JPanel(); {
            panel_4.setLayout(new GridLayout(2, 1));

            JPanel panel_4_1 = new JPanel(); {
                buttonUpgrade.setPreferredSize(new Dimension(150, 45));
                panel_4_1.add(buttonUpgrade);
            }
            panel_4.add(panel_4_1);

            JPanel panel_4_2 = new JPanel(); {
                buttonTradeStock.setPreferredSize(new Dimension(150, 45));
                panel_4_2.add(buttonTradeStock);
            }
            panel_4.add(panel_4_2);
        }
        panel.add(panel_4);

        JPanel panel_5 = new JPanel(); {
            panel_5.setLayout(new GridLayout(2, 1));

            JPanel panel_5_1 = new JPanel(); {
                buttonManageAccount.setPreferredSize(new Dimension(150, 45));
                panel_5_1.add(buttonManageAccount);
            }
            panel_5.add(panel_5_1);

            JPanel panel_5_2 = new JPanel(); {
                panel_5_2.setBorder(BorderFactory.createEtchedBorder());
                panel_5_2.setLayout(new GridLayout(2, 1));
                panel_5_2.add(new JLabel("Version 2.0 | CS591 P1 Boston University", JLabel.CENTER));
                panel_5_2.add(new JLabel("Contact us: Group 21", JLabel.CENTER));
            }
            panel_5.add(panel_5_2);
        }
        panel.add(panel_5);

        add(panel);
        setTitle("FANCY BANK ATM");
        setSize(300, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        buttonLogin.addActionListener(e -> { // login
            if (!subWindowExist() && !login) {
                frameLogin = new FrameLogin(this, bankDatabase);
                frameLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameLogin.setVisible(true);
            } else if (!subWindowExist() && login) { // logout
                client = null;
                successLogout();
            }
        });

        buttonRegister.addActionListener(e -> { // register
            if (!subWindowExist() && !login) {
                frameRegister = new FrameRegister(bankDatabase);
                frameRegister.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameRegister.setVisible(true);
            }
        });

        buttonDeposit.addActionListener(e -> { // deposit
            if (login) {
                if (!subWindowExist()) {
                    frameDeposit = new FrameDeposit(this, bankDatabase);
                    frameDeposit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameDeposit.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonWithdraw.addActionListener(e -> { // withdraw
            if (login) {
                if (!subWindowExist()) {
                    frameWithdraw = new FrameWithdraw(this, bankDatabase);
                    frameWithdraw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameWithdraw.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonTransfer.addActionListener(e -> { // transfer
            if (login) {
                if (!subWindowExist()) {
                    frameTransfer = new FrameTransfer(this, bankDatabase);
                    frameTransfer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameTransfer.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonManageLoan.addActionListener(e -> { // request/repay loans
            if (login) {
                if (!subWindowExist()) {
                    frameLoans = new FrameLoans(this, bankDatabase);
                    frameLoans.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameLoans.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonUpgrade.addActionListener(e -> { // upgrade to securities account
            if (login) {
                if (!subWindowExist()) {
                    frameUpgrade = new FrameUpgrade(this, bankDatabase);
                    frameUpgrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameUpgrade.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonTradeStock.addActionListener(e -> { // stock trade
            if (login) {
                if (!subWindowExist()) {
                    frameStockTrade = new FrameStockTrade(this, bankDatabase);
                    frameStockTrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameStockTrade.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonManageAccount.addActionListener(e -> { // view/add accounts
            if (login) {
                if (!subWindowExist()) {
                    frameAccounts = new FrameAccounts(this, bankDatabase);
                    frameAccounts.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameAccounts.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please login first.", "NO LOGIN", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void setClient(Client currentClient) {
        client = currentClient;
    }

    public Client getClient() {
        return client;
    }

    public void successLogin(Client currentClient) {
        labelWelcome.setText("Welcome! You can");
        buttonLogin.setText("Logout");
        labelRegister.setText("Select your transaction below.");
        buttonRegister.setVisible(false);
        client = currentClient;
        login = true;
    }

    public void successLogout() {
        labelWelcome.setText("Welcome! Please");
        buttonLogin.setText("Login");
        labelRegister.setText("Not enrolled yet?");
        buttonRegister.setVisible(true);
        client = null;
        login = false;
    }

    private boolean subWindowExist() { // one sub window at the same time
        return (frameLogin.isVisible() ||
                frameRegister.isVisible() ||
                frameDeposit.isVisible() ||
                frameWithdraw.isVisible() ||
                frameTransfer.isVisible() ||
                frameLoans.isVisible() ||
                frameUpgrade.isVisible() ||
                frameStockTrade.isVisible() ||
                frameAccounts.isVisible());
    }
}
