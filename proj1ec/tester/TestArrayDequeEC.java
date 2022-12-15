package tester;
import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void test1() {
        ArrayDequeSolution<Integer> sad1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad2 = new StudentArrayDeque<>();
        int size = 0;
        for (int i = 0; i < 100; i += 1) {
            double zToo = StdRandom.uniform();
            if (zToo < 0.25) {
                sad1.addLast(i);
                sad2.addLast(i);
                assertEquals("should be equal",sad1.get(size), sad2.get(size));
                size += 1;
            } else if (zToo < 0.5) {
                sad1.addFirst(i);
                sad2.addFirst(i);
                assertEquals("should be equal", sad1.get(size), sad2.get(size));
                size += 1;
            } else if (zToo < 0.75 && size > 0) {
                assertEquals("should be equal",sad1.removeFirst(), sad2.removeFirst());
                size -= 1;
            } else if (zToo < 1 && size > 0) {
                assertEquals("should be equal",sad1.removeLast(), sad2.removeLast());
                size -= 1;
            }
        }
    }

    @Test
    public void test2() {
        ArrayDequeSolution<Integer> sad1 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad2 = new StudentArrayDeque<>();
        int size = 0;
        for (int i = 0; i < 100; i += 1) {
            double zToo = StdRandom.uniform();
            if (zToo < 0.75) {
                sad1.addLast(i);
                sad2.addLast(i);
                assertEquals("should be equal",sad1.get(size), sad2.get(size));
                size += 1;
            } else if (zToo < 1 && size > 0) {
                size -= 1;
                assertEquals("should be equal",sad1.removeFirst(), sad2.removeFirst());
            }
        }
    }

}
