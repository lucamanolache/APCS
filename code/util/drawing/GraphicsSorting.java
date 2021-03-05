package util.drawing;

import java.util.List;

public class GraphicsSorting {

    public static void mergeSort(Window window, List<Integer> list) {
        mergeSort(window, list, 0, list.size() - 1);
    }

    // might be easier to create an subclass of list with drawArray being called after every set TODO: investigate this
    private static void mergeSort(Window window, List<Integer> list, int s, int e) {
        if (e - s == 0) {
            window.drawArray(list);
            return;
        } else if (e - s == 1) {
            if (list.get(s).compareTo(list.get(e)) <= 0) {
                window.drawArray(list);
                return;
            } else {
                int h = list.get(s);
                list.set(s, list.get(e));
                list.set(e, h);
                window.drawArray(list);
                return;
            }
        }
        int split = s + (e - s + 1) / 2;
        mergeSort(window, list, s, split - 1);
        mergeSort(window, list, split, e);

        // merge
        var copy = list.subList(s, e + 1).toArray();
        for (int i = s, j = s, k = split; j < split || k < copy.length; i++) {
            if (j >= split) {
                list.set(i, (Integer) copy[k++ - s]);
                window.drawArray(list);
            } else if (k - s >= copy.length) {
                list.set(i, (Integer) copy[j++ - s]);
                window.drawArray(list);
            } else if (((Integer) copy[j - s]).compareTo((Integer) copy[k - s]) <= 0) {
                list.set(i, (Integer) copy[j++ - s]);
                window.drawArray(list);
            } else {
                list.set(i, (Integer) copy[k++ - s]);
                window.drawArray(list);
            }
        }
    }
}
