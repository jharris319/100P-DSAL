public class node {
	private int key;
	private int degree;
	private node parent;
	private node child;
	private list childList;
	private node prev;
	private node next;
	private boolean mark;

	public node (int Key, node Prev, node Next) {
		key = Key;
		prev = Prev;
		next = Next;
	}

	public void set_key(int Key) {key = Key;}

	public void set_degree(int Degree) {degree = Degree;}

	public void set_parent(node Parent) {parent = Parent;}

	public void set_child(node Child) {child = Child;}

	public void set_childList(list ChildList) {childList = ChildList;}

	public void set_prev(node Prev) {prev = Prev;}

	public void set_next(node Next) {next = Next;}

	public int get_key() {return key;}

	public int get_degree() {return degree;}

	public node get_parent() {return parent;}

	public node get_child() {return child;}

	public list get_childList() {return childList;}

	public node get_prev() {return prev;}

	public node get_next() {return next;}

	public void mark() {mark = true;}

	public void unmark() {mark = false;}

	public boolean get_mark() {return mark;};
}
