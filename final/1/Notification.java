public class Notification {
    private String description;

    /**
     * Constructor for Notification
     * @param description Description of the notification
     */
    public Notification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
