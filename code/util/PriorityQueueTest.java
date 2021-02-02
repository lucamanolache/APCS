package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {

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
}