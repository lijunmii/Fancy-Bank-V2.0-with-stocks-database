import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FrameRegister extends JFrame {
    private JPanel panel = new JPanel();
    private JTextField inputUsername = new JTextField(18);
    private JTextField inputAddress = new JTextField(18);
    private JTextField inputPhoneNumber = new JTextField(18);
    private JPasswordField inputPassword = new JPasswordField(18);
    private JPasswordField inputPasswordAgain = new JPasswordField(18);
    private JButton buttonRegister = new JButton("Register");

    FrameRegister() {}
    FrameRegister(BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(6, 1));
        panel.setBorder(BorderFactory.createEtchedBorder());

        JPanel panel_1 = new JPanel(); {
            panel_1.setBorder(new TitledBorder("Username"));
            panel_1.add(inputUsername);
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setBorder(new TitledBorder("Address"));
            panel_2.add(inputAddress);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setBorder(new TitledBorder("Phone Number"));
            panel_3.add(inputPhoneNumber);
        }
        panel.add(panel_3);

        JPanel panel_4 = new JPanel(); {
            panel_4.setBorder(new TitledBorder("Password"));
            panel_4.add(inputPassword);
        }
        panel.add(panel_4);

        JPanel panel_5 = new JPanel(); {
            panel_5.setBorder(new TitledBorder("Re-enter Password"));
            panel_5.add(inputPasswordAgain);
        }
        panel.add(panel_5);

        JPanel panel_6 = new JPanel(); {
            panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 15));
            panel_6.add(buttonRegister);
        }
        panel.add(panel_6);

        add(panel);
        setTitle("REGISTER");
        setSize(250, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        buttonRegister.addActionListener(e -> {
            String username = inputUsername.getText();
            String address = inputAddress.getText();
            String phoneNumber = inputPhoneNumber.getText();
            String password = new String(inputPassword.getPassword());
            String passwordAgain = new String(inputPasswordAgain.getPassword());
            if (username.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty username!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (address.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty address!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (phoneNumber.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty phone number!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (password.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty password!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (passwordAgain.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please re-enter your password!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (!password.equals(passwordAgain)) {
                JOptionPane.showMessageDialog(this, "Password doesn't match!", "NO MATCH", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!bankDatabase.usernameExist(username)) {
                    Client client = new Client(username, password);
                    // 2 default accounts for every new client
                    client.addAccount(new AccountChecking(username + "Acc" + (client.getAccounts().size() + 1), 100));
                    client.addAccount(new AccountSaving(username + "Acc" + (client.getAccounts().size() + 1), 0));
                    bankDatabase.addClient(client);
                    JOptionPane.showMessageDialog(this, "Registration Success!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Username already exists!", "EXISTED", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
