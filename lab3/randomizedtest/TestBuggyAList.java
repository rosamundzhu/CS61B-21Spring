package randomizedtest;

import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // TODO: YOUR TESTS HERE
    public void main(String[] args) {
        testThreeAddThreeRemove();
        randomCall();
    }

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        for (int i = 4; i <= 6; i ++) {
            correct.addLast(i);
            broken.addLast(i);
            assertEquals(correct.getLast(), broken.getLast());
        }
        for (int i = 0; i < 3; i ++) {
            assertEquals(correct.removeLast(), broken.removeLast());
        }
    }

    @Test
    public void randomCall() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        // for a total of N total calls to one of these functions.
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            // set the breakpoint "L.size() == 12"
            // Click resume, and the code will run until the condition of the breakpoint is met, i.e. the size is 12
            int operationNumber = StdRandom.uniform(0, 4); // returns a random integer in the range [0, 2)
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                assertEquals(L.getLast(), B.getLast());
                // System.out.println("addLast(" + randVal + ")");

            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = B.size();
                assertEquals(sizeL, sizeB);
                // System.out.println("size: " + sizeL);
            } else if (operationNumber == 2) {
                if (L.size() != 0 & B.size() != 0) {
                    L.getLast();
                    B.getLast();
                    assertEquals(L.getLast(), B.getLast());
                }
            } else if (operationNumber == 3) {
                if (L.size() != 0 & B.size() != 0) {
                    assertEquals(L.removeLast(), B.removeLast());
                }
            }
        }
    }





}
