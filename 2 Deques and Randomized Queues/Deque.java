import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("You are not supposed to call this function"); }
        public Item next() {
            if (current == null) throw new NoSuchElementException("Null argument in iterator");
            Item temp = current.item;
            current = current.next;
            return temp;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
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

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null to addFirst()");
        boolean temp = isEmpty();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (temp) last = first;
        else oldfirst.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null to addLast()");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Null to removeFirst()");
        Item item = first.item;
        first = first.next;
        if (!isEmpty()) first.prev = null;
        if (isEmpty()) last = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Null to removeLast()");
        Item item = last.item;
        last = last.prev;
        if (last != null) last.next = null;
        if (last == null) first = null;
        return item;

        /*Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            if (first.next == last) {
                last = first;
            } else {
                Node temp = first;
                while (temp.next.next != null) {
                    temp = temp.next;
                }
                last = temp;
            }
        }
        return item;*/
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<>();
        for (int i = 1; i <= 10; i++) {
            test.addFirst(i);
        }
        System.out.println(test.size());
        for (int i = 1; i <= 10; i++) {
            System.out.println(test.removeFirst());
        }
        System.out.println(test.isEmpty());
        System.out.println(test.last == null);
        test.addFirst(4);
        test.addLast(2);
        test.addFirst(69);
        Iterator<Integer> temp = test.iterator();
        while (temp.hasNext()) {
            System.out.println(temp.next());
        }
        System.out.println(test.removeFirst());
        System.out.println(test.removeFirst());
        System.out.println(test.removeFirst());
        System.out.println(test.isEmpty());
    }

}
