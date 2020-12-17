package br.com.vhb.designpattern.behavioural.iterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

class NodeIt<T> {
	public T value;
	public NodeIt<T> left, right, parent;

	public NodeIt(T value) {
		this.value = value;
	}

	public NodeIt(T value, NodeIt<T> left, NodeIt<T> right) {
		this.value = value;
		this.left = left;
		this.right = right;

		left.parent = right.parent = this;
	}
}

class InOrderIt<T> implements Iterator<T> {
	private NodeIt<T> current;
	private boolean yieldedStart;

	public InOrderIt(NodeIt<T> root) {
		current = root;

		while (current.left != null)
			current = current.left;
	}

	private boolean hasRightmostParent(NodeIt<T> node) {
		if (node.parent == null)
			return false;
		else {
			return (node == node.parent.left) || hasRightmostParent(node.parent);
		}
	}

	@Override
	public boolean hasNext() {
		return current.left != null || current.right != null || hasRightmostParent(current);
	}

	@Override
	public T next() {
		if (!yieldedStart) {
			yieldedStart = true;
			return current.value;
		}

		if (current.right != null) {
			current = current.right;
			while (current.left != null)
				current = current.left;
			return current.value;
		} else {
			NodeIt<T> p = current.parent;
			while (p != null && current == p.right) {
				current = p;
				p = p.parent;
			}
			current = p;
			return current.value;
		}
	}
}

class BinaryTreeIt<T> implements Iterable<T> {
	private NodeIt<T> root;

	public BinaryTreeIt(NodeIt<T> root) {
		this.root = root;
	}

	@Override
	public Iterator<T> iterator() {
		return new InOrderIt<>(root);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		for (T item : this)
			action.accept(item);
	}

	@Override
	public Spliterator<T> spliterator() {
		return null;
	}
}

public class Iterator_1 {
	public static void main(String[] args) {
		// 1
		// / \
		// 2 3
		NodeIt<Integer> root = new NodeIt<>(1, new NodeIt<>(2), new NodeIt<>(3));

		InOrderIt<Integer> it = new InOrderIt<>(root);

		while (it.hasNext()) {
			System.out.print("" + it.next() + ",");
		}
		System.out.println();

		BinaryTreeIt<Integer> tree = new BinaryTreeIt<>(root);
		for (int n : tree)
			System.out.print("" + n + ",");
		System.out.println();
	}
}
