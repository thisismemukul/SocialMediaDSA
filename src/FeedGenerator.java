import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class FeedGenerator {
    private PriorityQueue<Post> postQueue;

    public FeedGenerator() {
        postQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p2.getLikeCount(), p1.getLikeCount()));
    }

    public void addPost(Post post) {
        postQueue.offer(post);
    }

    public List<Post> getTopPosts() {
        List<Post> topPosts = new ArrayList<>();
        while (!postQueue.isEmpty()) {
            topPosts.add(postQueue.poll());
        }
        return topPosts;
    }
}
