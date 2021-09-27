package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> canon = new AListNoResizing<Integer>();
        BuggyAList<Integer> buggy = new BuggyAList<Integer>();
        int[] testSet = {4, 5, 6};
        for (int i : testSet) {
            canon.addLast(i);
            buggy.addLast(i);
        }
        for (int i : testSet)
            assertEquals(canon.removeLast(), buggy.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int Bsize = B.size();
                assertEquals(Lsize, Bsize);
            } else if (operationNumber == 2 && L.size() > 0) {
                int Lval = L.getLast();
                int Bval = B.getLast();
                assertEquals(Lval, Bval);
            } else if (operationNumber == 3 && L.size() > 0) {
                int Lval = L.removeLast();
                int Bval = B.removeLast();
                assertEquals(Lval, Bval);
            }
        }
    }
}
