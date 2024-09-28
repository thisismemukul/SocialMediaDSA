import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Post {
    private String postId;
    private String title;
    private String content;
    private User author;
    private Set<User> likes;
    private LocalDateTime createdAt;

    public Post(){
        this.postId = "";
        this.title = "";
        this.content = "";
        this.author = new User();
        this.likes = new HashSet<User>();
        this.createdAt = LocalDateTime.now();
    }

    public Post(String postId, String title, String content, User author) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.likes = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    public Set<User> getLikes() {
        return likes;
    }
    public int getLikeCount() {
        return likes.size();
    }
    public String getPostId() {
        return postId;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public User getAuthor() {
        return author;
    }

    public void likePost(User user){
        if (likes.contains(user)){
            throw new IllegalArgumentException("You have already liked this post");
        }
        likes.add(user);
    }
    public void unlikePost(User user){
        likes.remove(user);
    }

    public void likePost(User user, Post post) {
        post.likePost(user);
    }
    public void unlikePost(User user, Post post) {
        post.unlikePost(user);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
