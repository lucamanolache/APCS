package util;

import java.util.ArrayList;
import java.util.List;

public class Sorting {

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

    public static void main(String[] args) {
        var list = new ArrayList(List.of(1, 2, 3, 8, 4, 5, 9, 6));
        threeWaySort(list);
        System.out.println(list.toString());
    }
}
