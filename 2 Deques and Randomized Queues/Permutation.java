import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        for (int i = 0; i < n; i++) {
            System.out.println(rq.dequeue());
        }
        /*int N = Integer.parseInt(args[0]);
        String[] storage = new String[5];
        int counter = 0;
        while (!StdIn.isEmpty()) {
            if (counter >= storage.length) {
                String[] copy = new String[storage.length*2];
                for (int i = 0; i < N; i++) {
                    copy[i] = storage[i];
                }
                storage = copy;
            }
            storage[counter] = StdIn.readString();
            counter++;
        }
        for (int i = 0; i < counter; i++) {
            int r = StdRandom.uniform(counter);
            String temp = storage[i];
            storage[i] = storage[r];
            storage[r] = temp;
        }
        for (int i = 0; i < N; i++) {
            System.out.println(storage[i]);
        }*/
    }
}