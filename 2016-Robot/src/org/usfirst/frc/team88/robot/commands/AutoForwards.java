package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoForwards extends Command {
	private double distance;
	private String prefName;
	private double prefValue;
	private boolean fast;
	private double leftSpeed;
	private double rightSpeed;
	private double roll;
	private double lidarDistance;
	private String setting;
	private Preferences prefs = Preferences.getInstance(); 


	public AutoForwards(double input) {
		this(input,false);
	}

	public AutoForwards(double input, boolean speedy) {
		requires(Robot.drive);

		prefName = null;
		fast = speedy;
		distance = input;
	}

	public AutoForwards(double input, double angle, boolean speedy){
		requires(Robot.drive);

		prefName = null;
		fast = speedy;
		distance = input;
		roll = angle;

		setting = "Roll";
	}

	public AutoForwards(double input, double angle, double lidar, boolean speedy){
		requires(Robot.drive);

		fast = speedy;
		distance = input;
		roll = angle;
		lidarDistance = lidar;

		setting = "Roll & Lidar";
	}
	public AutoForwards(String pref, double input) {
		this(pref, input, false);
	}

	public AutoForwards(String pref, double input, boolean speedy) {
		requires(Robot.drive);

		prefName = pref;
		fast = speedy;
		prefValue = input;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (prefName != null) {
			distance = prefs.getDouble(prefName, prefValue);
		}

		if(setting == "Roll"){
			roll = prefs.getDouble("forwardsRoll", 0.0);
					
			Robot.drive.resetPosition();
			if (fast) {
				leftSpeed = -prefs.getDouble("leftFast", 0.72);
				rightSpeed = -prefs.getDouble("rightFast", 0.7);
				Robot.drive.set(leftSpeed, rightSpeed);
			} else {
				leftSpeed = -prefs.getDouble("leftSlow", 0.27);
				rightSpeed = -prefs.getDouble("rightSlow", 0.2);
				Robot.drive.set(leftSpeed, rightSpeed);
			}
		}
		else if(setting == "Roll & Lidar"){
			roll = prefs.getDouble("setRollR&L", 3.0);
			lidarDistance = prefs.getDouble("setLidarR&L", 100.0);
			
			Robot.drive.resetPosition();
			if (fast) {
				leftSpeed = -prefs.getDouble("leftFast", 0.72);
				rightSpeed = -prefs.getDouble("rightFast", 0.7);
				Robot.drive.set(leftSpeed, rightSpeed);
			} else {
				leftSpeed = -prefs.getDouble("leftSlow", 0.27);
				rightSpeed = -prefs.getDouble("rightSlow", 0.2);
				Robot.drive.set(leftSpeed, rightSpeed);
			}
		}
		else{
			Robot.drive.resetPosition();
			if (fast) {
				leftSpeed = -prefs.getDouble("leftFast", 0.72);
				rightSpeed = -prefs.getDouble("rightFast", 0.7);
				Robot.drive.set(leftSpeed, rightSpeed);
			} else {
				leftSpeed = -prefs.getDouble("leftSlow", 0.27);
				rightSpeed = -prefs.getDouble("rightSlow", 0.2);
				Robot.drive.set(leftSpeed, rightSpeed);
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.updateSmartDashboard();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(setting == "Roll"){
			return (Robot.drive.navX.getRoll() > Math.abs(roll));
		}
		else if(setting == "Roll & Lidar"){
			return ((Robot.drive.navX.getRoll() > Math.abs(roll)) && (Robot.lidar.getDistance() < lidarDistance));
		}
		else{
			return (Math.abs(Robot.drive.getLeftPosition()) > distance || Math.abs(Robot.drive.getRightPosition()) > distance);
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.set(0.0, 0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.set(0.0, 0.0);
	}
}
