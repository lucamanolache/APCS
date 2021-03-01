package util;

import java.util.*;

/**
 * A priority queue that has a recursive heapify method. The heapify method only works if the heap has 1 error in it.
 * This is because it is used only when removing an element. Removing an element swaps the first and the last element
 * and deletes the first element (or the one that used to be first). This should have time complexity O (log n),
 * however it is still a lot slower than {@link java.util.PriorityQueue}. Creating a priority queue from a Collection
 * takes O(n).
 * @param <T> What value to be stored in the Array, needs to implement Comparable. Might have
 *           been a better choice to take a comparator or something and use that instead of Comparable
 *           because then you could sort both min and max heaps.
 */
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
        this.list = objects.toArray();
        this.size = objects.size();

        buildHeap();
    }

    /**
     * The Wikipedia page on Binary Heaps says this way should be n because of some complex math involving infinite
     * series. This should work. TODO: test this
     */
    private void buildHeap() {
        for (int i = size / 2; i >= 1; i--) {
            heapify(i);
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

    /**
     * Takes our the smallest element (which should always be the first) and then moves the last element to the start
     * and then heapifies the resulting array (or binary tree)
     * @return The smallest value in the array, that element is deleted from the priority queue. Returns null if
     * the queue is empty
     */
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

    /**
     * @return The smallest value in the array, that element is not deleted from the priority queue. Returns null if
     * the queue is empty. (does not do this explicitly but the array should be full of null's so list[0] should be
     * null)
     */
    @Override
    public T peek() {
        return (T) list[0];
    }
}
