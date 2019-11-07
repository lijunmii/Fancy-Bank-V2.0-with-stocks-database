import java.util.Date;

public class Loan {
    private Date requestDate;
    private double amount;
    private double repayment;
    private double interestRate;

    Loan() {
        requestDate = new Date();
    }
    Loan(double amount, double interestRate) {
        requestDate = new Date();
        this.interestRate = interestRate;
        this.amount = amount;
        repayment = this.amount;
        applyInterest(1);
    }

    public void setRepayment(double value) {
        repayment = value;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public double getAmount() {
        return amount;
    }

    public double getRepayment() {
        return repayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void applyInterest(int timePeriod) {
        for (int i = 0; i < timePeriod; i++) {
            repayment *= (1 + interestRate);
        }
    }
}
