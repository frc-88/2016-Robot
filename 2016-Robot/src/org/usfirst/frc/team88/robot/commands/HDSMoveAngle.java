package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.OI;

/**
 *
 */
public class HDSMoveAngle extends Command {
	
	public static final double ANGLE_MOTOR_LIMIT = 1000.0;

    public HDSMoveAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.HDS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftTrigger = Robot.oi.getOperatorLeftZAxis();
    	double rightTrigger = Robot.oi.getOperatorRightZAxis();
    	
    	double encoderValue = CANTalon.FeedbackDevice.QuadEncoder.value;
    	double speed = leftTrigger - rightTrigger;
    	
    	if(Math.abs(encoderValue) >= ANGLE_MOTOR_LIMIT && (leftTrigger!= 0 || rightTrigger !=0)){
    		speed = 0.0;
    	}
    	
    	Robot.HDS.moveAngle(speed);
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
    	Robot.HDS.moveAngle(0);
    }
}
