package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import jh61b.junit.In;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    private String msgHelper(ArrayDequeSolution<String> ops) {
        // NOTE: Have to clone, because deque interface doesn't provide iterator
        ArrayDequeSolution<String> opsCopy = (ArrayDequeSolution<String>)ops.clone();
        StringBuilder msg = new StringBuilder();
        while(!opsCopy.isEmpty()) {
            String op = opsCopy.removeFirst();
            msg.append(op);
            msg.append('\n');
        }
        msg.deleteCharAt(msg.length() - 1);
        return msg.toString();
    }

    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> ops = new ArrayDequeSolution<>();

        int N = 500;
        int randVal;
        Integer valExpected;
        Integer valActual;
        for(int i = 0; i < N; ++i) {
            int opN = StdRandom.uniform(0, 4);
            switch (opN) {
                case 0 -> {
                    randVal = StdRandom.uniform(0, 100);
                    sad.addFirst(randVal);
                    ads.addFirst(randVal);
                    ops.addLast("addFirst(" + randVal + ')');
                }
                case 1 -> {
                    randVal = StdRandom.uniform(0, 100);
                    sad.addLast(randVal);
                    ads.addLast(randVal);
                    ops.addLast("addLast(" + randVal + ')');
                }
                case 2 -> {
                    ops.addLast("isEmpty()");
                    assertEquals(msgHelper(ops), ads.isEmpty(), sad.isEmpty());
                    ops.addLast("removeFirst()");
                    if (!sad.isEmpty()) {
                        valExpected = ads.removeFirst();
                        valActual = sad.removeFirst();
                        assertEquals(msgHelper(ops), valExpected, valActual);
                    }
                }
                case 3 -> {
                    ops.addLast("isEmpty()");
                    assertEquals(msgHelper(ops), ads.isEmpty(), sad.isEmpty());
                    ops.addLast("removeLast()");
                    if (!sad.isEmpty()) {
                        valExpected = ads.removeLast();
                        valActual = sad.removeLast();
                        assertEquals(msgHelper(ops), valExpected, valActual);
                    }
                }
            }
        }
    }
}
