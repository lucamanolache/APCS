package util;

import java.util.ArrayList;
import java.util.List;

public class Sorting {

    // All of these have the same inputs as Collections.sort().

    public static <T extends Comparable<? super T>> void threeWaySort(List<T> list) {
        threeWaySort(list, 0, list.size());
    }

    public static <T extends Comparable<? super T>> void threeWaySort(List<T> list, int s, int e) {
        if (e - s == 1) {
            return;
        }
        if (e - s == 2) {
            T h = list.get(s);
            list.set(s, list.get(s).compareTo(list.get(e)) <= 0 ? list.get(s) : list.get(e));
            if (list.get(s) != h) {
                list.set(e, h);
            }
            return;
        }

        int si = e - s;
        int sp1, sp2, sp3;
        sp1 = si / 3;
        si -= sp1;
        sp2 = si / 2;
        si -= sp2;
        sp3 = si;

        threeWaySort(list, s, s + sp1);
        threeWaySort(list, s + sp1, s + sp1 + sp2);
        threeWaySort(list, s + sp1 + sp2, s + sp1 + sp2 + sp3);

        // merge
        int s1 = s;
        int s2 = s + sp1;
        int s3 = s + sp1 + sp2;

        var hlist = list.subList(s, e).toArray();
        for (int i = s; i < e; i++) {
            int l;
            if (s1 < s + sp1) {
                l = s1;
            } else if (s2 < s + sp1 + sp2) {
                l = s2;
            } else {
                l = s3;
            }
            T min = (T) hlist[l - s];
            if (((T) hlist[s2 - s]).compareTo(min) <= 0 && s2 < s + sp1 + sp2) {
                min = (T) hlist[s2 - s];
                if (s3 < s + sp1 + sp2 + sp3 && ((T) hlist[s3 - s]).compareTo(min) <= 0 ) {
                    min = (T) hlist[s3 - s];
                    s3++;
                } else {
                    s2++;
                }
            } else if (s3 < s + sp1 + sp2 + sp3 && ((T) hlist[s3 - s]).compareTo(min) <= 0) {
                min = (T) hlist[s3 - s];
                s3++;
            } else {
                s1++;
            }
            list.set(i, min);
        }
    }

    public static <T extends Comparable<? super T>> void mergeSort(List<T> list) {
        mergeSort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<? super T>> void mergeSort(List<T> list, int s, int e) {
        if (e - s == 0) {
            return;
        } else if (e - s == 1) {
            if (list.get(s).compareTo(list.get(e)) <= 0) {
                return;
            } else {
                T h = list.get(s);
                list.set(s, list.get(e));
                list.set(e, h);
                return;
            }
        }
        int split = s + (e - s + 1) / 2;
        mergeSort(list, s, split - 1);
        mergeSort(list, split, e);

        // merge
        var copy = list.subList(s, e + 1).toArray();
        for (int i = s, j = s, k = split; j < split || k < copy.length; i++) {
            if (j >= split) {
                list.set(i, (T) copy[k++ - s]);
            } else if (k - s >= copy.length) {
                list.set(i, (T) copy[j++ - s]);
            } else if (((T) copy[j - s]).compareTo((T) copy[k - s]) <= 0) {
                list.set(i, (T) copy[j++ - s]);
            } else {
                list.set(i, (T) copy[k++ - s]);
            }
        }
    }

    private static <T extends Comparable<? super T>> void mergeInsertSort(List<T> list, int s, int e) {
        if (e - s <= 10) {
            // might want to try bubble sort instead.
            int n = e - s;
            for (int i = 1; i < n; ++i) {
                T t = list.get(i);
                int j = i - 1;

                while (j >= 0 && list.get(j).compareTo(t) > 0) {
                    T h = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, h);
                    j--;
                }
                list.set(j + 1, t);
            }
        } else {
            int split = s + (e - s + 1) / 2;
            mergeInsertSort(list, s, split - 1);
            mergeInsertSort(list, split, e);

            // merge
            var copy = list.subList(s, e + 1).toArray();
            for (int i = s, j = s, k = split; j < split || k < copy.length; i++) {
                if (j >= split) {
                    list.set(i, (T) copy[k++ - s]);
                } else if (k - s >= copy.length) {
                    list.set(i, (T) copy[j++ - s]);
                } else if (((T) copy[j - s]).compareTo((T) copy[k - s]) <= 0) {
                    list.set(i, (T) copy[j++ - s]);
                } else {
                    list.set(i, (T) copy[k++ - s]);
                }
            }
        }
    }

    public static <T extends Comparable<? super T>> void heapSort(List<T> list) {
        PriorityQueue<T> queue = new PriorityQueue<>(list); // my implementation of a queue/binary heap
        for (int i = 0; i < list.size(); i++) {
            list.set(i, queue.poll());
        }
    }

    public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(List<T> list, int lo, int hi) {
        if (lo < hi) {
            int p = partition(list, lo, hi);
            quickSort(list, lo, p - 1);
            quickSort(list, p + 1, hi);
        }
    }

    // Lomuto partition scheme (https://en.wikipedia.org/wiki/Quicksort). According to wikipedia, Sedgewick recommends
    // choosing the median of the first, middle and last element for the pivot so I changed it to use this instead of the
    // last index. Might want to try some of the other suggestions such as using insertion sort for small partitions
    // because quick sort is O ( N^2 ) for sorted arrays. Also according to wikipedia, one can partition it into
    // less than, equal to, and greater than the partition. This is used in qsort (stdlib). Probably should use insertion
    // sort at less than 10 or something small. This might speed it up, also should check what Java's tim sort does in
    // small situations such as these and if they do something similar do that. For this and merge sort making it
    // parallel might make it a lot faster. IIRC, java uses quick sort with 2 partitions on primitives, so might want
    // to try and work on that however.
    private static <T extends  Comparable<? super T>> int partition(List<T> list, int lo, int hi) {
//        T pivot = list.get(hi); // last element
        int mid = (lo + hi) / 2; // slightly confused by what this does, but if wikipedia says to do this ¯\_(ツ)_/¯
        if (list.get(mid).compareTo(list.get(lo)) < 0) {
            T h = list.get(lo);
            list.set(lo, list.get(mid));
            list.set(mid, h);
        } if (list.get(hi).compareTo(list.get(lo)) < 0) {
            T h = list.get(lo);
            list.set(lo, list.get(hi));
            list.set(hi, h);
        } if (list.get(mid).compareTo(list.get(hi)) < 0) {
            T h = list.get(mid);
            list.set(mid, list.get(hi));
            list.set(hi, h);
        }
        // I get the rest of this part though :)
        T pivot = list.get(hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (list.get(j).compareTo(pivot) < 0) {
                T h = list.get(i);
                list.set(i, list.get(j));
                list.set(j, h);
                i++;
            }
        }
        T h = list.get(i);
        list.set(i, list.get(hi));
        list.set(hi, h);
        return i;
    }

    // https://en.wikipedia.org/wiki/Introsort, seemed like an intresting implementation. Works by using quicksort at the
    // start, then switching to merge sort, and finally insertion sort. Also, I'm pretty sure that std::sort (C++) uses this.
    public static <T extends Comparable<? super T>> void introsort(List<T> list) {
        introsort(list, 0, list.size() - 1, (int) Math.log(list.size()) * 2);
    }

    private static <T extends Comparable<? super T>> void introsort(List<T> list, int lo, int hi, int maxdepth) {
        int n = hi - lo;
        if (n <= 0) {
        } else if (maxdepth == 0) {
            mergeInsertSort(list, lo, hi);
        } else {
            int p = partition(list, lo, hi);
            introsort(list, 0, p - 1, maxdepth - 1);
            introsort(list, p + 1, n, maxdepth - 1);
        }
    }

    public static void main(String[] args) {
        var list = new ArrayList(List.of(7, 0, 1, 2, 3, 8, 4, 5, 9, 6));
        mergeInsertSort(list, 0, list.size());
        System.out.println(list.toString());
    }
}
