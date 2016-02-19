package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.MoveArmsWithController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arms extends Subsystem {
    
	private final CANTalon armTalon;
	private boolean armZeroed;
	
	private final static double LOWER_LIMIT = 99999;
	private final static double BEST_INTAKE = 99999;

	public Arms(){
		armTalon = new CANTalon(RobotMap.armMotorController);
		armTalon.enableBrakeMode(true);
		armTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		armTalon.enableLimitSwitch(true, false);
		armZeroed = false;
	}
	
	public void move(double speed){
		if (armTalon.isFwdLimitSwitchClosed()) {
			armTalon.set(0);
			armTalon.setPosition(0);
			armTalon.setReverseSoftLimit(LOWER_LIMIT);
			armZeroed = true;
		} else {
			armTalon.set(speed);
		}
		
		SmartDashboard.putNumber("Arm Voltage: ", armTalon.getOutputVoltage());
		SmartDashboard.putNumber("Arm Current: ", armTalon.getOutputCurrent());
		SmartDashboard.putNumber("Arm Encoder: ", armTalon.getEncPosition());
		SmartDashboard.putNumber("Arm Forward Limit: ", armTalon.isFwdLimitSwitchClosed() ? 1 : 0);
	}

	public boolean isZeroed() {
		return armZeroed;
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new MoveArmsWithController());
    }
}

