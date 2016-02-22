package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveBackwards3Meters extends Command {
	private final static double DISTANCE = 1000.0;
	
    public DriveBackwards3Meters() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.setSpeed(0.5, 0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.drive.getLeftPosition() > DISTANCE && Robot.drive.getRightPosition() > DISTANCE);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setSpeed(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.setSpeed(0.0, 0.0);
    }
}
