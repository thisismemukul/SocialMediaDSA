import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("InstBook");
        Utils utils = new Utils();

        utils.lineSep();
        UserManager userManager = new UserManager();
        User mukul = userManager.registerUser("mukul");
        User bucky = userManager.registerUser("bucky");
        User captain = userManager.registerUser("captain");

        bucky.follow(mukul);
        bucky.follow(captain);

        captain.follow(mukul);
        captain.follow(bucky);

        utils.lineSep();
        System.out.println("Get followers of "+ captain.getUsername());
        for (User follower : captain.getFollowers()) {
            System.out.println(follower.toString());
        }

        utils.lineSep();
        System.out.println("Get following users of "+ captain.getUsername());
        for (User folowing : captain.getFollowing()) {
            System.out.println(folowing.toString());
        }

        Post post1 = new Post(
                UUID.randomUUID().toString(),
                "My last trip",
                "It was so adorable",
                mukul);
        Post post2 = new Post(
                UUID.randomUUID().toString(),
                "I'm the Winter Soldier",
                "I am awesome :)",
                bucky);
        Post post3 = new Post(
                UUID.randomUUID().toString(),
                "Avengers Assemble",
                "FOr the end game",
                captain);
        mukul.addPost(post1);
        bucky.addPost(post2);
        captain.addPost(post3);
        mukul.addPost(new Post(
                UUID.randomUUID().toString(),
                "Meesho's Upcoming diwali sale.",
                "Visit website for more",
                mukul));

        utils.lineSep();
        System.out.println("Get Feed of "+ mukul.getUsername());
        UserFeed feed = new UserFeed();
        List<Post> mukulsFeed = feed.getUserFeed(mukul);
        for (Post post : mukulsFeed) {
            System.out.println(
                    post.getAuthor().getUsername() + ": " +
                            post.getTitle() + " -> " +
                            post.getContent());
        }

        utils.lineSep();
        System.out.println("Get Following Feed of "+ bucky.getUsername());
        List<Post> buckyFeed = feed.getFeedFromFollowing(bucky);
        for (Post post : buckyFeed) {
            System.out.println(
                    post.getAuthor().getUsername() + ": " +
                    post.getTitle() + " -> " +
                    post.getContent());
        }

    }
}