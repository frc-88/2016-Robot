package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	Author:
 *	Nick Avtges
 */
public class Drive extends Subsystem {
    
	private final CANTalon lTalon1, lTalon2, rTalon1, rTalon2;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
		
		// left side drive controllers
		lTalon1 = new CANTalon(RobotMap.driveLeftMaster);
		lTalon2 = new CANTalon(RobotMap.driveLeftSlave);
		
		// right side drive controllers
		rTalon1 = new CANTalon(RobotMap.driveRightMaster);
		rTalon2 = new CANTalon(RobotMap.driveRightSlave);

	}
	protected void initDefaultCommand() {
		
		
	}
	public void DriveSimple(double leftDirection, double rightDirection){
		lTalon1.set(leftDirection);
		lTalon2.set(leftDirection);
		
		rTalon1.set(rightDirection);
		rTalon2.set(rightDirection);
	}
}

