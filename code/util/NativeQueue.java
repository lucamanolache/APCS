package util;

import java.io.File;

public class NativeQueue {

    private static native long newQueue();
    private static native void add(long pointer, double input);
    private static native double pop(long pointer);
    private static native double poll(long pointer);

    private final long pointer;

    static {
        System.load(new File("lib/priority_queue/target/debug/libpriority_queue.dylib").getAbsolutePath());
    }

    public NativeQueue() {
        pointer = newQueue();
    }

    public void add(double input) {
        add(pointer, input);
    }

    public double pop() {
        return pop(pointer);
    }

    public double poll() {
        return poll(pointer);
    }

    public static void main(String[] args) {
        NativeQueue queue = new NativeQueue();

        queue.add(1);
        queue.add(4);
        queue.add(1);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
}
