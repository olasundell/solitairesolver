package se.atrosys.solitaire.ntree;

import se.atrosys.solitaire.game.Solitaire;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends Solitaire> {
	private String checksum;
	private final T data;
	private final List<Node<T>> nodes = new ArrayList<>();

	public Node(T data) {
		this.data = data;
		checksum = data.hashString();
	}

	public Node<T> addChild(T data) {
		Node<T> node = new Node<>(data);
		nodes.add(node);

		return node;
	}

	public String getChecksum() {
		return checksum;
	}

	public T getData() { return data; }

	public boolean isEmpty() {
		return nodes.isEmpty();
	}
}
