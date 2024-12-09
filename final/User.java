public class User implements AccountManager{
    private String userID;
    private String email;
    private String password;
    private List notifications;

    /**
     * Constructor for User
     * @param userID The user's ID
     * @param email The user's email
     * @param password The user's password
     */
    public User(String userID, String email, String password) {
        for (User user: users) {
            if (user.getEmail.equals(email)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                throw new IllegalArgumentException("User ID already exists");
            }
        }

        this.userID = userID;
        registerAccount(email, password);
        notifications = new ArrayList<Notification>();
    }

    /**
     * Registers an account
     * @param email The user's email
     * @param password The user's password
     */
    public void registerAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Logs in the user
     * @param email The user's email
     * @param password The user's password
     */
    public void login(String email, String password) {
        // implement later?
    }

    /**
     * Resets the user's password
     * @param email The user's email
     */
    public void resetPassword(String userID, String email, String newPassword) {
        setPassword(newPassword);
    }

    /**
     * Updates the user's profile
     * @param email The user's email
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Gets the user's email
     * @return the user's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets the user's password
     * @return the user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the user's notifications
     * @return the user's notifications
     */
    public List getNotifications() {
        return this.notifications;
    }

    /**
     * Sets the user's ID
     * @param userID the user's ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Sets the user's email
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's password
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's notifications
     * @param notifications the user's notifications
     */
    public void setNotifications(List notifications) {
        this.notifications = notifications;
    }
}