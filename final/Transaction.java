public class Transaction {
    public double amount;
    public boolean paymentStatus;

    public Transaction(double amount) {
        this.amount = amount;
        this.paymentStatus = false;
    }

    public void processPayment() {
        this.paymentStatus = true;
    }
}
