package deque;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> lld1 = new ArrayDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("last1");
        lld1.addLast("last2");
        lld1.addFirst("front2");
        lld1.addFirst("front3");
        lld1.addFirst("front4");
        lld1.addLast("last3");
        lld1.addLast("last4");
        lld1.addLast("last5");
        lld1.addFirst("front5");
        lld1.addFirst("front6");
        lld1.addFirst("front7");
        lld1.addFirst("front8");
        lld1.addFirst("front9");
        lld1.addFirst("front10");
        lld1.addFirst("front11");
        lld1.addFirst("front12");
        lld1.addFirst("front13");
        lld1.addFirst("front14");
        lld1.addFirst("front15");
        lld1.addFirst("front16");
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        lld1.printDeque();
        assertEquals(6, lld1.size());
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());
        lld1.addFirst(10);
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());
        lld1.removeFirst();
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }
    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }
    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        // System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest1() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
            assertEquals(i, lld1.get(i), 0);
        }
        for (double i = 0; i < 500; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }
        for (double i = 999; i > 500; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest2() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
            assertEquals(i, lld1.get(i), 0);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }

    @Test
    public void testIte() {
        ArrayDeque<Integer> aque = new ArrayDeque();
        aque.addFirst(5);
        aque.addFirst(5);
        aque.addFirst(5);

        for (int i: aque) {
            assertEquals("Should have 5", 5, i, 0.0);
        }
    }

    @Test
    public void testEqual() {
        ArrayDeque<Integer> aque = new ArrayDeque();
        aque.addFirst(6);
        aque.addFirst(5);
        aque.addFirst(6);
        aque.addFirst(6);

        ArrayDeque o = new ArrayDeque();
        o.addFirst(4);
        o.addFirst(5);
        o.addFirst(6);
        o.addFirst(5);

        assertEquals("Should have different value", false, aque.equals(o));
    }
}
