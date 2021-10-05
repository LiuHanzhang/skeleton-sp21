package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {
    private static class reverseIntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    @Test
    public void intTest() {
        Comparator<Integer> comp = new reverseIntComparator();
        MaxArrayDeque<Integer> dq = new MaxArrayDeque<Integer>(comp);
        dq.addLast(10);
        dq.addLast(-100);
        dq.addLast(2041);
        dq.addFirst(123);
        assertEquals(-100, (int) dq.max());
    }
}
