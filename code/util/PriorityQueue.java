package util;

import java.util.*;

public class PriorityQueue<T extends Comparable<T>> extends AbstractQueue<T> {

    private static final int MAX_SIZE = Integer.MAX_VALUE;

    // I could use an ArrayList but that would have higher overhead.
    private Object[] list;
    private int size;

    public PriorityQueue() {
        this.list = new Object[10];
        this.size = 0;
    }

    public PriorityQueue(int initialCapacity) {
        this.list = new Object[initialCapacity];
        this.size = 0;
    }

    public PriorityQueue(Collection<T> objects) {
        this.list = new Object[Math.max(objects.size() + 1, 10)];
        this.size = objects.size();
        // There might be a quicker way to do this. Maybe turning the collection into an array and then heapifying
        // that array rather than adding objects 1 by 1. However this is probably the easiest way to implement this.
        for (T o : objects) {
            offer(o);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Using {@link PriorityQueue#add(Object)} will call offer. It is already implemented in
     * {@link AbstractQueue#add(Object)} so I do not need to override it.
     * @param t The element to be added
     * @return True if the element has been added and false if it has not been added.
     */
    @Override
    public boolean offer(T t) {
        if (t == null) {
            // Null will mess this implementation up because it does not implement comparable
            throw new NullPointerException("Null values can not be added to queue");
        }
        if (list.length <= size) {
            // Double the size of the array.
            list = Arrays.copyOf(list, Math.min(list.length * 2, MAX_SIZE));
        }
        // Check if greater than the max size and if the max size is Integer.MAX_VALUE check if its less than 0
        // and therefore overflowed.
        if (size >= MAX_SIZE || size < 0) {
            return false;
        }
        list[size] = t;
        if (size != 0) {
            heapify(0);
        }

        int i = size;
        while (i != 0 && ((T) list[i]).compareTo((T) list[parent(i)]) < 0) {
            Object s = list[i];
            list[i] = list[parent(i)];
            list[parent(i)] = s;
            i = parent(i);
        }

        size++;
        return true;
    }

    /**
     * Recursive implementation of heapify
     */
    private void heapify(int i) {
        int l = left(i), r = right(i);
        int smallest = i;
        if (l < size && ((T) list[l]).compareTo((T) list[smallest]) < 0) {
            smallest = l;
        }
        if (r < size && ((T) list[r]).compareTo((T) list[smallest]) < 0) {
            smallest = r;
        }

        if (smallest != i) {
            // swap list[i] and list[smallest]
            Object t = list[i];
            list[i] = list[smallest];
            list[smallest] = t;
            heapify(smallest);
        }
    }

    /**
     * Helper function. A heap can be represented as either a binary tree or an array. When representing as an array
     * the left node to the current one will be 2 * i + 1 (i is the position of the parent in the array). This
     * function is not needed but makes coding this easier.
     * @return The position of the left child in the array.
     * @see {@link #right(int)} or {@link #parent(int)}
     */
    private int left(int i) {
        return 2 * i + 1;
    }

    /**
     * Helper function. A heap can be represented as either a binary tree or an array. When representing as an array
     * the right node to the current one will be 2 * i + 2 (i is the position of the parent in the array). This
     * function is not needed but makes coding this easier.
     * @return The position of the right child in the array.
     * @see {@link #left(int)} or {@link #parent(int)}
     */
    private int right(int i) {
        return 2 * i + 2;
    }

    /**
     * Opposite of {@link #left(int)} and {@link #right(int)}
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    @Override
    public T poll() {
        if (size == 0) {
            return null;
        }
        size--;
        T peek = (T) list[0];
        list[0] = list[size];
        list[size] = null;
        heapify(0);
        return peek;
    }

    @Override
    public T peek() {
        return (T) list[0];
    }
}
