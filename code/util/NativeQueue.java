package util;

import java.io.File;

public class NativeQueue implements AutoCloseable {

    private static native long newQueue();
    private static native void add(long pointer, double input);
    private static native double poll(long pointer);
    private static native double peek(long pointer);
    private static native void free(long pointer);
    private static native boolean isEmpty(long pointer);
    private static native int size(long pointer);

    private final long pointer;

    static {
        // The lib must be compiled for this to run. target is in my .gitignore because Maven also uses that directory
        // and I do not want Java byte code to be on github. I do not remember how to allow these files to be ignored
        // by the gitignore so they will have to be compiled on every computer at least once. To do this
        // `cd` into ./lib/priority_queue and run `cargo build`
        if (System.getProperty("os.name").contains("Windows")) {
            System.load(new File("lib/priority_queue/target/debug/libpriority_queue.dll").getAbsolutePath());
        } else if (System.getProperty("os.name").contains("Linux")) {
            System.load(new File("lib/priority_queue/target/debug/libpriority_queue.so").getAbsolutePath());
        } else {
            // This is for Mac OS, however BSD and other OS's probably don't use dylib so they won't work
            System.load(new File("lib/priority_queue/target/debug/libpriority_queue.dylib").getAbsolutePath());
        }
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

    public int size() {
        return size(pointer);
    }

    @Override
    public void close() {
        free(pointer);
    }
}
