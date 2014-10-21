import java.io.*;

public class graph {
	private int[][] adjMatrix;
	private boolean[][] MSTedge;
	private int vertices;
	private int edges;
	private double[] distTo;
	private boolean dijkstra;

	public graph(int vert) {
		adjMatrix = new int[vert][vert];
		MSTedge = new boolean[vert][vert];
		vertices = vert;
	}

	public void set_edges(int edge) {
		edges = edge;
	}

	public void addEdge(int from, int to, int weight) {
		adjMatrix[from][to] = weight;
		adjMatrix[to][from] = weight;
	}

	public void dijkstra(int source) {
		dijkstra = true;
		fibHeap H = new fibHeap();
		distTo = new double[vertices];
		MSTedge = new boolean[vertices][vertices];

		for(int v = 0; v < vertices; v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[source] = 0;

		H.insert(new node(source));
		while(!H.isEmpty()) {
			relax(H, H.extractMin().get_key());
		}
	}

	private void relax(fibHeap H, int v) {
		for(int i = 0; i < vertices; i++) {
			if(adjMatrix[v][i] != 0) {
				if(distTo[i] > distTo[v] + adjMatrix[v][i]) {
					distTo[i] = distTo[v] + adjMatrix[v][i];
					MSTedge[v][i] = MSTedge[i][v] = true;
					H.insert(new node(i));
				}
			}
		}
	}

	public String toString() {
		String s = "V: " + vertices + "\tE: " + edges + "\n";
		for(int i = 0; i < vertices; i++) {
			for(int j = 0; j < vertices; j++) {
				s += adjMatrix[i][j] + "\t";
			}
			s += "\n";
		}
		return s;
	}

	public void visualize() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("graph.dot"));
		bw.write("graph G {\n\tnode[shape=circle,height=.5,fixedsize=true]\n");
		bw.write("\tedge[color=\"wheat4\",fontcolor=\"darkslategray\"]\n");
		String distI, distJ;
		for(int i = 0; i < vertices; i++) {
			if(dijkstra) {
				if(distTo[i] == Double.POSITIVE_INFINITY) distI = "inf";
				else distI = "" + (int)distTo[i];
				bw.write("\t\"" + i + ":" + distI + "\"\n");
			}
			else {
				bw.write("\t" + i + "\n");
			}
			for(int j = i; j < vertices; j++) {
				if(adjMatrix[i][j] != 0) {
					if(dijkstra) {
						if(distTo[i] == Double.POSITIVE_INFINITY) distI = "inf";
						else distI = "" + (int)distTo[i];
						if(distTo[j] == Double.POSITIVE_INFINITY) distJ = "inf";
						else distJ = "" + (int)distTo[j];
						if(MSTedge[i][j]) {
							bw.write("\t\"" + i + ":" + distI + "\" -- \"" + j + ":" + distJ + "\" [label = " + adjMatrix[i][j] + ",color=crimson]\n");
						}
						else {
							bw.write("\t\"" + i + ":" + distI + "\" -- \"" + j + ":" + distJ + "\" [label = " + adjMatrix[i][j] + ",style=\"dashed\"]\n");
						}
					}
					else {
						bw.write("\t" + i + "--" + j + " [label = " + adjMatrix[i][j] + "]\n");
					}
				}
			}
		}
		bw.write("}");
		bw.close();
	}
}
