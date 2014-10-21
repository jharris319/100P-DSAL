Fibonacci Heap
--------------

A heap data structure, written in Java.

#### Synopsis ####

Fibonacci heaps support a set of operations that constitute what is known as a "mergable heap". These are: make-heap, insert, minimum, extract-min, and union. Fibonacci heaps also support two additional operations: decrease-key and delete. The amortized runtime of all of the previously mentioned procedures are constant with the exception of extract-min and delete, both of which are O(logn).

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