Priority Queue
==============

Implementations
---------------

I wrote the code for this assignment in two different locations.
One of the implementations is in NativeQueue.java.
This uses native functions whose backend is written in Rust.
The other implementation is in PriorityQueue (I probably should use
have chosen a different name because it has the same name as 
Java's implementation, maybe something like QueuePriority). 
The native version only works with doubles because I am not familiar
enough with the JNI to use any Object. The Java version extends 
AbstractQueue and works for any Object of type ``T`` that
implements ``Comparable < T >``.

Algorithm
---------

The algorithm I used for this code is a heap. It is probably
not the most optimized way to write it. In short when adding
to the PriorityQueue, it will first look at the end of the 
heap and then check if it is a smaller number than its parent.
If it is a smaller number than it, it will swap them and continue
the process until it either becomes the root of the tree or
becomes the child of a smaller element. For removing it will
first remove the first element which should be the smallest
element and then will call a recursive function that will start
from the first element and then check if the left and right
node are smaller than it and if they are, it will swap them.
If it had to swap the parent and a child, it will then call 
the function again on the location of the smaller node.

Using the Native version
------------------------

Because the backend for one of the priority queues is written
in Rust, it will need to be compiled. The binary should be
there for Linux and Mac OS, if it does not work (maybe because
it was compiled for a specific CPU because I have messed with
my compiler flags) you will need to compile it yourself. To do
this make sure to go into lib/priority_queue (with 
``cd lib/priority_queue`` on linux) and run ``cargo build``.
If cargo does not run, it can probably be built using gcc, however
there probably will be issues with libraries.

Repl
----

I'm not very familiar with repl.it but I believe it uses 
Debian or Ubuntu so .so files should work. I do not know
if each repl will be on the same git branch. If it is not 
on updated-structure, use ``git checkout updated-structure``
to get the code needed. Ideally all that would need to be run
the code would be is 
``mvn -Plwjgl-natives-linux-amd64 exec:java -Dexec.args="use"``
(on Mac, change linux-amd64 to macos-amd64). This most likely
will work. To change the args, change ``-Dexec.args="use"``
to anything else. The only args that will work are ``"use"`` and 
``"benchmark"``. Benchmarking the code uses the 
[jmh library](https://openjdk.java.net/projects/code-tools/jmh/)
and will take around 25 minutes. After playing around with maven,
I manged to create a jar file. Now the way to run the code is by
doing ``java -cp apcs-0.1-jar-with-dependencies.jar util.PriorityQueueTest``
followed by the args. The code should work on this
[repl](https://repl.it/@lmanolache/APCS-1#README.md).
