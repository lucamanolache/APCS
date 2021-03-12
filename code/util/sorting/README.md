Benchmarking
============

For the benchmarking, I was not able to do it for an array of 1 billion elements because the RAM usage that would take
is too much (Integer class takes 16 bytes. 16 bytes * 1,000,000,000 = 16 gb only for the array.
Another large amount might be needed for the stack, however that depends on if the algorithm is recursive or not).
I decided to graph the results of the benchmarking with [matplotlib with python](resources/graph.py).
Benchmarking the code took way too long, over 5 hours. Part of the reason was debugging and the other part was that
the library used for benchmarking is *very thorough* and some of the sorting algorithms took a few seconds to sort.
The [results](resources/results.csv) of this can be found here:

![graph](resources/figure.png)

In my original code without tuning any of the parameters for introsort, Java's ``Collections.sort()`` was slightly faster.
Therefore, I might try tuning the parameters in order to see if I can beat Java's speed. The comparison isn't completely
fair because Java uses a stable sorting algorithm (Tim Sort) and introsort is unstable, however it is most likely the only
way I can get code faster than Java because their implementation of Tim Sort has years of experience.

Algorithms Implemented
======================

Most of the instructions on implementing the algorithms came from wikipedia. Heapsort was implemented using the same
code as the last assignment. I will stick a link to all the sources I used for each algorithm.

* [x] three-way merge sort
* [x] merge sort
* [x] heapsort
* [x] [quick sort](https://en.wikipedia.org/wiki/Quicksort)
* [x] [introsort (used by c++)](https://en.wikipedia.org/wiki/Introsort)
* [x] insertion sort (used by introsort)
* [ ] [pdqsort (used by rust for unstable_sort)](https://github.com/orlp/pdqsort)
* [ ] [tim sort (most languages use this for stable sort)](https://en.wikipedia.org/wiki/Timsort)
* [ ] radix sort