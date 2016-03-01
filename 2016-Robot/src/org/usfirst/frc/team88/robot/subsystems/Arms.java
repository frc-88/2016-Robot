package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.ArmsWithController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arms extends Subsystem {
    
	private final CANTalon armTalon;
	private boolean armZeroed;
	
	public final static double POS_FORWARD_LIMIT = 3000000;
	public final static double POS_CDF = 99999;
	public final static double POS_INTAKE = 77777;
	public final static double POS_PORTCULLIS = 55555;
	public final static double POS_REVERSE_LIMIT = 0;
	public final static double AUTO_SPEED = 0.5;

	public Arms(){
		armTalon = new CANTalon(RobotMap.armMotorController);
		armTalon.enableBrakeMode(true);
		armTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		armTalon.enableLimitSwitch(false, true);
		armZeroed = false;
	}
	
	public void move(double speed){
		if (armTalon.isRevLimitSwitchClosed() && speed < 0.0) {
			armTalon.set(0);
			armTalon.setPosition(0);
			armTalon.setForwardSoftLimit(POS_FORWARD_LIMIT);
			armTalon.enableForwardSoftLimit(true);
			armZeroed = true;
		} else {
			armTalon.set(speed);
		}
		
		SmartDashboard.putNumber("Arm Voltage: ", armTalon.getOutputVoltage());
		SmartDashboard.putNumber("Arm Current: ", armTalon.getOutputCurrent());
		SmartDashboard.putNumber("Arm Encoder: ", armTalon.getEncPosition());
		SmartDashboard.putNumber("Arm Forward Limit: ", atFwdLimit() ? 1 : 0);
		SmartDashboard.putNumber("Arm Reverse Limit: ", atRevLimit() ? 1 : 0);
		SmartDashboard.putNumber("Arm Zeroed: ", isZeroed() ? 1 : 0);
	}

	public boolean isZeroed() {
		return armZeroed;
	}
	
	public boolean atRevLimit() {
		return armTalon.isRevLimitSwitchClosed();
	}
	
	public boolean atFwdLimit() {
		return armTalon.getEncPosition() >= POS_FORWARD_LIMIT;
	}
	
	public double getPosition() {
		return armTalon.getEncPosition();
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArmsWithController());
    }
}
