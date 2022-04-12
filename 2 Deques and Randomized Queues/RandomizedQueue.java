import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;

    private class Node {
        Item item;
        Node next;
    }

    private class ListIterator implements Iterator<Item> {
        private Node storage = null;
        private boolean cleared = false;
        public boolean hasNext() { return !cleared && !isEmpty(); } // want it to return false
        public void remove() { throw new UnsupportedOperationException("You are not supposed to call this function"); }
        public Item next() {
            if (isEmpty() || cleared) throw new NoSuchElementException("Null in iteratory");
            int gen = StdRandom.uniform(size());
            Item temp;
            if (gen == 0) {
                temp = first.item;
                first = first.next;
            } else {
                Node current = first;
                for (int i = 0; i < gen-1; i++) {
                    current = current.next;
                }
                temp = current.next.item;
                current.next = current.next.next;
                current = null;
            }
            Node oldStorage = storage;
            storage = new Node();
            storage.item = temp;
            storage.next = oldStorage;
            if (isEmpty()) {
                first = storage;
                cleared = true;
            }
            return temp;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        if (isEmpty()) return 0;
        int count = 1;
        Node test = first;
        while (test.next != null) {
            test = test.next;
            count++;
        }
        test = null;
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("null in enqueue()");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("null in dequeue()");
        int gen = StdRandom.uniform(size());
        if (gen == 0) {
            Item temp = first.item;
            first = first.next;
            return temp;
        }
        Node current = first;
        for (int i = 0; i < gen-1; i++) {
            current = current.next;
        }
        Item temp = current.next.item;
        current.next = current.next.next;
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("null in sample()");
        int gen = StdRandom.uniform(size());
        Node current = first;
        for (int i = 0; i < gen; i++) {
            current = current.next;
        }
        Item temp = current.item;
        current = null;
        return temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> testing = new RandomizedQueue<>();
        for (int i = 1; i <= 10; i++) {
            testing.enqueue(i);
        }
        Iterator<Integer> temp = testing.iterator();
        while (temp.hasNext()) {
            System.out.println(temp.next());
        }
        System.out.println("");
        Iterator<Integer> tempo = testing.iterator();
        while (tempo.hasNext()) {
            System.out.println(tempo.next());
        }
        System.out.println("");
        System.out.println(testing.size());
        System.out.println("");
        for (int i = 1; i <= 10; i++) {
            System.out.println(testing.sample());
        }
        System.out.println("");
        System.out.println(testing.size());
        System.out.println("");
        for (int i = 1; i <= 10; i++) {
            System.out.println(testing.dequeue());
        }
        System.out.println("");
        System.out.println(testing.isEmpty());
    }

}
