package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithController;

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
	private CANTalon.TalonControlMode controlMode;
	
	private final static double MAX_SPEED = 1200;
	private final static double SPEED_P = 1.2;
	private final static double SPEED_I = 0.0;
	private final static double SPEED_D = 0.0;
	private final static double SPEED_F = 0.0;
	private final static int SPEED_IZONE = 0;
	private final static double SPEED_RAMPRATE = 0.0;
	private final static int SPEED_PROFILE = 0;

	private final static double POSITION_P = 1.2;
	private final static double POSITION_I = 0.0;
	private final static double POSITION_D = 0.0;
	private final static double POSITION_F = 0.0;
	private final static int POSITION_IZONE = 0;
	private final static double POSITION_RAMPRATE = 0.0;
	private final static int POSITION_PROFILE = 1;

	public Drive() {
		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lTalonMaster.reverseSensor(true);
		lTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		lTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE, POSITION_PROFILE);

		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		lTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());

		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalonMaster.reverseSensor(true);
		rTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		rTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE, POSITION_PROFILE);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

		setControlMode(CANTalon.TalonControlMode.Speed);
	}

	public double getLeftPosition(){
		return lTalonMaster.getEncPosition();
	}
	
	public double getRightPosition(){
		return rTalonMaster.getEncPosition();
	}

	public void park() {
		set(0.0,0.0);
		setControlMode(CANTalon.TalonControlMode.Position);
		set(0.0,0.0);
	}
	
	public void unpark() {
		setControlMode(CANTalon.TalonControlMode.Speed);
	}
	
	public void set(double left, double right) {
		SmartDashboard.putNumber("Left Input: ", left);
		SmartDashboard.putNumber("Right Input: ", right);

		switch(controlMode) {
		case Speed:
			lTalonMaster.set(left * MAX_SPEED);
			rTalonMaster.set(-right * MAX_SPEED);
			break;
		case Position:
			lTalonMaster.set(left);
			rTalonMaster.set(right);
			break;
		case PercentVbus:
			lTalonMaster.set(left);
			rTalonMaster.set(-right);
			break;
		default:
			break;
		}

		updateSmartDashboard();
	}

	public void setControlMode(CANTalon.TalonControlMode mode) {
		switch (mode){
		case Speed:
			lTalonMaster.setProfile(SPEED_PROFILE);
			lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
			rTalonMaster.setProfile(SPEED_PROFILE);
			rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
			break;
		case Position:
			lTalonMaster.setPosition(0);
			lTalonMaster.setProfile(POSITION_PROFILE);
			lTalonMaster.changeControlMode(CANTalon.TalonControlMode.Position);
			rTalonMaster.setPosition(0);
			rTalonMaster.setProfile(POSITION_PROFILE);
			rTalonMaster.changeControlMode(CANTalon.TalonControlMode.Position);
			break;
		case PercentVbus:
			lTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			rTalonMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			break;
		default:
			return;
		}
		controlMode = mode;
	}
	
	public CANTalon.TalonControlMode getControlMode() {
		return controlMode;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithController());
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
	
}
