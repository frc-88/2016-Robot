package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team88.robot.Robot;

/**
 *
 */
public class HDSMove extends Command {
	
	public static final double ANGLE_MOTOR_LIMIT = 1000.0;
	public static final double ELEVATOR_MOTOR_LIMIT = 1000.0;

    public HDSMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.HDS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speedElevator = Robot.oi.getDriverLeftVerticalAxis();
    	double encoderValueAngle = CANTalon.FeedbackDevice.QuadEncoder.value;
    	double speedAngle = Robot.oi.getOperatorLeftZAxis();
    	double encoderValueElevator = CANTalon.FeedbackDevice.QuadEncoder.value;
    	
    	if(Math.abs(encoderValueElevator) >= ELEVATOR_MOTOR_LIMIT){
    		if(speedElevator>0 && encoderValueElevator>0){
    			speedElevator = 0.0;
    		}
    		else if(speedElevator<0 && encoderValueElevator<0){
    			speedElevator = 0.0;
    		}
    	}
    	
    	if(Math.abs(encoderValueAngle) >= ANGLE_MOTOR_LIMIT){
    		if(speedAngle<0 && encoderValueAngle<0){
    			speedAngle = 0.0;
    		}
    		else if(speedAngle>0 && encoderValueAngle>0){
    			speedAngle = 0.0;
    		}
    	}
    	
    	Robot.HDS.moveAngle(speedAngle);
    	Robot.HDS.moveElevator(speedElevator);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	Robot.HDS.moveAngle(0.0);
    	Robot.HDS.moveElevator(0.0);
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.HDS.moveAngle(0);
    }
}
