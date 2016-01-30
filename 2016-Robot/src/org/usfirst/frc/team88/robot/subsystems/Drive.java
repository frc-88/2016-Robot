package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
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
	private final double MAX_SPEED;
	
	private final static double P = 1.0; 
	private final static double I = 0.002; 
	private final static double D = 0.0;
	private final static double F = 0.0;
	private final static int IZONE = 0;
	private final static int PROFILE = 0;
	private final static double RAMPRATE = 36.0;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
		
		MAX_SPEED = 300.0;
		
		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lTalonMaster.setPID(P, I, D, F, IZONE, RAMPRATE, PROFILE);

		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		lTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());

		
		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalonMaster.setPID(P, I, D, F, IZONE, RAMPRATE, PROFILE);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

	}
	
	private void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getEncPosition());
		SmartDashboard.putNumber("Left Encoder Quad A: ", lTalonMaster.getPinStateQuadA());
		SmartDashboard.putNumber("Left Encoder Quad B: ", lTalonMaster.getPinStateQuadB());
		SmartDashboard.putNumber("Left Encoder Quad Idx: ", lTalonMaster.getPinStateQuadIdx());
		
		SmartDashboard.putNumber("Left Master Voltage: ", lTalonMaster.getOutputVoltage());
		SmartDashboard.putNumber("Left Master Current: ", lTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Left Slave Voltage: ", lTalonSlave.getOutputVoltage());
		SmartDashboard.putNumber("Left Slave Current: ", lTalonSlave.getOutputCurrent());

		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getEncPosition());
		SmartDashboard.putNumber("Right Master Voltage: ", rTalonMaster.getOutputVoltage());
		SmartDashboard.putNumber("Right Master Current: ", rTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Right Slave Voltage: ", rTalonSlave.getOutputVoltage());
		SmartDashboard.putNumber("Right Slave Current: ", rTalonSlave.getOutputCurrent());
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithControllerSimple());
	}
	
	public void DriveSpeed (double leftDirection, double rightDirection){
		lTalonMaster.set(leftDirection * MAX_SPEED);
		rTalonMaster.set(rightDirection * MAX_SPEED);
		
	}
	
	public void DriveSimple(double leftDirection, double rightDirection){
		lTalonMaster.set(leftDirection);
		rTalonMaster.set(rightDirection);
	}
}

