package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Author: Nick Avtges
 */

// TODO:
// implement park mode, test its behavior when robot is disabled
// implement capability to easily switch between closed loop speed control
//         and open loop percentVbus control
// test drive behavior with brake mode enabled

public class Drive extends Subsystem {

	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;

	private final static double MAX_SPEED = 1200;
	private final static double P = 1.2;
	private final static double I = 0.0;
	private final static double D = 0.0;
	
	public Drive() {

		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lTalonMaster.reverseSensor(true);
		// lTalonMaster.reverseOutput(true);
		lTalonMaster.setPosition(0);
		lTalonMaster.setPID(P, I, D);
		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
//		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		lTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());

		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalonMaster.reverseSensor(true);
		// rTalonMaster.reverseOutput(true);
		rTalonMaster.setPosition(0);
		rTalonMaster.setPID(P, I, D);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
//		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

	}

	public void setControlMode(CANTalon.TalonControlMode mode) {
		switch (mode){

		}
		
		// lTalonMaster.reverseOutput(true);
		lTalonMaster.setPosition(0);
		lTalonMaster.setPID(P, I, D);
		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

		// rTalonMaster.reverseOutput(true);
		rTalonMaster.setPosition(0);
		rTalonMaster.setPID(P, I, D);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
//		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
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
	
	public void setPosition(double lPosition, double rPosition){
		lTalonMaster.setPosition(lPosition);
		rTalonMaster.setPosition(rPosition);
	}
	
	public double getLeftPosition(){
		return lTalonMaster.getPosition();
	}
	
	public double getRightPosition(){
		return rTalonMaster.getPosition();
	}

	public void park() {
		lTalonMaster.setPosition(0);
		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Position);
		lTalonMaster.set(0);

		rTalonMaster.setPosition(0);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Position);
		rTalonMaster.set(0);
	}
	
	public void unpark() {
		lTalonMaster.setPosition(0);
		lTalonMaster.setPID(P, I, D);
		lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);

		rTalonMaster.setPosition(0);
		rTalonMaster.setPID(P, I, D);
		rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
	}
	
	public void setSpeed(double leftDirection, double rightDirection) {
		SmartDashboard.putNumber("Left Input: ", leftDirection);
		SmartDashboard.putNumber("Right Input: ", rightDirection);

		leftDirection = Robot.oi.applyDeadZone(leftDirection);
		rightDirection = Robot.oi.applyDeadZone(rightDirection);

		lTalonMaster.set(leftDirection * MAX_SPEED);
		rTalonMaster.set(-rightDirection * MAX_SPEED);

//		lTalonMaster.set(leftDirection);
//		rTalonMaster.set(-rightDirection);
		
		updateSmartDashboard();
	}
}
