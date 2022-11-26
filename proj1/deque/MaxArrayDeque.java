package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> ourComparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        ourComparator = c;
        // creates a MaxArrayDeque with the given Comparator
    }

    public T max() {
        int size = size();
        if (size == 0) {
            return null;
        }
        T maxItem = this.get(0);
        for (int i = 1; i < size; i ++ ) {
            if (ourComparator.compare(this.get(i), maxItem) > 0) {
                maxItem = this.get(i);
            }
        }
        return maxItem;
    }
    // returns the maximum element in the deque as governed by the previously given Comparator.
    // If the MaxArrayDeque is empty, simply return null.

    public T max(Comparator<T> c) {
        int size = size();
        if (size == 0) {
            return null;
        }
        T maxItem = this.get(0);
        for (int i = 1; i < size; i ++ ) {
            if (c.compare(this.get(i), maxItem) > 0) {
                maxItem = get(i);
            }
        }
        return maxItem;
    }
    // returns the maximum element in the deque as governed by the parameter Comparator c.
    // If the MaxArrayDeque is empty, simply return null.

    public static Comparator<Integer> getIntComparator() {
        return new IntComparator();
    }

    private static class IntComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    public static Comparator<String> getCharComparator() {
        return new StringComparator();
    }
    private static class StringComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }
}
