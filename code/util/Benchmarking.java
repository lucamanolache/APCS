package util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 25, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Benchmarking {

    @State(Scope.Benchmark)
    public static class BenchmarkList {

        public ArrayList<Integer> list;
        public ArrayList<Integer> toSort;

        @Param({ "10", "100", "1000", "10000", "100000", "1000000", "10000000", "100000000", "1000000000" })
        public int size;

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < size; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
        }


        @Setup(Level.Invocation)
        public void makeArrayCopy() {
            Collections.copy(toSort, list);
        }

    }

    @Benchmark @BenchmarkMode({Mode.AverageTime, Mode.SampleTime, Mode.SampleTime})
    public void benchmarkHeapSort(BenchmarkList list) {
        Sorting.heapSort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime, Mode.SampleTime, Mode.SampleTime})
    public void benchmarkQSort(BenchmarkList list) {
        Sorting.quickSort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime, Mode.SampleTime, Mode.SampleTime})
    public void benchmarkIntroSort(BenchmarkList list) {
        Sorting.introsort(list.toSort);
    }

    @Benchmark @BenchmarkMode({Mode.AverageTime, Mode.SampleTime, Mode.SampleTime})
    public void benchmarkMergeSort(BenchmarkList list) {
        Sorting.mergeSort(list.toSort);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }
}
