import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        float counter = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            counter += 1;
            if (StdRandom.bernoulli(1/counter)){
                champion = s;
            }
        }
        StdOut.println(champion);
    }
}
