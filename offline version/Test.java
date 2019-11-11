import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase();
        Client client = new Client("123", "123");
        client.addAccount(new AccountChecking("123Acc1", 1000));
        client.addAccount(new AccountSaving("123Acc2", 3000));
        bankDatabase.addClient(client);
        FrameTest frameTest = new FrameTest(bankDatabase);
        frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTest.setVisible(true);
    }
}