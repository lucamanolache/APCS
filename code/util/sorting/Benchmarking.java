package util.sorting;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Benchmarking {

    @State(Scope.Benchmark)
    public static class BenchmarkList {

        public ArrayList<Integer> list;
        public ArrayList<Integer> toSort;

        @Param({ "10", "100", "1000", "10000", "100000", "1000000", "10000000"})
        public int size;

        @Setup(Level.Trial)
        public void setUp() {
            list = new ArrayList<>();
            toSort = new ArrayList<>(list.size());
            for (int i = 0; i < size; i++) {
                list.add(i);
                toSort.add(i);
            }
            Collections.shuffle(list);
        }

        @Setup(Level.Invocation)
        public void makeArrayCopy() {
            Collections.copy(toSort, list);
        }

    }

    @Benchmark @BenchmarkMode({Mode.AverageTime})
    public void benchmarkQSort(BenchmarkList list) {
        Sorting.quickSort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime})
    public void benchmarkIntroSort(BenchmarkList list) {
        Sorting.introsort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime})
    public void benchmarkMergeSort(BenchmarkList list) {
        Sorting.mergeSort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime})
    public void benchmarkJavaSort(BenchmarkList list) {
        Collections.sort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime})
    public void benchmarkHeapSort(BenchmarkList list) {
        Sorting.heapSort(list.toSort);
    }

    public static void main(String[] args) throws IOException, RunnerException {
//        org.openjdk.jmh.Main.main(args);

        Benchmarking benchmarking = new Benchmarking();
        BenchmarkList list = new BenchmarkList();
        list.size = 100000000;
        list.setUp();
        // benchmarkHeapSort
        double averageHeapSort = 0.0;
        for (int i = 0; i < 3; i++) {
            list.makeArrayCopy();
            long time = System.currentTimeMillis();
            benchmarking.benchmarkHeapSort(list);
            time = System.currentTimeMillis() - time;
            System.out.println("Heapsort on n=100000000 took: " + time + "ms");
            averageHeapSort += time;
        }
        averageHeapSort /= 3;
        System.out.println("Average heapsort took " + averageHeapSort);
        // benchmarkQSort
        double averageQSort = 0.0;
        for (int i = 0; i < 3; i++) {
            list.makeArrayCopy();
            long time = System.currentTimeMillis();
            benchmarking.benchmarkQSort(list);
            time = System.currentTimeMillis() - time;
            System.out.println("QSort on n=100000000 took: " + time + "ms");
            averageQSort += time;
        }
        averageQSort /= 3;
        System.out.println("Average qsort took " + averageHeapSort);
        // benchmarkIntroSort
        double averageIntroSort = 0.0;
        for (int i = 0; i < 3; i++) {
            list.makeArrayCopy();
            long time = System.currentTimeMillis();
            benchmarking.benchmarkIntroSort(list);
            time = System.currentTimeMillis() - time;
            System.out.println("IntroSort on n=100000000 took: " + time + "ms");
            averageIntroSort += time;
        }
        averageIntroSort /= 3;
        System.out.println("Average introsort took " + averageHeapSort);
        // benchmarkMergeSort
        double averageMergeSort = 0.0;
        for (int i = 0; i < 3; i++) {
            list.makeArrayCopy();
            long time = System.currentTimeMillis();
            benchmarking.benchmarkMergeSort(list);
            time = System.currentTimeMillis() - time;
            System.out.println("MergeSort on n=100000000 took: " + time + "ms");
            averageMergeSort += time;
        }
        averageMergeSort /= 3;
        System.out.println("Average mergesort took " + averageMergeSort);
        // benchmarkJavaSort
        double averageJavaSort = 0.0;
        for (int i = 0; i < 3; i++) {
            list.makeArrayCopy();
            long time = System.currentTimeMillis();
            benchmarking.benchmarkJavaSort(list);
            time = System.currentTimeMillis() - time;
            System.out.println("Collections.sort on n=100000000 took: " + time + "ms");
            averageJavaSort += time;
        }
        averageJavaSort /= 3;
        System.out.println("Average collections.sort took " + averageJavaSort);
    }
}
