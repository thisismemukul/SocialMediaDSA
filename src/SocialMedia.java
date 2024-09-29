import java.time.LocalDateTime;
import java.util.*;

public class SocialMedia {

    public static void main(String[] arg) {
        try {
            UserManagement userManagement = UserManagement.getUserManagementInstance(); // Singleton
            UserMain mukul = userManagement.registerUser("mukul");
            UserMain bucky = userManagement.registerUser("bucky");
            UserMain captain = userManagement.registerUser("captain");
            UserMain ironman = userManagement.registerUser("ironman");

            mukul.follow(bucky, userManagement);
            mukul.follow(captain, userManagement);

            UserFeedMain userFeedMain = new UserFeedMain();
            List<PostMain> userFeedOfMukul = userFeedMain.getUserFeed(mukul);
            System.out.println("UserFeedOfMukul: " + userFeedOfMukul);
            bucky.createPost("I'm Winter soldier", "If you know you now");
            captain.createPost("I'm Captain America", "Avengers Assemble");
            ironman.createPost("I'm Iron Man", "Tony");

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

    // Getters and setters
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

    // Follow method
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
        posts.add(post);
        System.out.println(username + " Posted a new Post -> " + post.getId());
    }
}

class PostMain {
    private String id;
    private String title;
    private String content;
    private UserMain author;
    private Set<UserMain> likes;
    private LocalDateTime createdAt;

    public PostMain(String title, String content, UserMain author) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    // Getters
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

class UserManagement {
    private static UserManagement userManagement;
    private Map<String, UserMain> usersByUsername;
    private Map<String, UserMain> usersById;

    private UserManagement() {
        this.usersByUsername = new HashMap<>();
        this.usersById = new HashMap<>();
    }

    public static UserManagement getUserManagementInstance() {
        if (userManagement == null) {
            userManagement = new UserManagement();
        }
        return userManagement;
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
        PriorityQueue<PostMain> topPostsQueue = new PriorityQueue<>(new UtilsMain.PostComparator());
        topPostsQueue.addAll(allPosts);

        List<PostMain> topPosts = new ArrayList<>();
//        for (int i = 0; i < Math.min(10, topPostsQueue.size()); i++) {
//            topPosts.add(topPostsQueue.poll());
//        }
        while (!topPostsQueue.isEmpty()) {
            topPosts.add(topPostsQueue.poll());
        }
        return topPosts;
    }
}

class UtilsMain {
    public static class PostComparator implements Comparator<PostMain> {
        @Override
        public int compare(PostMain post1, PostMain post2) {
            return post2.getCreatedAt().compareTo(post1.getCreatedAt());
        }
    }
}