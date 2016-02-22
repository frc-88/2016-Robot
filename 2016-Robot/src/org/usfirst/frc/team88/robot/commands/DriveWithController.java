package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithController extends Command {
	private double left, right;
	
    public DriveWithController() {
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if ((Robot.drive.getControlMode() == CANTalon.TalonControlMode.Speed) ||
    		(Robot.drive.getControlMode() == CANTalon.TalonControlMode.PercentVbus)) {

    		left = Robot.oi.getDriverLeftVerticalAxis();
        	right = Robot.oi.getDriverRightVerticalAxis();
        	
    		left = Robot.oi.applyDeadZone(left);
    		right = Robot.oi.applyDeadZone(right);
        	
        	Robot.drive.set(left, right);
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
