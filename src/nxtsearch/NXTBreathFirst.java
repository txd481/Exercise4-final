package nxtsearch;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import lejos.nxt.Button;


/**
 * The Class NXTBreathFirst.
 *
 * @param <Coordinate>
 *            the generic type
 */
public class NXTBreathFirst<Coordinate> {

	/**
	 * Find path.
	 *
	 * @param startNode
	 *            The start node
	 * @param predicate
	 *            The goal node
	 * @return The stack of the path
	 */
	public Stack<Node<Coordinate>> findPath(Node<Coordinate> startNode,
			Predicate<Coordinate> predicate) {
		Queue<Node<Coordinate>> queue = new Queue<Node<Coordinate>>();

		Stack<Node<Coordinate>> pointers = new Stack<Node<Coordinate>>();
		ArrayList<Node<Coordinate>> visited = new ArrayList<Node<Coordinate>>();
		Stack<Node<Coordinate>> path = new Stack<Node<Coordinate>>();

		queue.addElement(startNode);

		while (!queue.isEmpty()) {

			Object popped = queue.pop();
			Node<Coordinate> x = (Node<Coordinate>) popped;

			Coordinate content = x.contents();

			if (!alreadyVisited(x, visited)) {

				if (predicate.holds(content)) {

					Node<Coordinate> a = x;

					while (a != startNode) {

						Node<Coordinate> aNode = a;
						path.push(a);

						while (aNode.contents().equals(a.contents())) {

							Node<Coordinate> z = pointers.pop();

							// ArrayList<Node<A>> point = z.getPointers();
							for (Node<Coordinate> point : z.getPointers()) {
								if (point.contents().equals(a.contents())) {
									a = z;
								}
							}

						}

					}

					path.push(startNode);
					return path;
				}

				visited.add(x);
				pointers.push(x);
				for (Node<Coordinate> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						queue.addElement(suc);
						x.addPointer(suc);
					}
				}

			}
		}
		return new Stack<Node<Coordinate>>();
	}

	/**
	 * Already visited.
	 *
	 * @param something
	 *            The node
	 * @param visited
	 *            The visited nodes
	 * @return true, if the node is in the stack of visited 
	 */
	private boolean alreadyVisited(Node<Coordinate> something,
			ArrayList<Node<Coordinate>> visited) {

		return (visited.contains(something));

	}
}
