public class OrderEmailSender implements EmailSender {
    @Override
    public void sendEmail(String customerEmail, String subject, String message) {
        System.out.println("Email to: " + customerEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + message);
    }
}