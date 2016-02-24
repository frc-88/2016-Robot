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
	
	private final static double FORWARD_LIMIT = 3000000;
	private final static double BEST_INTAKE = 99999;

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
			armTalon.setForwardSoftLimit(FORWARD_LIMIT);
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
		return armTalon.getEncPosition() >= FORWARD_LIMIT;
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new ArmsWithController());
    }
}
