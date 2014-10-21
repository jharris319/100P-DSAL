public class list {
	private node head;
	private int size;

	public list() {
		size = 0;
	}

	public int size() {
		return size;
	}

	public void set_size(int Size) {
		size = Size;
	}

	public node get_start() {
		return head;
	}

	public node insert(node insNode) {
		if(head == null) {
			head = insNode;
			head.set_prev(head);
			head.set_next(head);
		}
		node prevNode = head.get_prev();
		prevNode.set_next(insNode);
		head.set_prev(insNode);
		insNode.set_prev(prevNode);
		insNode.set_next(head);
		size++;
		return insNode;
	}

	public node find(int value) {
		node tmpNode = get_start();
		while (tmpNode.get_key() != value && tmpNode.get_next() != head) {
			tmpNode = tmpNode.get_next();
		}
		if (tmpNode.get_key() == value) return tmpNode;
		else return null;
	}

	public node remove(int value) {
		node tmpNode = find(value);
		if (tmpNode.get_key() == value) {
			if(tmpNode == head) head = tmpNode.get_next();
			if(tmpNode.get_parent() != null) {
				if(tmpNode.get_parent().get_child() == tmpNode) {
					tmpNode.get_parent().set_child(tmpNode.get_next());
				}
			}
			tmpNode.get_prev().set_next(tmpNode.get_next());
			tmpNode.get_next().set_prev(tmpNode.get_prev());
			size--;
			if(size == 0) head = null;
			return tmpNode;
		}
		else return null;
	}

	public void delete(node delNode) {
		delNode.set_prev(null);
		delNode.set_next(null);
	}
}
