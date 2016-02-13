package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HDSMoveElevator extends Command {

	public static final double ELEVATOR_MOTOR_LIMIT = 1000.0;
	
    public HDSMoveElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.HDS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Robot.oi.getDriverLeftVerticalAxis();
    	
    	double encoderValue = CANTalon.FeedbackDevice.QuadEncoder.value;
    	
    	if(Math.abs(encoderValue) >= ELEVATOR_MOTOR_LIMIT){
    		if(speed>0 && encoderValue>0){
    			speed = 0.0;
    		}
    		else if(speed<0 && encoderValue<0){
    			speed = 0;
    		}
    	}
    	
    	Robot.HDS.moveElevator(speed);
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
    	Robot.HDS.moveElevator(0);
    }
}
