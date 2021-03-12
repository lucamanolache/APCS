package util;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

    private static final int benchmarkIterations = 100000;

    @Test
    public void testNative() {
        // should not be that necessary because I also test the code in rust
        NativeQueue queue = new NativeQueue();
        java.util.PriorityQueue<Double> realQueue = new java.util.PriorityQueue<>();

        for (int i = 0; i < 100; i++) {
            double random = Math.random() * 10 + 10;
            queue.add(random);
            realQueue.add(random);
        }

        assertFalse(queue.isEmpty());
        for (int i = 0; i < 100; i++) {
            assertEquals(realQueue.peek(), queue.peek());
            assertEquals(realQueue.poll(), queue.poll());
            assertEquals(realQueue.size(), queue.size());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Double> queue = new PriorityQueue<>();
        java.util.PriorityQueue<Double> realQueue = new java.util.PriorityQueue<>();

        for (int i = 0; i < 100; i++) {
            double random = Math.random() * 10 + 10;
            queue.add(random);
            realQueue.add(random);
        }

        assertFalse(queue.isEmpty());
        for (int i = 0; i < 100; i++) {
            assertEquals(realQueue.peek(), queue.peek());
            assertEquals(realQueue.poll(), queue.poll());
            assertEquals(realQueue.size(), queue.size());
        }
        assertTrue(queue.isEmpty());
    }

//    @Benchmark
//    public void benchmarkQueue() {
//        PriorityQueue<Double> queue = new PriorityQueue<>();
//
//        for (double i = 0; i < benchmarkIterations; i++) {
//            queue.add(i);
//            queue.add(-i);
//            queue.add(10.0);
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.peek();
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.poll();
//        }
//    }

//    @Benchmark
//    public void benchmarkNative() {
//        NativeQueue queue = new NativeQueue();
//
//        for (double i = 0; i < benchmarkIterations; i++) {
//            queue.add(i);
//            queue.add(-i);
//            queue.add(10.0);
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.peek();
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.poll();
//        }
//    }

//    @Benchmark
//    public void benchmarkJava() {
//        java.util.PriorityQueue<Double> queue = new java.util.PriorityQueue<>();
//
//        for (double i = 0; i < benchmarkIterations; i++) {
//            queue.add(i);
//            queue.add(-i);
//            queue.add(10.0);
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.peek();
//        }
//
//        for (int i = 0; i < 3 * benchmarkIterations; i++) {
//            queue.poll();
//        }
//    }

    /*
    (benchmarkIterations = 1000)
    Benchmark (higher is better)        Mode  Cnt     Score     Error  Units
    PriorityQueueTest.benchmarkJava    thrpt   25  1988.952 ± 212.518  ops/s
    PriorityQueueTest.benchmarkNative  thrpt   25    73.338 ±   6.897  ops/s
    PriorityQueueTest.benchmarkQueue   thrpt   25  1443.219 ± 130.674  ops/s

    (benchmarkIterations = 100000) - this was done on a different computer than the prev test so score should be "better"
    Benchmark (higher is better)        Mode  Cnt     Score     Error  Units
    PriorityQueueTest.benchmarkJava    thrpt   25  13.250 ± 0.206  ops/s
    PriorityQueueTest.benchmarkNative  thrpt   25   0.793 ± 0.001  ops/s
    PriorityQueueTest.benchmarkQueue   thrpt   25   7.800 ± 0.193  ops/s

    This shows that Java's implementation of an PriorityQueue is the fastest. I expected the native code to do
    poorly because I heard that JNI is slow but not this poorly. What I believe would be interesting to see, is how
    do PriorityQueue's implemented in other languages perform (not using the JNI). I do not know how to
    benchmark code in different languages though. I am also interested in what algorithm Java's implementation uses.
    I recall reading that it was also a heap but I am not sure if that is true, however I am fairly confident that
    Java's queue has O(1) read time and O(log n) poll and offer times. I am also fairly confident that my own code
    has O(1) read time and O(log n) poll and offer times as well so I wonder what makes their code faster.
    After running the test again with a higher n, the score for everything went down, however the differences between
    each test increased. When doing ratios (idk if this is a proper thing to do), I found that when n = 1000 java's
    implementation is 137% better than my implementation. When n = 1000000 java's implementation is 169% better than mine.
    When comparing my implementation in Java and my implementation using the JNI, the java implementation was 1967%
    better when n = 1000, however when n = 1000000, the java implementation is only 983% better. If I were to code
    the native portion of the priority queue in a different compiled language like C instead of Rust I might see an
    improvement in its performance because the languages might work better together however I doubt that will happen.
     */
    public static void main(String[] args) throws Exception {
        ArgumentParser parser = ArgumentParsers.newFor("PriorityQueue").build()
                .defaultHelp(true)
                .description("Use or benchmark my implementation of a priority queue.\n" +
                        "Warning: benchmark takes over 20 minutes.");
        parser.addArgument("task")
                .dest("task")
                .help("What task should this program complete")
                .choices("benchmark", "use")
                .nargs(1);

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        assert(ns != null);

        String task = ns.getString("task");
        switch (task) {
            case "[benchmark]": benchmark(); break;
            case "[use]": use(); break;
        }
    }

    private static void use() {
        Scanner in = new Scanner(System.in);

        // This is my priority queue not java's. They both have the same name.
        // Java's would be java.util.PriorityQueue<Double>
        PriorityQueue<Double> queue = new PriorityQueue<>();

        System.out.println("How to use");
        System.out.println("==========");
        System.out.println("Type a or add along with a value to add it to the priority queue.");
        System.out.println("Type r or poll in order to remove and view the top value from the priority queue");
        System.out.println("Type p or peek in order view the top value from the priority queue");
        System.out.println("Type q or quit or ^c in order to stop the program");
        String next;
        while (true) {
            next = in.next();

            switch (next) {
                case "a":
                case "add":
                    queue.add(in.nextDouble());
                    break;
                case "r":
                case "poll":
                    System.out.println(queue.poll());
                    break;
                case "p":
                case "peek":
                    System.out.println(queue.peek());
                    break;
                case "q":
                case "quit":
                    System.exit(0);
            }
        }
    }

    private static void benchmark() throws IOException {
        String[] args = new String[0];
        org.openjdk.jmh.Main.main(args);
    }
}