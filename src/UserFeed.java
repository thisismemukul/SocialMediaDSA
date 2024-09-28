import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class UserFeed {
    public List<Post> getFeedFromFollowing(User user) {
        List<Post> feed = new ArrayList<Post>();
        PriorityQueue<Post> postQueue = new PriorityQueue<>(new Utils.PostComparator());
        for (User following : user.getFollowing()) {
            postQueue.addAll(following.getPosts());
        }
        while (!postQueue.isEmpty()) {
            feed.add(postQueue.poll());
        }
        return feed; // this will return the most recent post using PriorityQueue
    }
    public List<Post> getUserFeed(User user) {
        List<Post> feed = new ArrayList<Post>(user.getPosts());
        feed.sort(new Utils.PostComparator());
        return feed;
    }

}
