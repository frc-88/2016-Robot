package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *	Author:
 *	Nick Avtges
 */
public class Drive extends Subsystem {
    
	private final CANTalon lTalonMaster, lTalonSlave, rTalonMaster, rTalonSlave;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Drive(){
		
		// left side drive controllers
		lTalonMaster = new CANTalon(RobotMap.driveLeftMaster);
		lTalonSlave = new CANTalon(RobotMap.driveLeftSlave);
		
		// right side drive controllers
		rTalonMaster = new CANTalon(RobotMap.driveRightMaster);
		rTalonSlave = new CANTalon(RobotMap.driveRightSlave);

	}
	protected void initDefaultCommand() {
		
		
	}
	public void DriveSimple(double leftDirection, double rightDirection){
		lTalonMaster.set(leftDirection);
		lTalonSlave.set(leftDirection);
		
		rTalonMaster.set(rightDirection);
		rTalonSlave.set(rightDirection);
	}
}

