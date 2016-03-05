package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoBack3MetersFast extends Command {
	private final static double DISTANCE = 5000.0;
	
    public AutoBack3MetersFast() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetPosition();
    	Robot.drive.set(0.36, 0.13);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.drive.getLeftPosition()) > DISTANCE || Math.abs(Robot.drive.getRightPosition()) > DISTANCE);
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
