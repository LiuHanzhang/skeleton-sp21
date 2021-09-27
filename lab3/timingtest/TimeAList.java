package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    private static boolean watch(int N, int[] watchN) {
        for (int Ns : watchN) {
            if (N == Ns)
                return true;
        }
        return false;
    }

    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<Integer>();
        AList<Double> times = new AList<Double>();
        AList<Integer> opCounts = new AList<Integer>();
        int[] watchN = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        AList<Integer> testee = new AList<Integer>();
        Stopwatch sw = new Stopwatch();

        for (int i = 1; i <= watchN[watchN.length - 1]; i++) {
            testee.addLast(1);
            if (watch(i, watchN)) {
                Ns.addLast(i);
                times.addLast(sw.elapsedTime());
                opCounts.addLast(i);
            }
        }
        printTimingTable(Ns, times, opCounts);
    }
}
