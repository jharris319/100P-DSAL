Fibonacci Heap
--------------

A heap data structure, written in Java.

#### Synopsis ####

Fibonacci heaps support a set of operations that constitute what is known as a "mergable heap." These operations are: make-heap, insert, find-min, extract-min, and union. Fibonacci heaps are desirable when the number of extract-min and delete operations are small in relation to the other operations performed. This is a result of the consolidation of root nodes after extracting the minimum to ensure that each root in the root list has a unique degree. Fibonacci heaps are considered to have "lazy" insert and union operations. That is, the work of consolidation is built up until an extraction is performed. This facilitates the Θ(1) runtime of the insert and merge operations at the cost of a potential increase in runtime of delete operations.  Much like a [Binomial Heap](http://en.wikipedia.org/wiki/Binomial_heap), Fibonacci heaps are not designed to efficiently support the search operation and thus several Fibonacci heap operations require a pointer to a node in the heap.

#### Asymptotic Analysis ####

Procedure|Amortized| Worst |
| ------ | ----- | ------|
| Make-Heap |Θ(1)   |Θ(1)	 |
| Insert |Θ(1)|Θ(1)|
| Minimum |Θ(1))|Θ(1)|
| Extract-Min |O(logn)|O(n)|
| Union |Θ(1)|Θ(1)|
| Decrease-key |Θ(1)|O(logn)|
| Delete |Θ(logn)|O(logn)|

#### Example ####

![Fib Heap Example](https://github.com/jharris319/100P-DSAL/blob/master/fibHeap/fib.png)
