package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	Author:
 *	Nick Avtges
 */
public class Drive extends Subsystem {
    
	private final CANTalon lTalon1, lTalon2, lTalon3, rTalon1, rTalon2, rTalon3;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
		
		// left side drive controllers
		lTalon1 = new CANTalon(RobotMap.leftMotorController1);
		lTalon2 = new CANTalon(RobotMap.leftMotorController2);
		lTalon3 = new CANTalon(RobotMap.leftMotorController3);
		
		// right side drive controllers
		rTalon1 = new CANTalon(RobotMap.rightMotorController1);
		rTalon2 = new CANTalon(RobotMap.rightMotorController2);
		rTalon3 = new CANTalon(RobotMap.rightMotorController3);

	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

