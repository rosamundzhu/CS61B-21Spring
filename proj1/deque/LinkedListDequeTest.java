package deque;
import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque();

		assertTrue("should be empty", lld1.isEmpty());
        lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

        lld1.addLast(10);
        lld1.removeLast();
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        // System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        assertEquals("should be equal to zero", 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

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
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        // System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void twoGetEqual() {
        LinkedListDeque<String> lld1 = new LinkedListDeque();

        lld1.addFirst("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertEquals(lld1.getRecursive(0), lld1.get(0));
        assertEquals(lld1.getRecursive(1), lld1.get(1));
        assertEquals(lld1.getRecursive(2), lld1.get(2));

    }

    @Test
    public void testIterator() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque();
        lld1.addFirst(5);
        lld1.addFirst(5);
        lld1.addFirst(5);

        for (int i: lld1) {
            assertEquals("Should have 5", 5, i, 0.0);
        }
    }
    @Test
    public void testEqual() {
        LinkedListDeque lld1 = new LinkedListDeque<>();
        lld1.addFirst(5);
        lld1.addFirst("long");
        lld1.addFirst(5);

        LinkedListDeque lld2 = new LinkedListDeque<>();
        lld2.addFirst(5);
        lld2.addFirst("long");
        lld2.addFirst(5);

        LinkedListDeque o = new LinkedListDeque<>();
        o.addFirst(5);
        o.addFirst("long");
        o.addFirst(6);
        o.removeLast();
        o.removeLast();
        o.removeFirst();

        assertEquals("equal", false, lld1.equals(o));

        LinkedListDeque o2 = new LinkedListDeque<>();
        o2.addFirst(5);
        o2.addFirst(lld1);
        o2.addFirst("short");

        LinkedListDeque o3 = new LinkedListDeque<>();
        o3.addFirst(5);
        o3.addFirst(lld2);
        o3.addFirst("short");

        assertEquals("not equal", true, o2.equals(o3));
    }
}
