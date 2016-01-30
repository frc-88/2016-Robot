package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerSimple;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Author:
 *	Nick Avtges
 */
public class Drive extends Subsystem {
    
	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;
	
	private final static double MAX_SPEED = 500;
	private final static double RIGHT_P = 0.75;
	private final static double LEFT_P = 0.75;
	private final static double I = 0.0; 
	private final static double D = 0.0;
	private final static double DEAD_ZONE = 0.2;
			
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
				
		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
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
		rTalonMaster.setPosition(0);
		rTalonMaster.setPID(RIGHT_P, I, D);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

	}
	
	private void updateSmartDashboard() {
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
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithControllerClosed());
	}
	
	public void DriveSpeed (double leftDirection, double rightDirection){
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
		
		updateSmartDashboard();
	}
	
	public void DriveSimple(double leftDirection, double rightDirection){
		lTalonMaster.set(leftDirection);
		rTalonMaster.set(rightDirection);
	}
}

