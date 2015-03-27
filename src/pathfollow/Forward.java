package pathfollow;


import rp.systems.WheeledRobotSystem;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class Forward implements Behavior {

	private boolean m_suppressed = false;

	private NXTMotor leftWheel;
	private NXTMotor rightWheel;
	private double threshold1;
	private double threshold2;
	private double leftlighthigh;
	private double rightlighthigh;

	public Forward(double leftlighthigh,
			double rightlighthigh, double leftlightlow, double rightlightlow,
			double threshold1, double threshold2) {

		this.leftWheel = new NXTMotor(MotorPort.B);
		this.rightWheel = new NXTMotor(MotorPort.C);
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
		this.leftlighthigh = leftlighthigh;
		this.rightlighthigh = rightlighthigh;

	}

	public boolean takeControl() {

		return true;
	}

	@Override
	public void action() {
		// follows the line using PID as in the first part of the exercise

		double targetReading1 = leftlighthigh;
		double output1 = 2;
		double saveError1 = 2;

		double targetReading2 = rightlighthigh;

		double kp = 2.2;
		double ki = 0.07;
		double kd = 5;

		double p = 0;
		double i = 0;
		double d = 0;

		LightSensor leftlight = new LightSensor(SensorPort.S3);
		LightSensor rightlight = new LightSensor(SensorPort.S2);

		double max = output1;
		while (!m_suppressed) {

			double l1 = leftlight.getLightValue();
			double l2 = rightlight.getLightValue();

			double error1 = (targetReading1 - l1) + (targetReading2 - l2);

			p = error1;
			i = i + error1;
			d = error1 - saveError1;

			output1 = (p * kp) + (i * ki) + (d * kd);

			if (output1 > max) {
				max = output1;

			}
			// if both of the sensors detect black line that means the robot is
			// on a junction
			
			else if (leftlight.getLightValue() < threshold1) {

				leftWheel.setPower(30 + (int) Math.abs((output1 / max) * 40));
				rightWheel.setPower(30 + (int) Math.abs((output1 / max) * 35));

				rightWheel.stop();
				leftWheel.forward();

				saveError1 = error1;

			}

			else if (rightlight.getLightValue() < threshold2) {

				leftWheel.setPower(30 + (int) Math.abs((output1 / max) * 35));
				rightWheel.setPower(30 + (int) Math.abs((output1 / max) * 40));

				leftWheel.stop();
				rightWheel.forward();

				saveError1 = error1;
			}

			else {

				leftWheel.setPower(30);
				rightWheel.setPower(30);
				rightWheel.forward();
				leftWheel.forward();

			}

		}
		Thread.yield();
		m_suppressed = false;
	}

	@Override
	public void suppress() {
		m_suppressed = true;

	}

}
