package pathfollow;

import java.util.ArrayList;
import java.util.Random;

import rp.systems.WheeledRobotSystem;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

/**
 * The Class Junction.
 */
public class Junction implements Behavior {

	/** The left wheel. */
	private NXTMotor leftWheel;

	/** The right wheel. */
	private NXTMotor rightWheel;

	/** The threshold1. */
	private double threshold1;

	/** The threshold2. */
	private double threshold2;

	/** The leftlight. */
	private LightSensor leftlight;

	/** The rightlight. */
	private LightSensor rightlight;

	/** The control. */
	boolean control;
	
	/** The list of movements. */
	private ArrayList<String> list;

	/**
	 * Instantiates a new junction.
	 *
	 * @param leftlight
	 *            the leftlight
	 * @param rightlight
	 *            the rightlight
	 * @param threshold1
	 *            the threshold1
	 * @param threshold2
	 *            the threshold2
	 */
	public Junction(ArrayList<String> list, LightSensor leftlight, LightSensor rightlight,
			double threshold1, double threshold2) {

		this.leftWheel = new NXTMotor(MotorPort.B);
		this.rightWheel = new NXTMotor(MotorPort.C);
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
		this.leftlight = leftlight;
		this.rightlight = rightlight;
		this.list = list;

	}
	
	/**
	 * Go backward.
	 */
	private void goBackward() {
		System.out.println("backward");
		rightWheel.forward();
		leftWheel.backward();
		Delay.msDelay(2400);
		control = false;
	}


	/**
	 * Go forward.
	 */
	private void goForward() {
		System.out.println("forward");
		rightWheel.forward();
		leftWheel.forward();
		Delay.msDelay(700);
		control = false;
	}

	/**
	 * Turn right.
	 *
	 * @param threshold2
	 *            The right sensor threshold
	 */
	public void turnRight(double threshold2) {

		rightWheel.forward();
		leftWheel.forward();
		Delay.msDelay(800);

		while (rightlight.getLightValue() > threshold2) {
			rightWheel.forward();
			leftWheel.backward();
		}
		control = false;

	}

	/**
	 * Turn left.
	 *
	 * @param threshold1
	 *            The left sensor threshold
	 */
	public void turnLeft(double threshold1) {

		rightWheel.forward();
		leftWheel.forward();

		Delay.msDelay(800);

		while (leftlight.getLightValue() > threshold1) {
			leftWheel.forward();
			rightWheel.backward();

		}
		control = false;

	}

	@Override
	public boolean takeControl() {
		// takes control when both sensors are on a black line i.e when there is
		// a junction
		control = ((leftlight.getLightValue() < threshold1) && (rightlight
				.getLightValue() < threshold2));

		return control;
	}

	@Override
	public void action() {

		leftWheel.stop();
		rightWheel.stop();

		leftWheel.setPower(30);
		rightWheel.setPower(30);

		while (control) {
			// we use random generator for the turns it takes values 0,1 and 2
			// then it starts follow the arraylist path
			if (list.size() == 0) {

				Sound.beepSequence();
				Sound.beepSequence();
				Sound.beepSequence();
				System.exit(0);

			}

			else if (list.get(0).equals("right")) {
				list.remove(0);

				turnRight(threshold2);

			}

			else if (list.get(0).equals("left")) {
				list.remove(0);

				turnLeft(threshold1);
			}

			else if (list.get(0).equals("goForward")) {
				list.remove(0);

				goForward();
			} else if (list.get(0).equals("goBackwards")) {
				list.remove(0);

				goBackward();
			}
		}

	}

	@Override
	public void suppress() {
	}

}
