package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {
    @Test
    public void testNative() {
        NativeQueue queue = new NativeQueue();
        java.util.PriorityQueue<Double> realQueue = new java.util.PriorityQueue<>();

        for (int i = 0; i < 100; i++) {
//            double random = Math.random() * 10 + 10;
            double random = i;
            queue.add(random);
            realQueue.add(random);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(queue.poll(), realQueue.poll());
        }
    }
}