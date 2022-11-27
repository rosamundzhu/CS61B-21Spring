package deque;

import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    /* Test IntComparator of MaxArrayDeque */
    public void testIntComparator() {

        Comparator<Integer> c = MaxArrayDeque.getIntComparator();
        MaxArrayDeque<Integer> a = new MaxArrayDeque<>(c);
        assertNull("Max should be null.", a.max());

        for (int i = 0; i < 5; i++) {
            a.addFirst(i);
        }
        assertEquals("Max should be 4.", 4, (double) a.max(), 0.0);

        for (int i = 5; i < 10; i++) {
            a.addLast(i);
        }
        assertEquals("Max should be 9.", 9, (double) a.max(), 0.0);
    }

    @Test
    public void testStringComparator() {
        Comparator<String> c = MaxArrayDeque.getCharComparator();
        MaxArrayDeque<String> a = new MaxArrayDeque<>(c);
        assertNull("Max should be null.", a.max());

        a.addFirst("abc");
        a.addFirst("bca");
        a.addLast("cab");
        assertEquals("Max should be cab: ", "cab", a.max());
    }

    @Test
    public void testAssignedComparator() {
        Comparator<Integer> x = MaxArrayDeque.getIntComparator();
        Comparator<String> y = MaxArrayDeque.getCharComparator();
        MaxArrayDeque<String> a = new MaxArrayDeque(x);
        assertNull("Max should be null.", a.max(y));

        a.addFirst("Jack");
        a.addFirst("Alex");
        a.addLast("Brian");
        a.addFirst("NoName");

        assertEquals("Max should be 'NoName'.", "NoName", a.max(y));
    }

    @Test
    public void testBasic1() {
        Comparator<Integer> x = MaxArrayDeque.getIntComparator();
        MaxArrayDeque<Integer> MaxArrayDeque = new MaxArrayDeque(x);
        MaxArrayDeque.addFirst(0);
        assertEquals("It should be 0.", 0, MaxArrayDeque.removeLast(), 0);
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.isEmpty();
        MaxArrayDeque.addFirst(8);
        assertEquals("It should be 8.", 8, MaxArrayDeque.removeLast(),0);
    }

    @Test
    public void testBasic2() {
        Comparator<Integer> x = MaxArrayDeque.getIntComparator();
        MaxArrayDeque<Integer> MaxArrayDeque = new MaxArrayDeque(x);
        MaxArrayDeque.addLast(0);
        assertEquals("It should be 0.", 0, MaxArrayDeque.get(0),0);
        assertEquals("It should be 0.", 0, MaxArrayDeque.removeFirst(),0);
        MaxArrayDeque.addLast(3);
        assertEquals("It should be 3.", 3, MaxArrayDeque.removeLast(),0);
        MaxArrayDeque.addLast(5);
        assertEquals("It should be 5.", 5, MaxArrayDeque.removeLast(),0);
        MaxArrayDeque.addLast(7);
        assertEquals("It should be 7.", 7, MaxArrayDeque.removeLast(),0);
        MaxArrayDeque.addLast(9);
        assertEquals("It should be 9.", 9, MaxArrayDeque.get(0),0);
    }
}
