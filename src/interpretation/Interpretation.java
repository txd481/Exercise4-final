package interpretation;

import java.util.ArrayList;
import java.util.Stack;

import nxtsearch.Coordinate;
import nxtsearch.Node;
import rp.robotics.mapping.Heading;

/**
 * Class interpreting the path returned from the search into accurate movements
 *
 */
public class Interpretation {

	private ArrayList<String> list;
	private Heading myHead;

	public Interpretation(Heading head) {
		list = new ArrayList<String>();
		this.myHead = head;
	}

	/**
	 * Doing the interpretation of the path
	 * 
	 * @param path
	 *            The path returned form the search
	 * @return An array list of appropriate movements
	 */
	public ArrayList<String> interpret(Stack<Node<Coordinate>> path) {

		while (!path.empty()) {

			Object popped = path.pop();
			Node<Coordinate> elem = (Node<Coordinate>) popped;
			if (!path.isEmpty()) {
				Object newpopped = path.peek();
				Node<Coordinate> newelem = (Node<Coordinate>) newpopped;

				if (myHead.equals(Heading.PLUS_X)) {
					xPlusMove(elem, newelem);
					// list.add("goForward");
				} else if (myHead.equals(Heading.PLUS_Y)) {
					yPlusMove(elem, newelem);
					// list.add("right");

				} else if (myHead.equals(Heading.MINUS_Y)) {
					yMinusMove(elem, newelem);
					// list.add("goBackwards");

				} else if (myHead.equals(Heading.MINUS_X)) {
					xMinusMove(elem, newelem);
					// list.add("left");
				}
			}
		}

		return list;

	}

	/**
	 * Movements required when the heading of the robot is xMinus and adding one
	 * to the array list
	 * 
	 * @param elem
	 *            The current node from the stack of the path
	 * @param newelem
	 *            The next node from the stack of the path
	 */
	public void xMinusMove(Node<Coordinate> elem, Node<Coordinate> newelem) {
		if (elem.contents().x > newelem.contents().x) {
			list.add("goForward");
		} else if (elem.contents().y > newelem.contents().y) {
			list.add("left");
			myHead = Heading.MINUS_Y;

		} else if (elem.contents().x < newelem.contents().x) {
			list.add("goBackwards");
			myHead = Heading.PLUS_X;

		} else if (elem.contents().y < newelem.contents().y) {
			list.add("right");
			myHead = Heading.PLUS_Y;

		}

	}

	/**
	 * Movements required when the heading of the robot is yMinus and adding one
	 * to the array list
	 * 
	 * @param elem
	 *            The current node from the stack of the path
	 * @param newelem
	 *            The next node from the stack of the path
	 */
	public void yMinusMove(Node<Coordinate> elem, Node<Coordinate> newelem) {

		if (elem.contents().y > newelem.contents().y) {
			list.add("goBackwards");
			myHead = Heading.PLUS_Y;
		} else if (elem.contents().x < newelem.contents().x) {
			list.add("left");
			myHead = Heading.PLUS_X;

		} else if (elem.contents().y < newelem.contents().y) {
			list.add("goForward");

		} else if (elem.contents().x > newelem.contents().x) {
			list.add("right");
			myHead = Heading.MINUS_X;

		}

	}

	/**
	 * Movements required when the heading of the robot is yPlus and adding one
	 * to the array list
	 * 
	 * @param elem
	 *            The current node from the stack of the path
	 * @param newelem
	 *            The next node from the stack of the path
	 */
	public void yPlusMove(Node<Coordinate> elem, Node<Coordinate> newelem) {
		if (elem.contents().y < newelem.contents().y) {
			list.add("goForward");
		} else if (elem.contents().x > newelem.contents().x) {
			list.add("left");
			myHead = Heading.MINUS_X;

		} else if (elem.contents().y > newelem.contents().y) {
			list.add("goBackwards");
			myHead = Heading.MINUS_Y;

		} else if (elem.contents().x < newelem.contents().x) {
			list.add("right");
			myHead = Heading.PLUS_X;

		}

	}

	/**
	 * Movements required when the heading of the robot is xPlus and adding one
	 * to the array list
	 * 
	 * @param elem
	 *            The current node from the stack of the path
	 * @param newelem
	 *            The next node from the stack of the path
	 */
	public void xPlusMove(Node<Coordinate> elem, Node<Coordinate> newelem) {

		if (elem.contents().x < newelem.contents().x) {
			list.add("goForward");

		} else if (elem.contents().y < newelem.contents().y) {
			list.add("left");
			myHead = Heading.PLUS_Y;

		} else if (elem.contents().x > newelem.contents().x) {
			list.add("goBackwards");
			myHead = Heading.MINUS_X;

		} else if (elem.contents().y > newelem.contents().y) {
			list.add("right");
			myHead = Heading.MINUS_Y;

		}
	}
}
