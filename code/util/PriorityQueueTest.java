package util;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

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

    @Benchmark
    public void benchmarkQueue() {
        PriorityQueue<Double> queue = new PriorityQueue<>();

        for (double i = 0; i < 1000; i++) {
            queue.add(i);
            queue.add(-i);
            queue.add(10.0);
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.peek();
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.poll();
        }
    }

    @Benchmark
    public void benchmarkNative() {
        NativeQueue queue = new NativeQueue();

        for (double i = 0; i < 1000; i++) {
            queue.add(i);
            queue.add(-i);
            queue.add(10.0);
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.peek();
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.poll();
        }
    }

    @Benchmark
    public void benchmarkJava() {
        java.util.PriorityQueue<Double> queue = new java.util.PriorityQueue<>();

        for (double i = 0; i < 1000; i++) {
            queue.add(i);
            queue.add(-i);
            queue.add(10.0);
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.peek();
        }

        for (int i = 0; i < 3 * 1000; i++) {
            queue.poll();
        }
    }

    /*
    Benchmark (higher is better)        Mode  Cnt     Score     Error  Units
    PriorityQueueTest.benchmarkJava    thrpt   25  1988.952 ± 212.518  ops/s
    PriorityQueueTest.benchmarkNative  thrpt   25    73.338 ±   6.897  ops/s
    PriorityQueueTest.benchmarkQueue   thrpt   25  1443.219 ± 130.674  ops/s

    This shows that Java's implementation of an PriorityQueue is the fastest. I expected the native code to do
    extremely poorly because I heard that JNI is rather slow. What I believe would be interesting to see, is how
    do PriorityQueue's implemented in other languages perform (not using the JNI). I do not know how to
    benchmark code in different languages though. I am also interested in what algorithm Java's implementation uses.
    I recall reading that it was also a heap but I am not sure if that is true, however I am fairly confident that
    Java's queue has O(1) read time and O(log n) poll and offer times. I am also fairly confident that my own code
    has O(1) read time and O(log n) poll and offer times as well so I wonder what makes their code faster.
     */
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}