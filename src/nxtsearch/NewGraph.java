package nxtsearch;

import rp.util.SimpleSet;


/**
 * The Class NewGraph.
 *
 * @param <A> the generic type
 */
public class NewGraph<A> {

	/** The nodes. */
	private SimpleSet<Node<A>> nodes;

	// Constructs the empty graph:
	/**
	 * Instantiates a new graph.
	 */
	public NewGraph() {
		nodes = new SimpleSet<Node<A>>();
	}

	// Get method:
	/**
	 * Nodes.
	 *
	 * @return the simple set of nodes
	 */
	public SimpleSet<Node<A>> nodes() {
		return nodes;
	}

	// Finds or else creates a node with a given contents c:
	/**
	 * Node with content.
	 *
	 * @param c the content
	 * @return the node
	 */
	public Node<A> nodeWith(A c) {
		for (Node<A> node : nodes) { // Inefficient for large graph.
			if (node.contentsEquals(c))
				return node; // Found.
		}
		// Not found, hence create it:
		Node<A> node = new Node<A>(c);
		nodes.add(node);
		return node;
	}
}
