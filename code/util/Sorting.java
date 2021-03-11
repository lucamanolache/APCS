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
                if (s3 < s + sp1 + sp2 + sp3 && ((T) hlist[s3 - s]).compareTo(min) <= 0) {
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
            for (int i = s + 1; i <= e; ++i) {
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

    private static <T extends Comparable<? super T>> void insertionSort(List<T> list, int lo, int hi) {
        for (int i = lo + 1; i <= hi; ++i) {
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
    }

    public static <T extends Comparable<? super T>> void heapSort(List<T> list) {
        for (int i = list.size() / 2; i >= 1; i--) {
            heapify(i, list, list.size());
        }
        heapify(0, list, list.size());
        for (int i = list.size() - 1; i >= 0; i--) {
            Object temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, (T) temp);
            heapify(0, list, i);
        }

        // reverse
        for (int i = 0; i < list.size() / 2; i++) {
            Object temp = list.get(i);
            list.set(i, list.get(list.size() - i - 1));
            list.set(list.size() - i - 1, (T) temp);
        }
    }

    /**
     * Recursive implementation of heapify
     */
    private static <T extends Comparable<? super T>> void heapify(int i, List<T> list, int size) {
        int l = 2 * i + 1, r = 2 * i + 2;
        int smallest = i;
        if (l < size && ((T) list.get(l)).compareTo((T) list.get(smallest)) < 0) {
            smallest = l;
        }
        if (r < size && ((T) list.get(r)).compareTo((T) list.get(smallest)) < 0) {
            smallest = r;
        }

        if (smallest != i) {
            // swap list[i] and list[smallest]
            Object t = list.get(i);
            list.set(i, list.get(smallest));
            list.set(smallest, (T) t);
            heapify(smallest, list, size);
        }
    }

    public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<? super T>> void quickSort(List<T> list, int lo, int hi) {
        if (hi <= lo) return;

        int p = partitionNinther(list, lo, hi);
        quickSort(list, lo, p - 1);
        quickSort(list, p + 1, hi);
    }

    // Lomuto partition scheme (https://en.wikipedia.org/wiki/Quicksort). According to wikipedia, Sedgewick recommends
    // choosing the median of the first, middle and last element for the pivot so I changed it to use this instead of the
    // last index. Might want to try some of the other suggestions such as using insertion sort for small partitions
    // because quick sort is O ( N^2 ) for sorted arrays. Also according to wikipedia, one can partition it into
    // less than, equal to, and greater than the partition. This is used in qsort (stdlib). Probably should use insertion
    // sort at less than 10 or something small. This might speed it up, also should check what Java's tim sort does in
    // small situations such as these and if they do something similar do that. For this and merge sort making it
    // parallel might make it a lot faster. IIRC, java uses quick sort with 2 partitions on primitives, so might want
    // to try working on that. After testing around with this and the basic one, this is a lot faster.
    private static <T extends Comparable<? super T>> int partitionWikipedia(List<T> list, int lo, int hi) {
//        T pivot = list.get(hi); // last element
        int mid = (lo + hi) / 2;
        // slightly confused by what this part does, but if wikipedia says to do this ¯\_(ツ)_/¯
        if (list.get(mid).compareTo(list.get(lo)) < 0) {
            T h = list.get(lo);
            list.set(lo, list.get(mid));
            list.set(mid, h);
        }
        if (list.get(hi).compareTo(list.get(lo)) < 0) {
            T h = list.get(lo);
            list.set(lo, list.get(hi));
            list.set(hi, h);
        }
        if (list.get(mid).compareTo(list.get(hi)) < 0) {
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

    private static <T extends Comparable<? super T>> int partitionBasic(List<T> list, int lo, int hi) {
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

    /**
     * This uses Tukey's ninther. When I was reading about different sorting algorithms, this came up in how to choose
     * the pivot. What Tukey's ninther does is find an approximation of the median in a large dataset.
     */
    private static <T extends  Comparable<? super T>> int partitionNinther(List<T> list, int lo, int hi) {
        int size = hi - lo + 1;
        int mid = lo + size / 2;
        int delta = size / 8;
        int median1 = median3(list, lo, lo + delta, lo + 2 * delta);
        int median2 = median3(list, mid - delta, mid, mid + delta);
        int median3 = median3(list, hi - 2 * delta, hi - delta, hi);
        int median = median3(list, median1, median2, median3);

        T h = list.get(hi);
        list.set(hi, list.get(median));
        list.set(median, h);
        T pivot = list.get(hi);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (list.get(j).compareTo(pivot) < 0) {
                h = list.get(i);
                list.set(i, list.get(j));
                list.set(j, h);
                i++;
            }
        }
        h = list.get(i);
        list.set(i, list.get(hi));
        list.set(hi, h);
        return i;
    }

    private static <T extends Comparable<? super T>> int median3(List<T> l, int a, int b, int c) {
        if (l.get(a).compareTo(l.get(b)) < 0) {
            // a < b; therefore median is b if b < c or c if c < b
            return l.get(b).compareTo(l.get(c)) < 0 ? b : l.get(a).compareTo(l.get(c)) < 0 ? c : a;
        } else {
            // a > b; therefore median is a if a < c or c if c < a
            return l.get(a).compareTo(l.get(c)) < 0 ? a : l.get(c).compareTo(l.get(b)) < 0 ? b : c;
        }
    }

    // https://en.wikipedia.org/wiki/Introsort, seemed like an interesting implementation. Works by using quicksort at the
    // start, then switching to heap sort. Also, I'm pretty sure that std::sort (C++) uses this.
    public static <T extends Comparable<? super T>> void introsort(List<T> list) {
        introsort(list, 0, list.size() - 1, (int) (2 * Math.log(list.size()) / Math.log(2)));
    }

    private static <T extends Comparable<? super T>> void introsort(List<T> list, int lo, int hi, int maxdepth) {
        int n = hi - lo;
        if (n <= 8) {
            insertionSort(list, lo, hi);
        } else if (maxdepth == 1) {
            heapSort(list.subList(lo, hi + 1));
        } else {
            int p = partitionNinther(list, lo, hi);
            introsort(list, lo, p - 1, maxdepth - 1);
            introsort(list, p + 1, hi, maxdepth - 1);
        }
    }

    public static void main(String[] args) {
        var list = new ArrayList(List.of(7, 0, 1, 2, 3, 8, 4, 5, 9, 6, 10, -1, 321, -12, 5000, -1));
        heapSort(list);
        System.out.println(list.toString());
    }
}
