# For the graphing of the results, I do not know of any way to do it easily in Java so I am going to use the matplotlib library from python.

import matplotlib.pyplot as plt
import numpy as np
import csv

heapsort = []
quicksort = []
introsort = []
mergesort = []
java_sort = [] # Not calling it Tim Sort in case I ever want to implement Tim Sort

with open('results.csv', newline='') as csvfile:
    reader = csv.reader(csvfile, delimiter=',', quotechar='|')
    heapsort = [float(s) for s in next(reader)]
    introsort = [float(s) for s in next(reader)]
    java_sort = [float(s) for s in next(reader)]
    mergesort = [float(s) for s in next(reader)]
    quicksort = [float(s) for s in next(reader)]

fig, ax = plt.subplots()  # Create a figure and an axes.
x = [10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000]
ax.plot(x, heapsort, label='heapsort')
ax.plot(x, quicksort, label='quicksort')
ax.plot(x, introsort, label='introsort')
ax.plot(x, mergesort, label='mergesort')
ax.plot(x, java_sort, label='java sort')
ax.set_xscale('log')
ax.set_yscale('log')
ax.set_xlabel('array size')
ax.set_ylabel('time mircoseconds')
ax.set_title("Sorting Algorithm Speeds")
ax.legend()

plt.show()
