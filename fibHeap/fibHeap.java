import java.io.*;

public class fibHeap {
	private node min;
	private list rootList;
	private int size,numMarked;
	private double phi;

	public fibHeap() {
		rootList = new list();
		phi = (1 + Math.sqrt(5)) / 2;
	}

	public node insert(node newNode) {
		rootList.insert(newNode);
		size++;
		if(min == null || newNode.get_key() < min.get_key()) min = newNode;
		return newNode;
	}

	public node findMin() {
		return min;
	}

	public void statistics() {
		System.out.println("\n[Heap Size: " + size + "}");
		System.out.println("[Root List Size: " + rootList.size() + "]");
		System.out.println("[Num Marked Nodes: " + numMarked + "]");
		if(min != null) {
			System.out.println("[Current Min: " + min.get_key() + "]");
		}
	}

	public void set_min(node Min) {
		min = Min;
	}

	public int get_size() {
		return size;
	}

	public void set_size(int Size) {
		size = Size;
	}

	public list get_rootList() {
		return rootList;
	}

	public void set_rootList(list RootList) {
		rootList = RootList;
	}

	public list merge_lists(list x, list y) {
		x.set_size(x.size() + y.size());
		node tmpNode = y.get_start();
		for(int i = 0; i < y.size(); i++){
			tmpNode.set_parent(null);
			tmpNode = tmpNode.get_next();
		}
		node xLast = x.get_start().get_prev();
		node yLast = y.get_start().get_prev();
		xLast.set_next(y.get_start());
		y.get_start().set_prev(xLast);
		yLast.set_next(x.get_start());
		x.get_start().set_prev(yLast);
		return x;
	}

	public fibHeap union(fibHeap h1, fibHeap h2) {
		fibHeap h = new fibHeap();
		if(h1.findMin().get_key() < h2.findMin().get_key()) {
			h.set_min(h1.findMin());
		}
		else {
			h.set_min(h2.findMin());
		}
		h.set_rootList(merge_lists(h1.get_rootList(),h2.get_rootList()));
		h.set_size(h1.get_size() + h2.get_size());
		return h;
	}

	private list get_memberList(node currNode) {
		if(currNode.get_parent() == null) {return rootList;}
		else return currNode.get_parent().get_childList();
	}

	private list get_childList(node currNode) {
		return currNode.get_childList();
	}

	public node extractMin() {
		node z = min;
		if(z != null) {
			list childList = get_childList(min);
			if(childList != null) {
				merge_lists(rootList, childList);
			}
			z.set_child(null);
			z.set_childList(null);
			rootList.remove(z.get_key());
			if(z == z.get_next()) {min = null;}
			else {
				min = z.get_next();
				rootList.delete(z);
				consolidate();
			}
			size--;
		}
		return z;
	}

	private void consolidate() {
		node[] cA = new node[(int)(Math.log((double)size)/Math.log(phi))]; //log(phi,H.size)
		node currRoot = findMin();
		node x,y;
		int d;
		int loops = rootList.size();
		for(int i = 0; i < loops; i++) {
			x = currRoot;
			d = x.get_degree();
			while(cA[d] == x) {
				currRoot = x.get_next();
				x = currRoot;
				d = x.get_degree();
			}
			while(cA[d] != null && cA[d] != x) {
				y = cA[d];
				if (x.get_key() > y.get_key()) {
					node temp = x;
					x = y;
					y = temp;
				}
				fibHeapLink(x, y);
				cA[d] = null;
				d++;
			}
			cA[d] = x;
			currRoot = x.get_next();
		}
		min = null;
		rootList = null;
		rootList = new list();
		for(node fibNode : cA) {
			if(fibNode != null) {
				if (min == null || fibNode.get_key() < min.get_key()) {
					min = fibNode;
				}
				rootList.insert(fibNode);
			}
		}
	}

	private void fibHeapLink(node x, node y) {
		rootList.remove(y.get_key());
		if(get_childList(x) == null) {
			list newChildList = new list();
			newChildList.insert(y);
			x.set_childList(newChildList);
			y.set_parent(x);
			x.set_child(y);
		}
		else {
			get_childList(x).insert(y);
			y.set_parent(x);
		}
		x.set_degree(x.get_degree() + 1);
		y.unmark();
	}

	public void decreaseKey(node x, int newKey) {
		if(newKey > x.get_key()) {
			return;
		}
		x.set_key(newKey);
		node y = x.get_parent();
		if(y != null && x.get_key() < y.get_key()) {
			cut(x,y);
			cascadingCut(y);
		}
		if(x.get_key() < min.get_key()) min = x;
	}

	private void cut(node x, node y) {
		list childList = get_childList(y);
		childList.remove(x.get_key());
		if(childList.size() == 0) {
			y.set_child(null);
			y.set_childList(null);
		}
		y.set_degree(y.get_degree() - 1);
		rootList.insert(x);
		x.set_parent(null);
		x.unmark();
	}

	private void cascadingCut(node y) {
		node z = y.get_parent();
		if(z != null) {
			if(!y.get_mark()) {
				numMarked++;
				y.mark();
			}
			else {
				numMarked--;
				cut(y,z);
				cascadingCut(z);
			}
		}
	}

	public node delete(node x) {
		decreaseKey(x, min.get_key() - 1);
		extractMin();
		return x;
	}

	public node search(fibHeap H, int value) {
		return searchHelper(H.min, value);
	}

	private node searchHelper(node fibNode, int value) {
		if(fibNode == null || fibNode.get_key() == value) {
			return fibNode;
		}
		list currList = get_memberList(fibNode);
		node findNode = currList.find(value);
		if(findNode == null) {
			for(int i = 0; i < currList.size(); i++) {
				findNode = searchHelper(fibNode.get_child(),value);
				if(findNode != null && findNode.get_key() == value) return findNode;
				fibNode = fibNode.get_next();
			}
		}
		return findNode;
	}

	public void visualize(fibHeap H, String title) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("fib.dot"));
		bw.write("digraph fib {\n\tnode[shape=circle,height=.6,fixedsize=true]\n");
		bw.write("\tlabelloc=\"b\"\n\tlabel=" + title + "\n");
		if(size == 0) {
			bw.write("\tnode [shape=plaintext]");
			bw.write("\tempty\n");
		}
		else {
			vizHelper(H.get_rootList().get_start(), bw);
		}
		bw.write("}");
		bw.close();
	}

	private void vizHelper(node fibNode, BufferedWriter bw) throws IOException {
		list currList = get_memberList(fibNode);
		for(int i = 0; i < currList.size(); i++) {
			if(fibNode.get_parent() != null) {
				bw.write("\t" + fibNode.get_parent().get_key() + "->" + fibNode.get_key() + " [arrowhead=none]\n");
			}
			else {
				bw.write("\t" + fibNode.get_key() + "[fontcolor=\"cadetblue\"]\n");
			}
			if(fibNode.get_mark()) {
				bw.write("\t" + fibNode.get_key() + "[fontcolor=\"red\" style=dashed]\n");
			}
			if(fibNode.get_child() != null) {
				vizHelper(fibNode.get_child(),bw);
			}
			fibNode = fibNode.get_next();
		}
	}
 }
