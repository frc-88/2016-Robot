package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Delay extends Command {
	private double delay;
	
    public Delay(double timeout) {
    	delay = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(delay);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
