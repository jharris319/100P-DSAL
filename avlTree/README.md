AVL Tree
--------

A self balancing binary search tree, written in C.

#### Synopsis ####

AVL trees, invented in 1962 by Adelson-Velsky and Landis, are binary search trees which require that the height of each of the two child subtrees of any node differ by at most one. If any imbalance is detected, rebalancing is perform to restore the AVL property.

### Asymtotic Analysis ###

Operation|Average| Worst |
| ------ | ----- | ------|
| Space	 |O(n)   |O(n)	 |
| Search |O(logn)|O(logn)|
| Insert |O(logn)|O(logn)|
| Delete |O(logn)|O(logn)|