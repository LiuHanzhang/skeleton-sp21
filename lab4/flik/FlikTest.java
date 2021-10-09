package flik;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class FlikTest {
    @Test
    public void test() {
        for(int i = 0; i < 100; i++) {
            Integer a = StdRandom.uniform(1000);
            Integer b = a;
            assertTrue(Flik.isSameNumber(a, b));
        }
        for(int i = 0; i < 100; i++) {
            int val = StdRandom.uniform(1000);
            Integer a = new Integer(val);
            Integer b = new Integer(val);
            assertTrue(Flik.isSameNumber(a, b));
        }
        for(int i = 0; i < 100; i++) {
            int val1 = StdRandom.uniform(0, 1000);
            int val2 = StdRandom.uniform(1001, 2000);
            Integer a = val1;
            Integer b = val2;
            assertFalse(Flik.isSameNumber(a, b));
        }
    }
}
