import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private static List users;

    public AccountManager() {
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List getUsers() {
        return users;
    }

    public void setUsers(List users) {
        this.users = users;
    }
}
