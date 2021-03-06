package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithController;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * defenses crumble
 * underneath the power of 
 *    gray pneumatic tires
 */

public class Drive extends Subsystem implements PIDOutput {

	Preferences prefs;
	
	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;
	private CANTalon.TalonControlMode controlMode;
	private double priorLeftInput, priorRightInput;
	
	public AHRS navX;
	public PIDController turnController;

	private final static double MAX_SPEED = 800;
	private final static double SPEED_P = 0.4;
	private final static double SPEED_I = 0.0;
	private final static double SPEED_D = 0.0;
	private final static double SPEED_F = 1.3;
	private final static int SPEED_IZONE = 0;
	private final static double SPEED_RAMPRATE = 3.0;
	private final static int SPEED_PROFILE = 0;

	private final static double POSITION_P = 0.0;
	private final static double POSITION_I = 0.0;
	private final static double POSITION_D = 0.0;
	private final static double POSITION_F = 0.0;
	private final static int POSITION_IZONE = 0;
	private final static double POSITION_RAMPRATE = 0.0;
	private final static int POSITION_PROFILE = 1;

	private final static double ROTATE_P = 0.01;
	private final static double ROTATE_I = 0.0002;
	private final static double ROTATE_D = 0.0;
	private final static double ROTATE_F = 0.0;
	private final static double ROTATE_TOLERANCE = 4.0f;
	
	private static double LIDAR_DISTANCE = 275;

	public Drive() {
		// instantiate NavX
		try {
			navX = new AHRS(SerialPort.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}

		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lTalonMaster.reverseSensor(true);
		lTalonMaster.enableBrakeMode(true);
		lTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		lTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE,
				POSITION_PROFILE);

		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		lTalonSlave.enableBrakeMode(true);
		lTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		lTalonSlave.set(lTalonMaster.getDeviceID());

		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rTalonMaster.reverseSensor(true);
		rTalonMaster.enableBrakeMode(true);
		rTalonMaster.setPID(SPEED_P, SPEED_I, SPEED_D, SPEED_F, SPEED_IZONE, SPEED_RAMPRATE, SPEED_PROFILE);
		rTalonMaster.setPID(POSITION_P, POSITION_I, POSITION_D, POSITION_F, POSITION_IZONE, POSITION_RAMPRATE,
				POSITION_PROFILE);

		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);
		rTalonSlave.enableBrakeMode(true);
		rTalonSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		rTalonSlave.set(rTalonMaster.getDeviceID());

		setControlMode(CANTalon.TalonControlMode.Speed);
		resetPosition();

		// set up turnController
		turnController = new PIDController(ROTATE_P, ROTATE_I, ROTATE_D, ROTATE_F, navX, this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(ROTATE_TOLERANCE);
		turnController.setContinuous(true);
		
		/* Add the PID Controller to the Test-mode dashboard, allowing manual */
		/* tuning of the Turn Controller's P, I and D coefficients. */
		/* Typically, only the P value needs to be modified. */
		LiveWindow.addActuator("DriveSystem", "TurnController", turnController);
	}

	public double getLeftPosition() {
		return lTalonMaster.getEncPosition();
	}

	public double getRightPosition() {
		return rTalonMaster.getEncPosition();
	}

	public void resetPosition() {
		lTalonMaster.setEncPosition(0);
		rTalonMaster.setEncPosition(0);
	}

	public void set(double left, double right) {
		SmartDashboard.putNumber("Left Input: ", left);
		SmartDashboard.putNumber("Right Input: ", right);

// the below code does not work...think about initial states
		//		if (left - priorLeftInput > 0.1) {
//			left = priorLeftInput + 0.1;
//		} else if (left - priorLeftInput < 0.1) {
//			left = priorLeftInput - 0.1;
//		}
//
//		if (right - priorRightInput > 0.1) {
//			right = priorRightInput + 0.1;
//		} else if (right - priorRightInput < 0.1) {
//			right = priorRightInput - 0.1;
//		}
		
		switch (controlMode) {
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

//		priorLeftInput = left;
//		priorRightInput = right;
		
		updateSmartDashboard();
	}

	public void setControlMode(CANTalon.TalonControlMode mode) {
		switch (mode) {
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

	/* This function is invoked periodically by the PID Controller, */
	/* based upon navX-MXP yaw angle input and PID Coefficients. */
	public void pidWrite(double output) {
		double max = 0.4;
		double min = 0.075;
		
		if (output > max) {
			output = max;
		} else if (output < min && output >0) {
			output = min;
		} else if (output == 0) {
			output = 0;
		} else if (output > (0-min)) {
			output = 0 - min;
		} else if (output < (0-max)) {
			output = 0 - max;
		}
		
		set(-output, output);
	}

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder: ", lTalonMaster.getPosition());
//		SmartDashboard.putNumber("Left Master Voltage: ", lTalonMaster.getOutputVoltage());
//		SmartDashboard.putNumber("Left Master Current: ", lTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Left Master Speed: ", lTalonMaster.getSpeed());
//		SmartDashboard.putNumber("Left Slave Voltage: ", lTalonSlave.getOutputVoltage());
//		SmartDashboard.putNumber("Left Slave Current: ", lTalonSlave.getOutputCurrent());

		SmartDashboard.putNumber("Right Encoder: ", rTalonMaster.getPosition());
//		SmartDashboard.putNumber("Right Master Voltage: ", rTalonMaster.getOutputVoltage());
//		SmartDashboard.putNumber("Right Master Current: ", rTalonMaster.getOutputCurrent());
		SmartDashboard.putNumber("Right Master Speed: ", -rTalonMaster.getSpeed());
//		SmartDashboard.putNumber("Right Slave Voltage: ", rTalonSlave.getOutputVoltage());
//		SmartDashboard.putNumber("Right Slave Current: ", rTalonSlave.getOutputCurrent());

		SmartDashboard.putNumber("Lidar", Robot.lidar.getDistance());

		navXData();
	}

	private void navXData() {
		/* Display 6-axis Processed Angle Data */
		SmartDashboard.putBoolean("IMU_Connected", navX.isConnected());
		SmartDashboard.putBoolean("IMU_IsCalibrating", navX.isCalibrating());
		SmartDashboard.putNumber("IMU_Yaw", navX.getYaw());
		SmartDashboard.putNumber("IMU_Pitch", navX.getPitch());
		SmartDashboard.putNumber("IMU_Roll", navX.getRoll());

		SmartDashboard.putNumber("Displacement_X", navX.getDisplacementX());
		SmartDashboard.putNumber("Displacement_Y", navX.getDisplacementY());

	}
    
    public double getRoll(){
    	return navX.getRoll();
    }
}
