import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        BankDataBase bankDataBase = new BankDataBase();
        FrameTest frameTest = new FrameTest(bankDataBase);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setVisible(true);
    }
}