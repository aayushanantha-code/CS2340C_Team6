public interface AccountInterface {
    public void registerAccount(String email, String password);

    public void login(String email, String password);

    public void resetPassword(String email);

    public void updateProfile(String email);
}