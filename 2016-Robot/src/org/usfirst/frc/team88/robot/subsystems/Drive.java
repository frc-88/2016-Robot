package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.DriveWithController;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
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

	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;
	private CANTalon.TalonControlMode controlMode;
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

	private final static double ROTATE_P = 0.0;
	private final static double ROTATE_I = 0.0;
	private final static double ROTATE_D = 0.0;
	private final static double ROTATE_F = 0.0;
	private final static double ROTATE_TOLERANCE = 2.0f;

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

		setControlMode(CANTalon.TalonControlMode.PercentVbus);
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
		set(-output, output);
	}

	public void updateSmartDashboard() {
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

		/* Display tilt-corrected, Magnetometer-based heading (requires */
		/* magnetometer calibration to be useful) */

		SmartDashboard.putNumber("IMU_CompassHeading", navX.getCompassHeading());

		/*
		 * Display 9-axis Heading (requires magnetometer calibration to be
		 * useful)
		 */
		SmartDashboard.putNumber("IMU_FusedHeading", navX.getFusedHeading());

		/*
		 * These functions are compatible w/the WPI Gyro Class, providing a
		 * simple
		 */
		/* path for upgrading from the Kit-of-Parts gyro to the navx-MXP */

		SmartDashboard.putNumber("IMU_TotalYaw", navX.getAngle());
		SmartDashboard.putNumber("IMU_YawRateDPS", navX.getRate());

		/*
		 * Display Processed Acceleration Data (Linear Acceleration, Motion
		 * Detect)
		 */

		SmartDashboard.putNumber("IMU_Accel_X", navX.getWorldLinearAccelX());
		SmartDashboard.putNumber("IMU_Accel_Y", navX.getWorldLinearAccelY());
		SmartDashboard.putBoolean("IMU_IsMoving", navX.isMoving());
		SmartDashboard.putBoolean("IMU_IsRotating", navX.isRotating());

		/*
		 * Display estimates of velocity/displacement. Note that these values
		 * are
		 */
		/*
		 * not expected to be accurate enough for estimating robot position on a
		 */
		/*
		 * FIRST FRC Robotics Field, due to accelerometer noise and the
		 * compounding
		 */
		/*
		 * of these errors due to single (velocity) integration and especially
		 */
		/* double (displacement) integration. */

		SmartDashboard.putNumber("Velocity_X", navX.getVelocityX());
		SmartDashboard.putNumber("Velocity_Y", navX.getVelocityY());
		SmartDashboard.putNumber("Displacement_X", navX.getDisplacementX());
		SmartDashboard.putNumber("Displacement_Y", navX.getDisplacementY());

		/* Display Raw Gyro/Accelerometer/Magnetometer Values */
		/*
		 * NOTE: These values are not normally necessary, but are made available
		 */
		/*
		 * for advanced users. Before using this data, please consider whether
		 */
		/* the processed data (see above) will suit your needs. */

		SmartDashboard.putNumber("RawGyro_X", navX.getRawGyroX());
		SmartDashboard.putNumber("RawGyro_Y", navX.getRawGyroY());
		SmartDashboard.putNumber("RawGyro_Z", navX.getRawGyroZ());
		SmartDashboard.putNumber("RawAccel_X", navX.getRawAccelX());
		SmartDashboard.putNumber("RawAccel_Y", navX.getRawAccelY());
		SmartDashboard.putNumber("RawAccel_Z", navX.getRawAccelZ());
		SmartDashboard.putNumber("RawMag_X", navX.getRawMagX());
		SmartDashboard.putNumber("RawMag_Y", navX.getRawMagY());
		SmartDashboard.putNumber("RawMag_Z", navX.getRawMagZ());
		SmartDashboard.putNumber("IMU_Temp_C", navX.getTempC());

		/* Omnimount Yaw Axis Information */
		/*
		 * For more info, see
		 * http://navx-mxp.kauailabs.com/installation/omnimount
		 */
		AHRS.BoardYawAxis yaw_axis = navX.getBoardYawAxis();
		SmartDashboard.putString("YawAxisDirection", yaw_axis.up ? "Up" : "Down");
		SmartDashboard.putNumber("YawAxis", yaw_axis.board_axis.getValue());

		/* Sensor Board Information */
		SmartDashboard.putString("FirmwareVersion", navX.getFirmwareVersion());

		/* Quaternion Data */
		/*
		 * Quaternions are fascinating, and are the most compact representation
		 * of
		 */
		/*
		 * orientation data. All of the Yaw, Pitch and Roll Values can be
		 * derived
		 */
		/*
		 * from the Quaternions. If interested in motion processing, knowledge
		 * of
		 */
		/* Quaternions is highly recommended. */
		SmartDashboard.putNumber("QuaternionW", navX.getQuaternionW());
		SmartDashboard.putNumber("QuaternionX", navX.getQuaternionX());
		SmartDashboard.putNumber("QuaternionY", navX.getQuaternionY());
		SmartDashboard.putNumber("QuaternionZ", navX.getQuaternionZ());

		/* Connectivity Debugging Support */
		SmartDashboard.putNumber("IMU_Byte_Count", navX.getByteCount());
		SmartDashboard.putNumber("IMU_Update_Count", navX.getUpdateCount());
	}
}
