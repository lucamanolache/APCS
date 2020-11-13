import java.util.*;

/**
 * Implementation of an {@link java.util.ArrayList}. Not all of the methods are implemented because I am not good at
 * using iterators.
 */
public class BarryList<T> implements List<T> {

    private static final int DEFAULT_SIZE = 10; // Using 10, because that is the default size in ArrayLists

    private Object[] arr;
    private int size;

    public BarryList() {
        this(DEFAULT_SIZE);
    }

    public BarryList(int initialSize) {
        arr = new Object[initialSize];
        size = 0;
    }

    private BarryList(Object[] arr, int size) {
        this.arr = arr;
        this.size = size;
    }

    private void optimize() {
        for (int i = 0; i < size - 1; i++) {
            if (arr[i] == null) {
                // no need to check for out of bounds exception because we know that we are under size - 1
                arr[i] = arr[i + 1];
                arr[i + 1] = null;
            }
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public T next() {
                current++;
                return (T) arr[current - 1];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return arr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null; // TODO: read the docs on what this is supposed to do
    }

    @Override
    public boolean add(T t) {
        if (arr.length == size) {
            // The length of our BarryList is the size of our actual data array
            arr = Arrays.copyOf(arr, arr.length * 2); // doubling our data array's size
        }
        arr[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                arr[i] = null;
                optimize();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // TODO: read the docs on what this is supposed to do
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T o : c) {
            add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Object[] ca = c.toArray();
        for (int i = 0; i < ca.length; i++) {
            add(index + i, (T) ca[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (int i = 0; i < size; i++) {
            if (c.contains(arr[i])) {
                arr[i] = null;
            }
        }
        optimize();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i = 0; i < size; i++) {
            if (!c.contains(arr[i])) {
                arr[i] = null;
            }
        }
        optimize();
        return false;
    }

    @Override
    public void clear() {
        arr = new Object[size];
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != BarryList.class) {
            return false;
        }
        if (((BarryList<?>) o).size != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!((BarryList<?>) o).get(i).equals(arr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d is larger than size %d", index, size));
        }
        return (T) arr[index];
    }

    @Override
    public T set(int index, T element) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Index %d is larger than size %d", index, size));
        }
        T hold = (T) arr[index];
        arr[index] = element;
        return hold;
    }

    @Override
    public void add(int index, T element) {
        size++;
        if (size >= arr.length) {
            arr = Arrays.copyOf(arr, arr.length * 2); // doubling our data array's size
        }

        T hold = (T) arr[index];
        T temp;
        if (hold == null) {
            arr[index] = element;
            return;
        }
        for (int i = index + 1; i < size; i++) {
            // no need to check if out of bounds because we did so at the start
            temp = (T) arr[i];
            arr[i] = hold;
            hold = temp;
        }
        arr[index] = element;
    }

    @Override
    public T remove(int index) {
        T hold = (T) arr[index];
        arr[index] = null;
        optimize();
        return hold;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1; // not found
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i++) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1; // not found
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new BarryList<T>(Arrays.copyOfRange(arr, fromIndex, toIndex), toIndex - fromIndex);
    }

    public static void main(String[] args) {
        BarryList<Integer> list = new BarryList<>();

        list.add(1);
        list.add(3);
        list.add(5);
        list.add(10);
        list.add(-1);

        System.out.println(list.get(4));
    }

    /**
     * Overriding the sort method to implement merge sort instead of tim sort.
     * @param c
     */
    @Override
    public void sort(Comparator<? super T> c) {
    }
}
