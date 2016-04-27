package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBackwardsToRoll extends Command {	

	private double roll;
	private boolean fast;
	private String prefName;
	private double prefValue;
	private double leftSpeed;
	private double rightSpeed;
	private Preferences prefs = Preferences.getInstance();

	
    public AutoBackwardsToRoll(double input) {
    	this(input,false);
    }

    public AutoBackwardsToRoll(double input, boolean speedy) {
    	requires(Robot.drive);
    	
    	prefName = null;
    	fast = speedy;
    }

    public AutoBackwardsToRoll(String pref, double input) {
    	this(pref, input, false);
    }

    public AutoBackwardsToRoll(String pref, double input, boolean speedy) {
    	requires(Robot.drive);
    	
    	prefName = pref;
    	fast = speedy;
    	prefValue = input;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (prefName != null) {
    		roll = prefs.getDouble(prefName, prefValue);
    	}

    	Robot.drive.resetPosition();
    	if (fast) {
    		leftSpeed = prefs.getDouble("leftFast", 0.72);
    		rightSpeed = prefs.getDouble("rightFast", 0.7);
        	Robot.drive.set(leftSpeed, rightSpeed);
    	} else {
    		leftSpeed = prefs.getDouble("leftSlow", 0.27);
    		rightSpeed = prefs.getDouble("rightSlow", 0.2);
    		Robot.drive.set(leftSpeed, rightSpeed);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.updateSmartDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Math.abs(Robot.drive.getRoll()) > roll;
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
