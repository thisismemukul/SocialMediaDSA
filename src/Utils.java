import java.util.Comparator;

public class Utils {

    public void lineSep(){
        for (int i = 0; i<=100; i++){
            System.out.print("-");
            if (i==100){
                System.out.println();
            }
        }
    }
    public static class PostComparator implements Comparator<Post>{
        @Override
        public int compare(Post p1, Post p2){
            return p1.getCreatedAt().compareTo(p2.getCreatedAt()); //Latest post first
        }
    }
}
