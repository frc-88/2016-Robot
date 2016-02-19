package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.HookDeliverySystemWithController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class HookDeliverySystem extends Subsystem {
	
    private final CANTalon angleTalon;
    private final CANTalon HDSTalon;
    
    public HookDeliverySystem() {
    	angleTalon = new CANTalon(RobotMap.hdsAngleMotorController);
    	angleTalon.enableBrakeMode(true);
    	
    	HDSTalon = new CANTalon(RobotMap.hdsElevatorMotorController);
    	HDSTalon.enableBrakeMode(true);
    }
    
    public void moveAngle(double voltage) {
    	angleTalon.set(voltage);
    	
    	SmartDashboard.putNumber("Angle Voltage: ", angleTalon.getOutputVoltage());
    	SmartDashboard.putNumber("Angle Current: ", angleTalon.getOutputCurrent());
    	SmartDashboard.putNumber("Angle Encoder Position: ", angleTalon.getEncPosition());
    	SmartDashboard.putNumber("Angle Encoder Velocity: ", angleTalon.getEncVelocity());
    }
    
    public void moveHDS(double voltage){
    	HDSTalon.set(voltage);
    	
    	SmartDashboard.putNumber("HDS Voltage: ", HDSTalon.getOutputVoltage());
    	SmartDashboard.putNumber("HDS Current: ", HDSTalon.getOutputCurrent());
    	SmartDashboard.putNumber("HDS Encoder Position: ", HDSTalon.getEncPosition());
    	SmartDashboard.putNumber("HDS Encoder Velocity: ", HDSTalon.getEncVelocity());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new HookDeliverySystemWithController());
    }
}

