package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivePark2 extends Command {
	private double targetDistance;
	
    public DrivePark2() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetDistance = Robot.lidar.getDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.lidar.getDistance() > targetDistance){
    		Robot.drive.set(0.2, 0.2);
    	}
    	else{
    		Robot.drive.set(0.0, 0.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
