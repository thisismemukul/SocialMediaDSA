import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<String, User>();
    }
    public UserManager(Map<String, User> users) {
        this.users = users;
    }

    public User registerUser(String username) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }
        User user = new User();
        user.setUsername(username);
        users.put(username, user);
        return user;
    }
    public User findUser(String username) {
        return users.get(username);
    }

}
