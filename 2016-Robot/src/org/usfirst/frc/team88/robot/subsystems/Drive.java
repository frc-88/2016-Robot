package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Author:
 *	Nick Avtges
 */
public class Drive extends Subsystem {
    
	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;
	
	private final static double MAX_SPEED = 600;
	private final static double RIGHT_P = 1.5;
	private final static double LEFT_P = 1.5;
	private final static double I = 0.01; 
	private final static double D = 0.02;
	private final static double DEAD_ZONE = 0.2;
	private double difference = 0.0;
			
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
				
		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    lTalonMaster.reverseSensor(true);
		//lTalonMaster.reverseOutput(true);
		lTalonMaster.setPosition(0);
		lTalonMaster.setPID(LEFT_P, I, D);
		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		lTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());
		
		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	    rTalonMaster.reverseSensor(true);
	    //rTalonMaster.reverseOutput(true);
		rTalonMaster.setPosition(0);
		rTalonMaster.setPID(RIGHT_P, I, D);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

	}
	
	private void updateSmartDashboard(double diff) {
		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
		SmartDashboard.putNumber("Left Master Voltage: ", lTalonMaster.getOutputVoltage());
		SmartDashboard.putNumber("Left Master Current: ", lTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Left Master Speed: ", lTalonMaster.getSpeed());
		SmartDashboard.putNumber("Left Slave Voltage: ", lTalonSlave.getOutputVoltage());
		SmartDashboard.putNumber("Left Slave Current: ", lTalonSlave.getOutputCurrent());

		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
		SmartDashboard.putNumber("Right Master Voltage: ", rTalonMaster.getOutputVoltage());
		SmartDashboard.putNumber("Right Master Current: ", rTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Right Master Speed: ", -rTalonMaster.getSpeed());
		SmartDashboard.putNumber("Right Slave Voltage: ", rTalonSlave.getOutputVoltage());
		SmartDashboard.putNumber("Right Slave Current: ", rTalonSlave.getOutputCurrent());
		
		//Positive distance means favoring left side.  negative means favoring right side.
		SmartDashboard.putNumber("Difference in Speed: ", diff);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithControllerClosed());
	}
	
	public void setSpeed (double leftDirection, double rightDirection){
		SmartDashboard.putNumber("Left Input: ", leftDirection);
		SmartDashboard.putNumber("Right Input: ", rightDirection);
		
		if(Math.abs(leftDirection) <= DEAD_ZONE){
			leftDirection = 0;
		}
		if(Math.abs(rightDirection) <= DEAD_ZONE){
			rightDirection = 0;
		}
		
		lTalonMaster.set(-leftDirection * MAX_SPEED);
		rTalonMaster.set(rightDirection * MAX_SPEED);
		
		difference = (Math.abs(lTalonMaster.getSpeed())-Math.abs(rTalonMaster.getSpeed()));
		updateSmartDashboard(difference);
	}
}

