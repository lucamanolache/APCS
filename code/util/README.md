Priority Queue
==============

Implementations
---------------

I wrote the code for this assignment in two different locations.
One of the implementations is in NativeQueue.java.
This uses native functions whose backend is written in Rust.
The other implementation is in PriorityQueue (I probably should use
have chosen a different name because it has the same name as 
Java's implementation). The native version only works with doubles
because I am not familiar enough with the JNI to use any Object.
The Java version extends AbstractQueue and works for any Object of
type ``T`` that implements ``Comparable < T >``.

Algorithm
---------

The algorithm I used for this code is a heap. It is probably
not the most optimized way to write it. In short when adding
to the PriorityQueue, it will first look at the end of the 
heap and then check if it is a smaller number than its parent.
If it is a smaller number than it, it will swap them and continue
the process until it either becomes the root of the tree or
becomes the child of a smaller element. 

Using the Native version
------------------------

Because the backend for one of the priority queues is written
in Rust, it will need to be compiled. The binary should be
there for Linux and Mac OS 