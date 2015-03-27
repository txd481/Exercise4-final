package pathfollow;

import interpretation.Interpretation;

import java.util.ArrayList;
import java.util.Stack;

import nxtsearch.Coordinate;
import nxtsearch.MyMap;
import nxtsearch.NXTBreathFirst;
import nxtsearch.NewGraph;
import nxtsearch.Node;
import nxtsearch.PopulateGraph;
import nxtsearch.Predicate;
//import exercise4.Coordinate;
//import exercise4.MyMap;
//import exercise4.NXTBreathFirst;
//import exercise4.NewGraph;
//import exercise4.Node;
//import exercise4.Predicate;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.Pose;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import rp.config.RobotConfigs;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;

/**
 * The Class PathFollow.
 */
public class PathFollow extends RobotProgrammingDemo {

	/** The m_robot. */
	private final WheeledRobotSystem m_robot;

	/** The list. */
	private ArrayList<String> list;

	/** The left wheel. */
	private NXTMotor leftWheel;

	/** The right wheel. */
	private NXTMotor rightWheel;

	/**
	 * Instantiates a new path follow.
	 *
	 * @param m_robot
	 *            the m_robot
	 * @param list
	 *            the list
	 */
	public PathFollow(WheeledRobotSystem m_robot, ArrayList<String> list) {
		this.m_robot = m_robot;
		this.list = list;
		this.leftWheel = new NXTMotor(MotorPort.B);
		this.rightWheel = new NXTMotor(MotorPort.C);
	}

	// method for calibration
	/**
	 * Calibrate.
	 *
	 * @return the array
	 */
	public static double[] calibrate() {
		LightSensor leftlight = new LightSensor(SensorPort.S3);
		LightSensor rightlight = new LightSensor(SensorPort.S2);

		double array[] = new double[4];
		double leftlighthigh = 0;
		double rightlighthigh = 0;
		double leftlightlow = 0;
		double rightlightlow = 0;
		boolean done = false;

		while (!done) {
			if (Button.LEFT.isDown()) {
				leftlighthigh = leftlight.getLightValue();
				rightlighthigh = rightlight.getLightValue();
				System.out.println("high done");

			}

			if (Button.RIGHT.isDown()) {
				leftlightlow = leftlight.getLightValue();
				rightlightlow = rightlight.getLightValue();
				System.out.println("low done");

				done = true;

			}

		}

		array[0] = leftlighthigh;
		array[1] = rightlighthigh;
		array[2] = leftlightlow;
		array[3] = rightlightlow;

		return array;

	}

	public void run() {
		System.out.print("something2");
		Button.waitForAnyPress();
		double array[] = calibrate();
		double leftlighthigh = array[0];
		double rightlighthigh = array[1];
		double leftlightlow = array[2];
		double rightlightlow = array[3];
		Button.waitForAnyPress();
		LightSensor leftlight = new LightSensor(SensorPort.S3);
		LightSensor rightlight = new LightSensor(SensorPort.S2);

		OpticalDistanceSensor sensor = new OpticalDistanceSensor(SensorPort.S1);

		while (m_run) {

			Button.waitForAnyPress();
			double threshold1 = (leftlightlow + (leftlighthigh - leftlightlow) / 2);
			double threshold2 = (rightlightlow + (rightlighthigh - rightlightlow) / 2);
			// arbitrator which operates with three different behaviours
			Arbitrator arby = new Arbitrator(new Behavior[] {
					new Forward(leftlighthigh, rightlighthigh, leftlightlow,
							rightlightlow, threshold1, threshold2),
					new Junction(list, leftlight, rightlight, threshold1,
							threshold2) });
			arby.start();
		}

	}

	public static void main(String[] args) {

		WheeledRobotSystem robot = new WheeledRobotSystem(
				RobotConfigs.CASTOR_BOT);

		NewGraph<Coordinate> ng = new NewGraph<Coordinate>();
		PopulateGraph<Coordinate> pg = new PopulateGraph<Coordinate>();
		pg.populateGraph(ng);

		NXTBreathFirst<Coordinate> bf = new NXTBreathFirst<Coordinate>();
		Stack<Node<Coordinate>> path = bf.findPath(
				ng.nodeWith(new Coordinate(1, 1)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(6, 4));
					}
				});

		System.out.println("Finding a path using BFS:");

		System.out.println(path.size());

		Heading head = Heading.PLUS_Y;
		Interpretation inter = new Interpretation(head);

		ArrayList<String> list = inter.interpret(path);
		System.out.print(" something");
		for (int i = 9; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		Button.waitForAnyPress();
		RobotProgrammingDemo demo = new PathFollow(robot, list);
		demo.run();
	}

}
