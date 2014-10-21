Dijkstra's Algorithm
--------------------

A graph search algorithm, written in Java.

#### Synopsis ####

Dijkstra's algorithm, written in 1956 by Edsger Dijkstra, solves the single-source shortest path path problem for a graph with non-negative edge weights. Dijkstra's algorithm is considered a "greedy algorithm". These types of algorithms follow the problem solving heuristic of making the locally optimal choice at each stage with the hope of finding a global optimum. In graph theory, Dijkstra's algorithm produces a minimum spanning tree which may or may not be the minimum spanning tree of a graph. This implementation of Dijkstra's algorithm utilizes a Fibonacci heap to produce the fastest known single-source shortest-path algorithm, running in O(|E| + |V|log|V|).

#### Example ####

![Dijkstra's Algorithm Example](https://github.com/jharris319/100P-DSAL/blob/master/dijkstra/graph.png)

Dijkstra's Algorithm from Node 2