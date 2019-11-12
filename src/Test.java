import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase();
        FrameTest frameTest = new FrameTest(bankDatabase);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setVisible(true);
    }
}