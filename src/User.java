import java.util.*;

public class User {
    private final String id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private List<Post> posts;

    public User(){
        this.id= UUID.randomUUID().toString();
        this.username="";
        this.email="";
        this.followers=new HashSet<User>();
        this.following=new HashSet<User>();
        this.posts=new ArrayList<>();
    }
    public User(String id, String username, String email, Set<User> followers, Set<User> following, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.followers = followers;
        this.following = following;
        this.posts=posts;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public Set<User> getFollowers(){
        return followers;
    }
    public Set<User> getFollowing(){
        return following;
    }
    public List<Post> getPosts(){
        return posts;
    }
    public void follow(User user) {
        if (this.equals(user)) {
            System.out.println("You cannot follow yourself.");
            return;
        }

        if (following.contains(user)) {
            System.out.println("You are already following " + user.getUsername());
        } else {
            following.add(user);
            user.getFollowers().add(this);
            System.out.println("You are now following " + user.getUsername());
        }
    }

    public void unfollow(User user) {
        if (!following.contains(user)) {
            System.out.println("You are not following " + user.getUsername());
        } else {
            following.remove(user);
            user.getFollowers().remove(this);
            System.out.println("You have unfollowed " + user.getUsername());
        }
    }


    public void addPost(Post post) {
        posts.add(post);
    }


    @Override
    public String toString(){
        return "User {" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers.size() +
                ", following=" + following.size() +
                ", posts=" + posts.size() +
                '}';
    }

}
