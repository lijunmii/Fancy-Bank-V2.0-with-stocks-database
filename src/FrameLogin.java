import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FrameLogin extends JFrame {
    private JPanel panel = new JPanel();
    private JTextField inputUsername = new JTextField(18);
    private JPasswordField inputPassword = new JPasswordField(18);
    private JButton buttonLogin = new JButton("Login");

    FrameLogin() {}
    FrameLogin(FrameATM frameATM, BankDataBase bankDataBase) {
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEtchedBorder());

        JPanel panel_1 = new JPanel(); {
            panel_1.setBorder(new TitledBorder("Username"));
            panel_1.add(inputUsername);
        }
        panel.add(panel_1);

        JPanel panel_2 = new JPanel(); {
            panel_2.setBorder(new TitledBorder("Password"));
            panel_2.add(inputPassword);
        }
        panel.add(panel_2);

        JPanel panel_3 = new JPanel(); {
            panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 15));
            panel_3.add(buttonLogin);
        }
        panel.add(panel_3);

        add(panel);
        setTitle("LOGIN");
        setSize(250, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        buttonLogin.addActionListener(e -> {
            String username = inputUsername.getText();
            String password = new String(inputPassword.getPassword());
            if (username.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty username!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else if (password.length() == 0) {
                JOptionPane.showMessageDialog(this, "Empty password!", "MISSING CONTENT", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Client client = bankDataBase.getClient(username, password);
                if (client != null) {
                    frameATM.successLogin(client);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong username/password!", "WRONG", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
