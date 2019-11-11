import javax.swing.*;
import java.awt.*;

public class FrameTest extends JFrame {
    private JPanel panel = new JPanel();
    private JButton testClient = new JButton("Open ATM interface as a client");
    private JButton testManager = new JButton("Open bank interface as manager");
    private FrameATM frameATM;
    private FrameBankManager frameBankManager;

    FrameTest(BankDatabase bankDatabase) {
        panel.setLayout(new GridLayout(2, 1));

        JPanel panelTestClient = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        testClient.setPreferredSize(new Dimension(240, 40));
        testClient.setBackground(Color.WHITE);
        panelTestClient.add(testClient);
        panel.add(panelTestClient);

        JPanel panelTestManager = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        testManager.setPreferredSize(new Dimension(240, 40));
        testManager.setBackground(Color.WHITE);
        panelTestManager.add(testManager);
        panel.add(panelTestManager);

        add(panel);
        setTitle("BACK END FOR TEST");
        setSize(360, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        frameATM = new FrameATM();
        frameBankManager = new FrameBankManager();

        testClient.addActionListener(e -> {
            if (!subWindowExist()) {
                frameATM = new FrameATM(bankDatabase);
                frameATM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameATM.setVisible(true);
            }
        });

        testManager.addActionListener(e -> {
            if (!subWindowExist()) {
                frameBankManager = new FrameBankManager(bankDatabase);
                frameBankManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameBankManager.setVisible(true);
            }
        });
    }

    private boolean subWindowExist() {
        return (frameATM.isVisible() || frameBankManager.isVisible());
    }
}
