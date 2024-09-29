import java.time.LocalDateTime;
import java.util.*;

public class SocialMedia {

    public static void main(String[] arg) {
        try {
            UserManagement userManagement = new UserManagement();
            UserMain mukul = userManagement.registerUser("mukul");
            UserMain bucky = userManagement.registerUser("bucky");
            UserMain captain = userManagement.registerUser("captain");
            UserMain ironman = userManagement.registerUser("ironman");
            // UserMain ironman1 = userManagement.registerUser("ironman"); //throw exc
            mukul.follow(bucky, userManagement);
            mukul.follow(new UserMain(), userManagement);
            mukul.follow(captain, userManagement);
            UserFeedMain userFeedMain = new UserFeedMain();
            List<PostMain> userFeedOfMukul = userFeedMain.getUserFeed(mukul);
            System.out.println("UserFeedOfMukul: " + userFeedOfMukul);
            bucky.createPost("I'm Winter soldier", "If you know you now");
            captain.createPost("I'm Winter soldier", "If you know you now");
//            List<PostMain> updatedUserFeedOfMukul = userFeedMain.getUserFeed(mukul);
//            for (PostMain post : updatedUserFeedOfMukul) {
//                System.out.println("Updated UserFeedOfMukul: " +
//                        post.getAuthor().getUsername() + ": " +
//                        post.getTitle() + " -> " +
//                        post.getContent());
//            }

            List<PostMain> topPosts = userFeedMain.getTopPostsFromFeed(mukul);
            System.out.println("Top Posts in Mukul's Feed:");
            for (PostMain post : topPosts) {
                System.out.println(post.getAuthor().getUsername() + ": " +
                        post.getTitle() + " -> " +
                        post.getContent());
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

}


class UserMain {
    private String id;
    private String username;
    private Set<UserMain> followers;
    private Set<UserMain> following;
    private List<PostMain> posts;

    public UserMain() {
        this.id = UUID.randomUUID().toString();
        this.username = "";
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.posts = new LinkedList<>();
    }

    //getter setter

    public String getId() {
        return id;
    }

    public Set<UserMain> getFollowers() {
        return followers;
    }

    public Set<UserMain> getFollowing() {
        return following;
    }

    public List<PostMain> getPosts() {
        return posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //follow
    public void follow(UserMain user, UserManagement userManagement) {
        if (!userManagement.getUserById(user.getId())) {
            System.out.println("User not found");
            return;
        }

        if (this.equals(user)) {
            System.out.println("You cannot follow yourself");
            return;
        }

        if (following.contains(user)) {
            System.out.println("You are already following " + user.getUsername());
        } else {
            following.add(user);
            user.followers.add(this);
            System.out.println("Following " + user.getUsername());
        }
    }
    public void createPost(String title, String content) {
        PostMain post = new PostMain(title, content, this);
        addPost(post);
    }

    public void addPost(PostMain post) {
        if (!posts.contains(post)) {
            posts.add(post);
            System.out.println(username + " Posted a new Post -> " + post.getId());
        }
    }
}


class PostMain {
    private String id;
    private String title;
    private String content;
    private UserMain author;
    private Set<UserMain> likes;
    private LocalDateTime createdAt;

    public PostMain() {
        this.id = UUID.randomUUID().toString();
        this.title = "";
        this.content = "";
        this.author = new UserMain();
        this.likes = new HashSet<UserMain>();
        this.createdAt = LocalDateTime.now();
    }

    public PostMain(String title, String content, UserMain author) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }
    //Getter Setter

    public String getId() {
        return id;
    }

    public UserMain getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

}

class UserManagement {
    private Map<String, UserMain> usersByUsername;
    private Map<String, UserMain> usersById;

    public UserManagement() {
        this.usersByUsername = new HashMap<>();
        this.usersById = new HashMap<>();
    }

    public UserMain registerUser(String username) {
        if (usersByUsername.containsKey(username)) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserMain user = new UserMain();
        user.setUsername(username);
        usersByUsername.put(username, user);
        usersById.put(user.getId(), user);
        return user;
    }

    public boolean getUserById(String id) {
        return usersById.containsKey(id);
    }

    public boolean getUserByUsername(String username) {
        return usersByUsername.containsKey(username);
    }
}

class UserFeedMain {

    public List<PostMain> getUserFeed(UserMain user) {
        List<PostMain> posts = new LinkedList<>();
        for (UserMain following : user.getFollowing()) {
            posts.addAll(following.getPosts());
        }
        return posts;
    }

    public List<PostMain> getTopPostsFromFeed(UserMain user) {
        List<PostMain> allPosts = getUserFeed(user);
        allPosts.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
        return allPosts.size() > 10 ? allPosts.subList(0, 10) : allPosts;
    }
}


