package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBackwards extends Command {
	private double distance;
	private String prefName;
	private double prefValue;
	private boolean fast;
	private Preferences prefs = Preferences.getInstance(); 

	
    public AutoBackwards(double input) {
    	this(input,false);
    }

    public AutoBackwards(double input, boolean speedy) {
    	requires(Robot.drive);
    	
    	prefName = null;
    	fast = speedy;
    	distance = input;
    }

    public AutoBackwards(String pref, double input) {
    	this(pref, input, false);
    }

    public AutoBackwards(String pref, double input, boolean speedy) {
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

    	Robot.drive.resetPosition();
    	if (fast) {
        	Robot.drive.set(0.72, 0.7);
    	} else {
    		Robot.drive.set(0.27, 0.2);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.drive.getLeftPosition()) > distance || Math.abs(Robot.drive.getRightPosition()) > distance);
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
