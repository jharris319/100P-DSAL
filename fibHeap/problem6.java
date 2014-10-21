import java.io.*;

public class problem6 {
	public static void main (String args[]) throws IOException {
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br;
		String command,line;
		int flag = 0;
		fibHeap activeHeap, H1, H2;
		H1 = new fibHeap();
		H2 = new fibHeap();
		activeHeap = H1;
		node fibNode;

		menu();
		while(true) {
			System.out.print("\nAt your command: ");
			command = consoleReader.readLine();
			if (command.equals("")) command = "junk";
			if (command.charAt(0) == 'i' ||  command.charAt(0) == 'k' || command.charAt(0) == 'd' || command.charAt(0) == 'f' || command.charAt(0) == 'b' || command.charAt(0) == 'r') {
				String strip = command.replaceAll("[^-|^\\d+]","");
				if (strip.equals("")) command = "junk";
				else flag = Integer.parseInt(strip);
			}
			switch(command.charAt(0)) {
				case 'a':
					menu();
					advMenu();
					break;
				case 'b':
					for (int i = 0; i < flag; i++) {
						fibNode = new node(i, null, null);
						activeHeap.insert(fibNode);
					}
					System.out.println("\n[Inserted: " + flag + "]");
					for (int i = 0; i < flag; i++) {
						activeHeap.extractMin();
					}
					System.out.println("[Extracted: " + flag + "]");
					break;
				case 'd':
					menu();
					fibNode = activeHeap.search(activeHeap, flag);
					if (activeHeap.delete(fibNode) != null) System.out.println("\n[Deleted: " + flag + "]");
					break;
				case 'e':
					menu();
					fibNode = activeHeap.extractMin();
					if(fibNode != null) {
						System.out.println("\n[Extracted: " + fibNode.get_key() + "]");
					}
					else {
						System.out.println("\n[Nothing to extract]");
					}
					break;
				case 'f':
					menu();
					fibNode = activeHeap.search(activeHeap, flag);
					if(fibNode != null) {
						System.out.println("\n[Found: " + fibNode.get_key() + "]");
						System.out.println("[Degree: " + fibNode.get_degree() + "]");
						if(fibNode.get_child() != null) System.out.println("[Child: " + fibNode.get_child().get_key() + "]");
						if(fibNode.get_parent() != null) System.out.println("[Parent: " + fibNode.get_parent().get_key() + "]");
						System.out.println("[Prev: " + fibNode.get_prev().get_key() + "]");
						System.out.println("[Next: " + fibNode.get_next().get_key() + "]");
						System.out.println("[Mark: " + fibNode.get_mark() + "]");
					}
					else System.out.println("\n[Not Found]");
					break;
				case 'g':
					menu();
					System.out.print("\nStart at: ");
					int start = Integer.parseInt(consoleReader.readLine());
					System.out.print("End at: ");
					int finish = Integer.parseInt(consoleReader.readLine());
					for(int i = start; i < finish; i++) {
						fibNode = new node(i, null, null);
						activeHeap.insert(fibNode);
					}
					break;
				case 'i':
					menu();
					fibNode = new node(flag, null, null);
					if (activeHeap.insert(fibNode) != null) System.out.println("\n[Inserted: " + flag + "]");
					break;
				case 'k':
					System.out.print("Decrease " + flag + " to: ");
					int newKey = Integer.parseInt(consoleReader.readLine());
					fibNode = activeHeap.search(activeHeap, flag);
					activeHeap.decreaseKey(fibNode,newKey);
					menu();
					break;
				case 'm':
					menu();
					fibNode = activeHeap.findMin();
					if(fibNode != null) System.out.println("\n[Heap Minimum: " + fibNode.get_key() + "]");
					break;
				case 's':
					menu();
					activeHeap.statistics();
					break;
				case 't':
					menu();
					if(activeHeap == H1) {
						activeHeap = H2;
						System.out.println("\n[Active heap set to H2]");
					}
					else {
						activeHeap = H1;
						System.out.println("\n[Active heap set to H1]");
					}
					break;
				case 'u':
					menu();
					activeHeap = activeHeap.union(H1,H2);
					H1 = activeHeap;
					H2 = new fibHeap();
					break;
				case 'v':
					menu();
					String title = "";
					if(activeHeap == H1) {title = "\"Heap 1\"";}
					if(activeHeap == H2) {title = "\"Heap 2\"";}
					activeHeap.visualize(activeHeap, title);
					Runtime.getRuntime().exec("dot -Tpng fib.dot -o fib.png");
					if (System.getProperty("os.name").startsWith("Linux")) {
						Runtime.getRuntime().exec("eog fib.png");
					}
					else if (System.getProperty("os.name").startsWith("Mac")) {
						Runtime.getRuntime().exec("open fib.png");
					}
					break;
				case 'q':
					System.out.print("\u001b[2J" + "\u001b[H");
					System.out.println("Thanks for flying 100P!");
					System.exit(0);
					break;
				case 'r':
					menu();
					rand.main(flag,0,1,flag/2);
					br = new BufferedReader(new FileReader("randInts.txt"));
					while ((line = br.readLine()) != null) {
						fibNode = new node(Integer.parseInt(line),null,null);
						activeHeap.insert(fibNode);
					}
					break;
				case 'z':
					System.out.println("\n[Debugging]");
					break;
				case '~':
					menu();
					activeHeap = null;
					H1 = null;
					H2 = null;
					System.gc();
					H1 = new fibHeap();
					H2 = new fibHeap();
					activeHeap = H1;
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
		System.out.print("Problem 6: Fibonacci Heaps\n\n");
		System.out.print("Available Commands:\n");
		System.out.print("    i XXX - Insert value XXX into the heap\n");
		System.out.print("    m XXX - Find the minimum value of the heap\n");
		System.out.print("    u     - Merge two heaps (H1 and H2)\n");
		System.out.print("    e     - Extract the minimum value from the heap\n");
		System.out.print("    k XXX - Decrease the key of node XXX\n");
		System.out.print("    d XXX - Delete a node with key XXX from the heap\n");
		System.out.print("    s     - Print statistics of active heap\n");
		System.out.print("    t     - Toggle active heap\n");
		System.out.print("    v     - Visualize active heap with graphviz\n");
		System.out.print("    a     - Show advanced menu\n");
		System.out.print("    q     - Quit Problem 6\n");
	}

	public static void advMenu() {
		System.out.println("\nAdvanced Commands:\n");
		System.out.print("    b XXX - Attempt to break data structure\n");
		System.out.print("    f XXX - Find XXX in active heap\n");
		System.out.print("    g XXX - Populate active heap with XXX values\n");
		System.out.print("    r XXX - Populate active heap with XXX random values\n");
		System.out.print("    ~     - Reset H1 and H2\n");
	}
}
