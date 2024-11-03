public interface EmailSender {
    void sendEmail(String customerEmail, String subject, String message);
}