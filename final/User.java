public class User implements AccountManager{
    public String userID;
    public String email;
    private String password;
    public List notifications;

    /**
     * Constructor for User
     * @param userID The user's ID
     * @param email The user's email
     * @param password The user's password
     */
    public User(String userID, String email, String password) {
        for (User user : users) {
            if (user.email.equals(email)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        for (User user : users) {
            if (user.userID.equals(userID)) {
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

    public void login(String email, String password) {
        // implement later?
    }

    public void resetPassword(String userID, String email, String newPassword) {
        setPassword(newPassword);
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;
    }
}