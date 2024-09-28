import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> usersByUsername;
    private Map<String, User> usersById;

    public UserManager() {
        usersByUsername = new HashMap<>();
        usersById = new HashMap<>();
    }

    public User registerUser(String username) {
        if (usersByUsername.containsKey(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }
        User user = new User();
        user.setUsername(username);
        usersByUsername.put(username, user);
        usersById.put(user.getId(), user);
        return user;
    }

    public User findUserByUsername(String username) {
        return usersByUsername.get(username);
    }

    public User findUserById(String id) {
        return usersById.get(id);
    }
}
