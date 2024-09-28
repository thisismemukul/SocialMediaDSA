import java.util.ArrayList;
import java.util.List;

public class UserFeed {
    public List<Post> getFeedFromFollowing(User user) {
        List<Post> feed = new ArrayList<Post>();
        for (User following : user.getFollowing()) {
            feed.addAll(following.getPosts());
        }
        feed.sort((p1,p2) -> p2.getPostId().compareTo(p1.getPostId()));
        return feed;
    }
    public List<Post> getUserFeed(User user) {
        List<Post> feed = new ArrayList<Post>(user.getPosts());
        feed.sort((p1,p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));
        return feed;
    }

}
