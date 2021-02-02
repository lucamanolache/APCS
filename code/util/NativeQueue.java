package util;

import java.io.File;

public class NativeQueue implements AutoCloseable {

    private static native long newQueue();
    private static native void add(long pointer, double input);
    private static native double poll(long pointer);
    private static native double peek(long pointer);
    private static native void free(long pointer);
    private static native boolean isEmpty(long pointer);

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

    public double poll() {
        return poll(pointer);
    }

    public double peek() {
        return peek(pointer);
    }

    public boolean isEmpty() {
        return isEmpty(pointer);
    }

    @Override
    public void close() throws Exception {
        free(pointer);
    }
}
