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
	public static int leftMotorController1=1;
	public static int leftMotorController2=2;
	// public static int leftMotorController3=3;

	public static int rightMotorController1=4;
	public static int rightMotorController2=5;
	// public static int rightMotorController3=6;

// Intake
	public static int frontRollerMotorController = 7;
	public static int innerRollerMotorController = 8;
    // light sensor switch to known when boulder is in the nest
	
// Arms
	public static int armMotorController = 10;
	// encoder to know angle of arms

// HDS - Hook Delivery System
	public static int hdsElevatorMotorController = 12;
	public static int hdsAngleMotorController = 13;
	// switch to determine if hook is on bar, limit switch? 
	// encoder on angle motor
	
// Ladder
	// some mechanism to release clock springs, solenoid?
	// or, alternatively, one motor to run winch
	
// Camera
	public static String cameraName = "cam0";
	
// Shooter ???

// CatABuster ???	
}
