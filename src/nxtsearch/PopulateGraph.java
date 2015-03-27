package nxtsearch;

import java.util.ArrayList;

import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;

/**
 * The Class PopulateGraph.
 *
 * @param <A>
 *            the generic type
 */
public class PopulateGraph<A> {

	/** The x junctions. */
	public int xJunctions = 12;

	/** The y junctions. */
	public int yJunctions = 8;

	/**
	 * Instantiates a new populate graph.
	 */
	public PopulateGraph() {

	}

	/**
	 * Populate graph.
	 *
	 * @param myGraph
	 *            the graph of nodes
	 */
	public void populateGraph(NewGraph<Coordinate> myGraph) {
		MyMap map = getMap();
		ArrayList<Node<Coordinate>> graph = new ArrayList<Node<Coordinate>>();
		Node<Coordinate>[] nodes;
		for (int q = 0; q < xJunctions; q++) {
			for (int p = 0; p < yJunctions; p++) {
				Coordinate coord = new Coordinate(q, p);
				graph.add(myGraph.nodeWith(coord));
			}
		}

		for (Node<Coordinate> node : graph) {
			int xcoord = node.contents().x;
			int ycoord = node.contents().y;

			if (map.isValidTransition(xcoord, ycoord, xcoord + 1, ycoord)) {

				Coordinate coord1 = new Coordinate(xcoord + 1, ycoord);
				node.addSuccessor(myGraph.nodeWith(coord1));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord, ycoord + 1)) {

				Coordinate coord2 = new Coordinate(xcoord, ycoord + 1);
				node.addSuccessor(myGraph.nodeWith(coord2));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord - 1, ycoord)) {

				Coordinate coord3 = new Coordinate(xcoord - 1, ycoord);
				node.addSuccessor(myGraph.nodeWith(coord3));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord, ycoord - 1)) {

				Coordinate coord4 = new Coordinate(xcoord, ycoord - 1);
				node.addSuccessor(myGraph.nodeWith(coord4));
			}
		}
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public MyMap getMap() {
		RPLineMap lineMap = MapUtils.create2015Map1();

		// grid map dimensions for this line map
		float junctionSeparation = 30;

		// position of grid map 0,0
		int xInset = 15;
		int yInset = 15;
		MyMap map = new MyMap(lineMap, xJunctions, yJunctions, xInset, yInset,
				junctionSeparation);
		return map;
	}
}
