package org.usfirst.frc.team88.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Drive
	// encoders on masters
	public static int driveLeftMaster = 1;
	public static int driveLeftSlave = 2;

	public static int driveRightMaster = 6;
	public static int driveRightSlave = 8;

	// Intake
	// encoder on shooterMotor to know when it is up to speed
	// light sensor switch to know when boulder is in the holder
	public static int intakeMotor = 4;
	public static int shooterMotor = 3;
	public static int boulderHolder = 0;

	// Arms
	// encoder to know angle of arms
	// limit switch for reverse limit and to zero encoder
	public static int armMotorController = 9;

	// Camera
	public static String cameraName = "cam0";

	// Lights
	public static final int lightsDigitalOut1 = 0;
	public static final int lightsDigitalOut2 = 1;
	public static final int lightsDigitalOut3 = 2;
	
	public static final int lightsAnalogOut = 1;

	// HDS - Hook Delivery System
	public static int hdsElevatorMotorController = 5;
	public static int hdsAngleMotorController = 7;

	// Climber
	public static int climberMotor = 10;
}
