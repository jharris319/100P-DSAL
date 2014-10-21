import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;

public class problem7 {
	public static void main (String args[]) throws IOException {
		Scanner consoleScan = new Scanner(System.in);
		String command;
		graph G = null;
		Scanner scan;

		menu();
		while(true) {
			System.out.print("\nAt your command: ");
			command = consoleScan.next();
			switch(command.charAt(0)) {
				case 'a':
					menu();
					G.addEdge(consoleScan.nextInt(),consoleScan.nextInt(),consoleScan.nextInt());
					break;
				case 'r':
					menu();
					try {
						scan = new Scanner(Paths.get(consoleScan.next()));
					}
					catch(NoSuchFileException exception) {
						System.out.println("\n[Error - No Such File]");
						break;
					}
					G = new graph(scan.nextInt());
					G.set_edges(scan.nextInt());
					while (scan.hasNext()) {
						G.addEdge(scan.nextInt(), scan.nextInt(), scan.nextInt());
					}
					break;
				case 'p':
					menu();
					G.dijkstra(consoleScan.nextInt());
					break;
				case 'q':
					System.out.print("\u001b[2J" + "\u001b[H");
					System.out.println("Thanks for flying 100P!");
					System.exit(0);
					break;
				case 't':
					menu();
					BufferedWriter bw = new BufferedWriter(new FileWriter("adjMatrix.txt"));
					bw.write(G.toString());
					bw.close();
					Runtime.getRuntime().exec("subl adjMatrix.txt");
					break;
				case 'v':
					menu();
					G.visualize();
					Runtime.getRuntime().exec("neato -Tpng graph.dot -o graph.png");
					Runtime.getRuntime().exec("eog graph.png");
					break;
				case 'z':
					System.out.println("\n[Debugging]");
					break;
				case '~':
					menu();
					G = null;
					break;
				default:
					menu();
					System.out.println("\n[Invalid Input]");
					break;
			}
		}
	}

	public static void menu() {
		System.out.print("\u001b[2J" + "\u001b[H");
		System.out.println("Problem 7: Graphs and Shortest Paths\n");
		System.out.println("Available Commands:");
		System.out.println("  r XXX - Read graph from file XXX");
		System.out.println("  a     - Add edge to graph [from/to/weight]");
		System.out.println("  p XXX - Invoke Dijkstra's algorithm at source XXX");
		System.out.println("  v     - Visualize graph with graphviz");
		System.out.println("  t     - Print adjacency matrix and statistics");
		System.out.println("  q     - Quit Problem 7");
	}
}
