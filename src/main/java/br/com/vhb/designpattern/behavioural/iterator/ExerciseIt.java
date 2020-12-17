package br.com.vhb.designpattern.behavioural.iterator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

class Node<T> {
	public T value;
	public Node<T> left, right, parent;

	public Node(T value) {
		this.value = value;
	}

	public Node(T value, Node<T> left, Node<T> right) {
		this.value = value;
		this.left = left;
		this.right = right;

		left.parent = right.parent = this;
	}

	private void traverse(Node<T> current, ArrayList<Node<T>> acc) {
		acc.add(current);
		if (current.left != null) {
			traverse(current.left, acc);
		}
		if (current.right != null) {
			traverse(current.right, acc);
		}
	}

	public Iterator<Node<T>> preOrder() {
		ArrayList<Node<T>> items = new ArrayList<>();
		traverse(this, items);
		return items.iterator();
	}
}

public class ExerciseIt {
	/**
	 * a b e c d
	 *
	 */
	@Test
	public void test() {
		Node<Character> node = new Node<>('a', new Node<>('b', new Node<>('c'), new Node<>('d')), new Node<>('e'));
		StringBuilder sb = new StringBuilder();
		Iterator<Node<Character>> it = node.preOrder();
		while (it.hasNext()) {
			sb.append(it.next().value);
		}
		assertEquals("abcde", sb.toString());
	}
}
