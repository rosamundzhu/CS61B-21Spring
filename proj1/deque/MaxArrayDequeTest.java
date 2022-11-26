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

}
