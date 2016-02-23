package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmsDown extends Command {

	private boolean done;
	
    public ArmsDown() {
    	requires(Robot.arms);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!Robot.arms.isZeroed()) {
    		done = true;
    	} else {
    		Robot.arms.move(0.5);
    	}
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done || Robot.arms.atFwdLimit();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
