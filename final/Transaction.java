public class Transaction {
    private double amount;
    private boolean paymentStatus;

    public Transaction(double amount) {
        this.amount = amount;
        this.paymentStatus = false;
    }

    public void processPayment() {
        setPaymentStatus(true);
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
