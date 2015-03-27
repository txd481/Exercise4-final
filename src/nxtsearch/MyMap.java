package nxtsearch;

import lejos.geom.Line;
import lejos.geom.Point;
import lejos.geom.Rectangle;
import lejos.robotics.navigation.Pose;
import lejos.util.Delay;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.RPLineMap;

/**
 * The Class MyMap.
 */
public class MyMap implements IGridMap {

	/** The grid x size. */
	private int gridXSize;
	
	/** The grid y size. */
	private int gridYSize;
	
	/** The linemap. */
	private RPLineMap linemap;
	
	/** The x start. */
	private float xStart;
	
	/** The y start. */
	private float yStart;
	
	/** The cell size. */
	private float cellSize;

	/**
	 * Instantiates a new my map.
	 *
	 * @param _lineMap the _line map
	 * @param _gridXSize the _grid x size
	 * @param _gridYSize the _grid y size
	 * @param _xStart the _x start
	 * @param _yStart the _y start
	 * @param _cellSize the _cell size
	 */
	public MyMap(RPLineMap _lineMap, int _gridXSize, int _gridYSize,
			float _xStart, float _yStart, float _cellSize) {
		linemap = _lineMap;
		gridXSize = _gridXSize;
		gridYSize = _gridYSize;
		xStart = _xStart;
		yStart = _yStart;
		cellSize = _cellSize;

	}

	@Override
	public int getXSize() {

		return gridXSize;
	}

	@Override
	public int getYSize() {
		// TODO Auto-generated method stub
		return gridYSize;
	}

	@Override
	public boolean isValidGridPosition(int _x, int _y) {

		if (_x > gridXSize - 1 || _y > gridYSize - 1 || _x < 0 || _y < 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isObstructed(int _x, int _y) {
		Point p = getCoordinatesOfGridPosition(_x, _y);
		return (!linemap.inside(p));
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {

		float x = xStart + cellSize * _x;
		float y = yStart + cellSize * _y;
		Point newPoint = new Point(x, y);
		return newPoint;
	}

	@Override
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {

		float x1 = xStart + cellSize * _x1;
		float x2 = xStart + cellSize * _x2;
		float y1 = yStart + cellSize * _y1;
		float y2 = yStart + cellSize * _y2;

		Line l = new Line(x1, y1, x2, y2);
		Line[] lines = linemap.getLines();

		if (isObstructed(_x1, _y1) || isObstructed(_x2, _y2)) {
			return false;
		}
		if (!isValidGridPosition(_x1, _y1) || !isValidGridPosition(_x2, _y2)) {
			return false;
		}
		for (int i = 0; i < lines.length; i++) {
			Point p = lines[i].intersectsAt(l);
			if (p != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		float x = xStart + cellSize * _x;
		float y = xStart + cellSize * _y;
		Pose pose = new Pose(x, y, _heading);

		return linemap.range(pose);
	}

}
